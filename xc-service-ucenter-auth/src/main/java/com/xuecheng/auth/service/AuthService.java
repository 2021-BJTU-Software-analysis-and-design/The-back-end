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
}
