package com.xuecheng.govern.gateway.service.impl;

import com.xuecheng.framework.utils.CookieUtil;
import com.xuecheng.govern.gateway.service.AuthService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 从用户请求中的cookie中取出token
     * @param request
     * @return
     */
    @Override
    public String getTokenFromCookie(HttpServletRequest request) {
        Map<String, String> cookieMap = CookieUtil.readCookie(request, "uid");
        String access_token = cookieMap.get("uid");
        if(StringUtils.isEmpty(access_token)){
            return null;
        }
        return access_token;
    }

    /**
     * 从header中查询jwt令牌
     * @param request
     * @return
     */
    @Override
    public String getJwtFromHeader(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if(StringUtils.isEmpty(authorization)){
            //拒绝访问
            return null;
        }
        if(!authorization.startsWith("Bearer ")){
            //拒绝访问
            return null;
        }
        return authorization;
    }

    /**
     * 查询令牌的有效期
     * @param jti 用户令牌
     * @return
     */
    @Override
    public long getExpire(String jti) {
        //token在redis中的key
        String key = "user_token:"+jti;
        Long expire = stringRedisTemplate.getExpire(key);
        return expire;
    }
}
