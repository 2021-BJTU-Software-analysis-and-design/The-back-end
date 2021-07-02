package com.xuecheng.ucenter.service;

import com.xuecheng.framework.domain.ucenter.XcUser;
import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import com.xuecheng.framework.domain.ucenter.request.RegisterRequest;
import com.xuecheng.framework.domain.ucenter.response.UcenterCode;

//关于用户的相关操作
public interface UserService {
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return XcUser
     */
    public XcUser findXcUserByUsername(String username);

    /**
     * 根据用户名获取用户权限/所属公司等信息
     * @param username 用户名
     * @return XcUserExt
     */
    public XcUserExt getUserExt(String username);

    public UcenterCode save(RegisterRequest registerRequest);
}
