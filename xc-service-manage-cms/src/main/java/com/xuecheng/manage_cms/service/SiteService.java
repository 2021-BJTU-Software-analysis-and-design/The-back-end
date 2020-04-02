package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.request.QuerySizeRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteService {
    @Autowired
    CmsSiteRepository cmsSiteRepository;

    /**
     * 查询所有的站点信息
     * @return
     */
    public QueryResponseResult findList(){
        //获取所有的站点信息
        List<CmsSite> all = cmsSiteRepository.findAll();
        if(all == null){
            return new QueryResponseResult(CommonCode.FAIL, null);
        }
        //查询响应模板
        QueryResult<CmsSite> queryResult = new QueryResult<CmsSite>();
        queryResult.setList(all);
        queryResult.setTotal(all.size());
        //根据指定模板响应数据
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }
}
