package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 继承MongoDB自带的一些Repository
 */
public interface CmsSiteRepository extends MongoRepository<CmsSite,String> {

}
