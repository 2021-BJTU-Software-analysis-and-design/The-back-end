package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_cms.dao.SysDicationaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysDictionaryService {
    @Autowired
    SysDicationaryRepository sysDicationaryRepository;

    public SysDictionary findByDtype(String dType){
        return sysDicationaryRepository.findSysDictionaryByDType(dType);
    }
}
