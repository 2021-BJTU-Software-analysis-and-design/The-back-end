package com.xuecheng.api.cms;


import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="CMS页面模板",description = "CMS页面模板，提供CMS页面模板的CRUD")
public interface CmsTemplateControllerApi {
    @ApiOperation("查询所有站点信息")
    public QueryResponseResult findList();
}
