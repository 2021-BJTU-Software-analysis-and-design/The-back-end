package com.xuecheng.manage_cms.service;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.*;
import com.xuecheng.manage_cms.config.RabbitmqConfig;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.netty.util.internal.StringUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PageService {

    @Autowired
    CmsPageRepository cmsPageRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CmsTemplateRepository templateRepository;

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 页面发布
     * @param pageId
     * @return
     */
    public ResponseResult postPage(String pageId) {
        //执行静态化
        String pageHtml = null;
        try {
            pageHtml = this.getPageHtml(pageId);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        if(StringUtils.isEmpty(pageHtml)){
            ExceptionCast.cast(CmsCode.CMS_GENRATEHTML_HTML_IS_NULL);
        }

        //保存静态化文件
        CmsPage cmsPage = this.saveHtml(pageId, pageHtml);
        //发送消息
        this.sendPostPage(pageId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //发送页面发布消息
    private void sendPostPage(String pageId){
        CmsPageResult cmsPageResult = this.cmsPageQueryById(pageId);
        CmsPage cmsPage = cmsPageResult.getCmsPage();
        if(cmsPage == null){
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOT_EXISTS);
        }
        Map<String, String> msgMap = new HashMap<>();
        msgMap.put("pageId",pageId);
        //消息内容
        String msg = JSON.toJSONString(msgMap);
        //获取站点id作为routing key
        String siteId = cmsPage.getSiteId();
        //发送消息到指定交换机、routingKey、以及要发送的消息
        rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE,siteId,msg);
    }

    //保存静态化页面内容
    private CmsPage saveHtml(String pageId,String content){
        //查询页面是否存在
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        if(!optional.isPresent()){
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOT_EXISTS);
        }

        CmsPage cmsPage = optional.get();
        //储存之前先删除
        String htmlFileId = cmsPage.getHtmlFileId();
        if(StringUtils.isNotEmpty(htmlFileId)){
            gridFsTemplate.delete(Query.query(Criteria.where("_id").is(htmlFileId)));
        }
        //保存html文件到GridFS
        try {
            InputStream inputStream = IOUtils.toInputStream(content,"utf-8");
            ObjectId objectId = gridFsTemplate.store(inputStream, cmsPage.getPageName());
            //文件id
            String fileId = objectId.toString();
            //将文件id储存到cmspage中
            cmsPage.setHtmlFileId(fileId);
            cmsPageRepository.save(cmsPage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cmsPage;
    }


    /**
     * 页面静态化
     * @param pageId
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public String getPageHtml(String pageId) throws IOException, TemplateException {
        //获取页面模型数据
        Map modelByPageId = this.getModelByPageId(pageId);
        if(modelByPageId == null){
            //获取页面模型数据为空
            ExceptionCast.cast(CmsCode.CMS_GENRATEHTML_DATA_IS_NULL);
        }
        //获取页面模板
        String templateContent = getTemplateByPageId(pageId);
        if(StringUtils.isEmpty(templateContent)){
            //页面模板为空
            ExceptionCast.cast(CmsCode.CMS_GENRATEHTML_TEMPLATE_IS_NULL);
        }
        //构建页面静态化数据
        String html = generateHtml(templateContent, modelByPageId);
        if(StringUtils.isEmpty(html)){
            ExceptionCast.cast(CmsCode.CMS_GENRATEHTML_HTML_IS_NULL);
        }
        return html;
    }

    //构建静态化页面数据
    private String generateHtml(String template,Map model) throws IOException, TemplateException {
        //生成配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        //模板加载器
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template",template);
        //配置模板加载器
        configuration.setTemplateLoader(stringTemplateLoader);

        //获取模板
        Template template1 = configuration.getTemplate("template");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template1, model);
        return html;
    }

    //获取页面模板文件数据
    private String getTemplateByPageId(String pageId){
        //查询页面信息
        CmsPageResult cmsPageResult = this.cmsPageQueryById(pageId);
        CmsPage cmsPage = cmsPageResult.getCmsPage();
        //页面不存在
        if(cmsPage == null){
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOT_EXISTS);
        }
        //获取页面模板数据
        String templateId = cmsPage.getTemplateId();
        if(StringUtils.isEmpty(templateId)){
            ExceptionCast.cast(CmsCode.CMS_GENRATEHTML_TEMPLATE_IS_NULL);
        }
        Optional<CmsTemplate> optional = templateRepository.findById(templateId);
        if(optional.isPresent()){
            CmsTemplate cmsTemplate = optional.get();
            //获取模板文件id
            String templateFileId = cmsTemplate.getTemplateFileId();
            //取出模板文件内容
            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
            //打开下载流对象
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            //创建GridResource
            GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);

            try {
                String content = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
                return content;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //从dataUrl中获取页面模型数据
    private Map getModelByPageId(String pageId){
        //查询页面信息
        CmsPageResult cmsPageResult = this.cmsPageQueryById(pageId);
        CmsPage cmsPage = cmsPageResult.getCmsPage();
        //页面不存在
        if(cmsPage == null){
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOT_EXISTS);
        }
        //取出dataUrl
        String dataUrl = cmsPage.getDataUrl();
        if(StringUtils.isEmpty(dataUrl)){
            ExceptionCast.cast(CmsCode.CMS_GENRATEHTML_DATAURL_IS_NULL);
        }
        //发送请求获取模型数据
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl,Map.class);
        Map body = forEntity.getBody();
        return body;
    }


    /**
     * 分页查询
     *
     * @param page             页号
     * @param size             每页大小
     * @param queryPageRequest 查询条件
     * @return
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {
        //判断条件对象是否为空
        if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }

        //匹配条件值
        CmsPage cmsPage = new CmsPage();

        //设置条件值
        //站点ID
        if (!StringUtil.isNullOrEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        //模板ID
        if (!StringUtil.isNullOrEmpty(queryPageRequest.getTemplateId())) {
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }

        //站点别名
        if (!StringUtil.isNullOrEmpty(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }

        //条件匹配器，用于模糊查询
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());


        //条件查询实例
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);

        //过滤条件
        if (page <= 0) {
            page = 1;
        }
        if (size <= 0) {
            size = 10;
        }

        page = page - 1;

        //创建分页查询参数
        PageRequest pageable = PageRequest.of(page, size);

        //分页查询数据
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);

        //整理查询到的数据
        QueryResult queryResult = new QueryResult();
        queryResult.setList(all.getContent());
        queryResult.setTotal(all.getTotalElements());

        //返回结果
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }


    /**
     * 根据id获取页面数据
     */
    public CmsPageResult cmsPageQueryById(String id) {
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if (optional.isPresent()) {
            CmsPage cmsPage = optional.get();
            return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
        }
        return new CmsPageResult(CommonCode.FAIL, null);
    }

    /**
     * 添加页面数据
     */
    public CmsPageResult addCmsPage(CmsPage cmsPage){
        //效验cmsPage是否为空
        if(cmsPage == null){
            //抛出异常，非法参数
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        //验证数据唯一性：sizeId、pageName、pageWebPath
        CmsPage cmsPage1 = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());

        //检验页面是否已存在
        if (cmsPage1 != null) {
            //抛出异常
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTS);
        }

        //站点id由mongoDB自动生成，防止前端传值
        cmsPage.setPageId(null);
        CmsPage save = cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS, save);

    }

    /**
     * 保存页面：如果不存在则添加，存在则更新。
     * @param cmsPage
     * @return
     */
    public CmsPageResult saveCmsPage(CmsPage cmsPage) {
        //效验cmsPage是否为空
        if(cmsPage == null){
            //抛出异常，非法参数
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        //验证数据唯一性：sizeId、pageName、pageWebPath
        CmsPage one = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());

        //如果页面已存在则进行更新操作
        if (one != null) {
            return this.updateCmsPage(one.getPageId(),one);
        }
        //不存在则直接添加
        return this.addCmsPage(cmsPage);
    }

    /**
     * 修改页面数据
     */
    public CmsPageResult updateCmsPage(String id, CmsPage cmsPage) {
        //判断该页面是否存在
        CmsPageResult cmsPageResult = this.cmsPageQueryById(id);
        CmsPage one = cmsPageResult.getCmsPage();
        if (one != null) {
            //修改数据为了安全性，这里还是建议每个字段单独设置
            //更新模板id
            one.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            one.setSiteId(cmsPage.getSiteId());
            //更新页面别名
            one.setPageAliase(cmsPage.getPageAliase());
            //更新页面名称
            one.setPageName(cmsPage.getPageName());
            //更新访问路径
            one.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            //更新dataUrl
            one.setDataUrl(cmsPage.getDataUrl());
            CmsPage save = cmsPageRepository.save(one);
            if(save != null){
                return new CmsPageResult(CommonCode.SUCCESS, save);
            }
        }
        return new CmsPageResult(CommonCode.FAIL, cmsPage);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    public ResponseResult deleteCmsPage(String id){
        //检索该页面id是否存在
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if(optional.isPresent()){
            //删除并返回结果
            cmsPageRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

}