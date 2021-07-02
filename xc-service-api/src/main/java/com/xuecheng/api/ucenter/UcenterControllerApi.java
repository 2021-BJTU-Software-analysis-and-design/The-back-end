package com.xuecheng.api.ucenter;

import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import com.xuecheng.framework.domain.ucenter.request.RegisterRequest;
import com.xuecheng.framework.domain.ucenter.response.UcenterCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "用户中心",description = "用户中心管理")
public interface UcenterControllerApi {

    @ApiOperation("获取用户信息")
    public XcUserExt getUserext(String username);

    @ApiOperation("用户注册")
    public UcenterCode save(RegisterRequest registerRequest);
}
