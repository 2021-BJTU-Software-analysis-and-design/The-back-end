package com.xuecheng.govern.gateway.service;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {
    /**
     * 查询身份令牌
     * @param request
     * @return
     */
    public String getTokenFromCookie(HttpServletRequest request);

    /**
     * 从header中查询jwt令牌
     * @param request
     * @return
     */
    public String getJwtFromHeader(HttpServletRequest request);

    /**
     * 查询令牌的有效期
     * @param jti 用户令牌
     * @return
     */
    public long getExpire(String jti);
}
