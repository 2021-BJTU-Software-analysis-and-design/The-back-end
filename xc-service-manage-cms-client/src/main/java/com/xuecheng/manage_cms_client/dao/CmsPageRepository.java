package com.xuecheng.manage_cms_client.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * 继承MongoDB自带的一些Repository
 */
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {
        /**
         * 根据站点id、站点名称、站点路径来查询站点信息
         * @return
         * @param siteId
         * @param pageName
         * @param pageWebPath
         */
        CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName, String siteId, String pageWebPath);
}