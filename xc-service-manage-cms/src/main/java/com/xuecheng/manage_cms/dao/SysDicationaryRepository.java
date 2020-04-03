package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;
        import org.springframework.stereotype.Repository;

@Repository
public interface SysDicationaryRepository extends MongoRepository<SysDictionary,String> {
    SysDictionary findSysDictionaryByDType(String dType);
}
