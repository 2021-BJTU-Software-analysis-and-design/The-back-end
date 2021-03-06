package com.xuecheng.manage_cms.service;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.config.RabbitmqConfig;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
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
import org.springframework.web.bind.annotation.PathVariable;
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
    CmsConfigRepository cmsConfigRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CmsTemplateRepository cmsTemplateRepository;

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    CmsSiteRepository cmsSiteRepository;

    /**
     * ????????????
     *
     * @param page
     * @param size
     * @param queryPageRequest
     * @return
     */
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest queryPageRequest) {

        if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        CmsPage cmsPage = new CmsPage();

        //????????????????????????id???
        if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        //????????????id??????????????????
        if (StringUtils.isNotEmpty(queryPageRequest.getTemplateId())) {
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }
        //????????????????????????????????????
        if (StringUtils.isNotEmpty(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        //??????????????????Example
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);

        //????????????
        if (page <= 0) {
            page = 1;
        }
        page = page - 1;
        if (size <= 0) {
            size = 10;
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        QueryResult queryResult = new QueryResult();
        queryResult.setList(all.getContent());//????????????
        queryResult.setTotal(all.getTotalElements());//??????????????????
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        return queryResponseResult;
    }

    public CmsPageResult add(CmsPage cmsPage) {
        CmsPage cmsPage1 = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (cmsPage1 != null) {
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        // ????????????
        cmsPage.setPageId(null);
        CmsPage save = cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS, save);
    }

    // ????????????ID???????????????
    public CmsPage getById(String id) {
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if (optional.isPresent()) {
            CmsPage cmsPage = optional.get();
            return cmsPage;
        }
        return null;
    }

    // ????????????
    public CmsPageResult update(String id, CmsPage cmsPage) {
        // ??????ID??????????????????????????????
        CmsPage cmspage1 = this.getById(id);
        // ????????????????????????
        if (cmspage1 != null) {
            //??????????????????
            //????????????????????????
            //????????????id
            cmspage1.setTemplateId(cmsPage.getTemplateId());
            //??????????????????
            cmspage1.setSiteId(cmsPage.getSiteId());
            //??????????????????
            cmspage1.setPageAliase(cmsPage.getPageAliase());
            //??????????????????
            cmspage1.setPageName(cmsPage.getPageName());
            //??????????????????
            cmspage1.setPageWebPath(cmsPage.getPageWebPath());
            //??????????????????
            cmspage1.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            //??????dataurl
            cmspage1.setDataUrl(cmsPage.getDataUrl());
            //????????????
            cmsPageRepository.save(cmspage1);
            return new CmsPageResult(CommonCode.SUCCESS, cmspage1);
        }
        return new CmsPageResult(CommonCode.FAIL, null);
    }

    // ??????id????????????
    public ResponseResult delete(String id) {
        // ???????????????
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if (optional.isPresent()) {
            cmsPageRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    // ??????id??????CmsConfig
    public CmsConfig getConfigById(String id) {
        Optional<CmsConfig> optional = cmsConfigRepository.findById(id);
        if (optional.isPresent()) {
            CmsConfig cmsConfig = optional.get();
            return cmsConfig;
        }
        return null;
    }

    // ????????????????????????
    public String getPageHtml(String pageId) {
        // ??????????????????
        Map model = getModelByPageId(pageId);
        if (model == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        // ?????????????????????id
        String template = getTemplateByPageId(pageId);
        if (StringUtils.isEmpty(template)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        // ???????????????
        String html = generateHtml(template, model);
        return html;
    }

    private String generateHtml(String templateContent, Map model) {
        // ????????????????????????
        Configuration configuration = new Configuration(Configuration.getVersion());
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template", templateContent);
        configuration.setTemplateLoader(stringTemplateLoader);
        // ????????????
        try {
            Template template = configuration.getTemplate("template");
            String s = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private String getTemplateByPageId(String pageId) {
        CmsPage cmsPage = this.getById(pageId);
        if (cmsPage == null) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        String templateId = cmsPage.getTemplateId();
        if (StringUtils.isEmpty(templateId)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        // ????????????????????????
        Optional<CmsTemplate> optional = cmsTemplateRepository.findById(templateId);
        if (optional.isPresent()) {
            CmsTemplate cmsTemplate = optional.get();
            String templateFileId = cmsTemplate.getTemplateFileId();
            //???GridFS????????????????????????
            //????????????id????????????
            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
            //???????????????????????????
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            //??????GridFsResource??????????????????
            GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
            //??????????????????
            try {
                String content = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
                return content;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    private Map getModelByPageId(String pageId) {
        CmsPage cmsPage = this.getById(pageId);
        if (cmsPage == null) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        // ???????????????dataurl
        String dataUrl = cmsPage.getDataUrl();
        if (StringUtils.isEmpty(dataUrl)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }

        // ??????resttemplate??????dataurl???????????????
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        Map body = forEntity.getBody();
        return body;
    }

    // ????????????
    public ResponseResult post(String pageId) {
        String pageHtml = this.getPageHtml(pageId);
        // ?????????????????????????????????GridFs???
        CmsPage cmsPage = saveHtml(pageId, pageHtml);
        // ???MQ?????????
        sendPostPage(pageId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    private void sendPostPage(String pageId) {
        CmsPage cmsPage = this.getById(pageId);

        if (cmsPage == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        HashMap<String, String> msg = new HashMap<>();
        msg.put("pageId", pageId);

        String jsonString = JSON.toJSONString(msg);

        String siteId = cmsPage.getSiteId();

        rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE, siteId, jsonString);
    }

    // ??????html ???GridFS
    private CmsPage saveHtml(String pageId, String htmlContent) {
        // ???html?????????????????????GridFS
        CmsPage cmsPage = this.getById(pageId);
        if (cmsPage == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        ObjectId objectId = null;

        try {
            InputStream inputStream = IOUtils.toInputStream(htmlContent, "utf-8");
            objectId = gridFsTemplate.store(inputStream, cmsPage.getPageName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ???html??????id?????????cms
        cmsPage.setHtmlFileId(objectId.toHexString());
        cmsPageRepository.save(cmsPage);
        return cmsPage;
    }

    //?????????????????????????????????????????????
    public CmsPageResult save(CmsPage cmsPage) {
        //????????????????????????
        CmsPage one = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (one != null) {
            //????????????
            return this.update(one.getPageId(), cmsPage);
        }
        return this.add(cmsPage);
    }

    //??????????????????
    public CmsPostPageResult postPageQuick(CmsPage cmsPage) {

        //????????????????????????cms_page ?????????
        CmsPageResult save = this.save(cmsPage);
        if (!save.isSuccess()) {
            ExceptionCast.cast(CommonCode.FAIL);
        }
        //???????????????id
        CmsPage cmsPageSave = save.getCmsPage();
        String pageId = cmsPageSave.getPageId();

        //??????????????????????????????????????????GridFS??????MQ???????????????
        ResponseResult post = this.post(pageId);
        if (!post.isSuccess()) {
            ExceptionCast.cast(CommonCode.FAIL);
        }
        //????????????Url= cmsSite.siteDomain+cmsSite.siteWebPath+ cmsPage.pageWebPath + cmsPage.pageName
        //????????????id
        String siteId = cmsPageSave.getSiteId();
        CmsSite cmsSite = this.findCmsSiteById(siteId);
        //??????url
        String pageUrl = cmsSite.getSiteDomain() + cmsSite.getSiteWebPath() + cmsPageSave.getPageWebPath() + cmsPageSave.getPageName();
        return new CmsPostPageResult(CommonCode.SUCCESS, pageUrl);
    }

    //????????????id??????????????????
    public CmsSite findCmsSiteById(String siteId) {
        Optional<CmsSite> optional = cmsSiteRepository.findById(siteId);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }
}

