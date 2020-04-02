package com.xuecheng.api.cms;

import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="CMS站点管理接口",description = "CMS站点管理接口，提供CMS站点的CRUD")
public interface CmsSiteControllerApi {
    @ApiOperation("查询所有站点信息")
    public QueryResponseResult findList();
}
