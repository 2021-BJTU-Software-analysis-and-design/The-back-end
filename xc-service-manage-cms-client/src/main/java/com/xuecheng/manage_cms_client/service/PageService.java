package com.xuecheng.manage_cms_client.service;

import com.mongodb.client.gridfs.GridFSBucket;

import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CmsCode;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.dao.CmsSiteRepository;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
public class PageService {
    @Autowired
    CmsPageRepository cmsPageRepository;

    @Autowired
    CmsSiteRepository cmsSiteRepository;

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;

    /**
     * 将页面html保存到页面物理路径
     * @param pageId
     */
    public void savePageToServerPath(String pageId){
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        //页面不存在则抛出异常
        if(!optional.isPresent()){
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOT_EXISTS);
        }

        CmsPage cmsPage = optional.get();
        CmsSite cmsSite = this.getCmsSiteById(cmsPage.getSiteId());
        if(cmsSite == null){
            ExceptionCast.cast(CmsCode.CMS_SIZE_NOT_EXISTS);
        }

        //原讲义和视频中使用的是sizePathysicaiPath,但是SizePage中没有这个字段，使用PageCms中的PathysicaiPath代替
        String pagePath = cmsPage.getPagePhysicalPath() + cmsPage.getPageName();

        //获取页面文件id
        String htmlFileId = cmsPage.getHtmlFileId();
        if(StringUtils.isEmpty(htmlFileId)){
            ExceptionCast.cast(CmsCode.CMS_PAGE_FILEID_NOT_EXISTS);
        }
        //获取文件流
        InputStream inputStream = this.getFileInputStreamFileById(htmlFileId);
        if(inputStream == null){
            ExceptionCast.cast(CmsCode.CMS_GENRATEHTML_HTML_IS_NULL);
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(pagePath));
            //将文件保存至物理路径
            IOUtils.copy(inputStream,fileOutputStream);
        }catch (Exception e){
            e.printStackTrace();
            ExceptionCast.cast(CommonCode.FAIL);
        }finally {
            //关闭流
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //根据文件id获取文件的gridFS输入流
    private InputStream getFileInputStreamFileById(String fileId){
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        //获取gridFs下载流
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        try {
            //获取文件输入流
            GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
            return gridFsResource.getInputStream();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    //根据站点id得到站点信息
    private CmsSite getCmsSiteById(String siteId){
        Optional<CmsSite> optional = cmsSiteRepository.findById(siteId);
        if(!optional.isPresent()){
            return null;
        }
        CmsSite cmsSite = optional.get();
        return cmsSite;
    }
}
