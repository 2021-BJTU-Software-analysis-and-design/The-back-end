package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateService {
    @Autowired
    CmsTemplateRepository cmsTemplateRepository;

    /**
     * 获取所有模板信息
     * @return
     */
    public QueryResponseResult findList(){
        //调用MongoDB提供的dao接口
        List<CmsTemplate> all = cmsTemplateRepository.findAll();
        if(all == null){
            return new QueryResponseResult(CommonCode.FAIL, null);
        }

        //拼装返回信息
        QueryResult<CmsTemplate> cmsTemplateQueryResult = new QueryResult<>();
        cmsTemplateQueryResult.setList(all);
        cmsTemplateQueryResult.setTotal(all.size());
        return new QueryResponseResult(CommonCode.SUCCESS,cmsTemplateQueryResult);
    }


}
