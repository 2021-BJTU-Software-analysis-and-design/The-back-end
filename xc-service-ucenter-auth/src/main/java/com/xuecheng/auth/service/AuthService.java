package com.xuecheng.auth.service;

import com.xuecheng.framework.domain.ucenter.ext.AuthToken;

public interface AuthService {
    /**
     * 用户登陆认证
     * @param username 用户名
     * @param password 密码
     * @param clientId 客户端id
     * @param clientSecret 客户端凭证
     * @return AuthToken
     */
    public AuthToken login(String username, String password, String clientId, String clientSecret);

    /**
     * 根据usertoken获取用户的accesstoken
     * @param token usertoken
     * @return accesstoken
     */
    public String getJwt(String token);

    /**
     * 删除用户在redis中的token信息
     * @param uid
     */
    public Boolean delToken(String uid);
}
