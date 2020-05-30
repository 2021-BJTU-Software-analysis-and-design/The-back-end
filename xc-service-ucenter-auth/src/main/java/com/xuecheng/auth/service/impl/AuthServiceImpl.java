package com.xuecheng.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.xuecheng.auth.service.AuthService;
import com.xuecheng.framework.domain.ucenter.ext.AuthToken;
import com.xuecheng.framework.domain.ucenter.response.AuthCode;
import com.xuecheng.framework.exception.ExceptionCast;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    @Value("${auth.tokenValiditySeconds}")
    int tokenValiditySeconds;
    //Eureka负载均衡客户端
    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    /**
     * 用户登陆认证实现
     * @param username 用户名
     * @param password 密码
     * @param clientId 客户端id
     * @param clientSecret 客户端凭证
     * @return AuthToken
     */
    @Override
    public AuthToken login(String username, String password, String clientId, String clientSecret) {
        //申请令牌
        AuthToken authToken = this.appleToken(username, password, clientId, clientSecret);
        if(authToken == null){
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_APPLYTOKEN_FAIL);
        }
        //保存令牌到redis
        boolean saveToken = this.saveToken(authToken, tokenValiditySeconds);
        if(!saveToken){
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_TOKEN_SAVEFAIL);
        }
        return authToken;
    }

    /**
     * 获取用户accessToken信息
     * @param token usertoken
     * @return accessToken
     */
    @Override
    public String getJwt(String token) {
        String key = "user_token:"+token;
        String jwtInfo = stringRedisTemplate.opsForValue().get(key);
        if(jwtInfo!=null){
            AuthToken authToken = null;
            try {
                authToken = JSON.parseObject(jwtInfo, AuthToken.class);
                String access_token = authToken.getAccess_token();
                return access_token;
            }catch (Exception e){
                LOGGER.error("getUserToken from redis and execute JSON.parseObject error {}",e.getMessage());
                return null;
            }
        }
        return null;
    }

    /**
     * 删除指定usertoken在redis中的jwt信息
     * @param uid usertoken
     * @return
     */
    @Override
    public Boolean delToken(String uid) {
        String key = "user_token:" + uid;
        Boolean delete = stringRedisTemplate.delete(key);
        return delete;
    }

    //储存令牌到redis
    private boolean saveToken(AuthToken authToken, long ttl){
        //储存到redis的key
        String key = "user_token:" + authToken.getJwt_token();
        Map<String,String> valueMap = new HashMap<>();
        //拼装value
        valueMap.put("access_token",authToken.getAccess_token());
        valueMap.put("refresh_token",authToken.getRefresh_token());
        String valueJson = JSON.toJSONString(valueMap);
        //保存到令牌到redis
        stringRedisTemplate.boundValueOps(key).set(valueJson,ttl, TimeUnit.SECONDS);
        //获取过期时间
        Long expire = stringRedisTemplate.getExpire(key);
        //大于0则返回true
        return expire>0;
    }

    //向Oauth2服务申请令牌
    private AuthToken appleToken(String username, String password, String clientId, String clientSecret){
        //采用客户端负载均衡的方式从eureka获取认证服务的ip和端口
        ServiceInstance serviceInstance = loadBalancerClient.choose("XC-SERVICE-UCENTER-AUTH");
        URI uri = serviceInstance.getUri();
        String authUrl = uri + "/auth/oauth/token";

        //使用LinkedMultiValueMap储存多个header信息
        LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        //设置basic认证信息
        String basicAuth = this.getHttpBasic(clientId, clientSecret);
        headers.add("Authorization",basicAuth);

        //设置请求中的body信息
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type","password");
        body.add("username",username);
        body.add("password",password);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, headers);

        //凭证信息错误时候, 指定restTemplate当遇到400或401响应时候也不要抛出异常，也要正常返回值
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                //当响应的值为400或者401时也要正常响应,不要抛出异常
                if(response.getRawStatusCode()!=400 && response.getRawStatusCode()!=401){
                    super.handleError(response);
                }
            }
        });

        Map map = null;
        try {
            ((RestTemplate) restTemplate).setErrorHandler(new DefaultResponseErrorHandler() {
                @Override
                public void handleError(ClientHttpResponse response) throws IOException {
                    // 设置 当响应400和401时照常响应数据，不要报错
                    if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401 ) {
                        super.handleError(response);
                    }
                }
            });

            //http请求spring security的申请令牌接口
            ResponseEntity<Map> mapResponseEntity = restTemplate.exchange(authUrl, HttpMethod.POST, new
                    HttpEntity<MultiValueMap<String, String>>(body, headers), Map.class);
            map = mapResponseEntity.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("request oauth_token_password error: {}",e.getMessage());
            e.printStackTrace();
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_APPLYTOKEN_FAIL);
        }

        if(map == null ||map.get("access_token") == null ||
                        map.get("refresh_token") == null ||
                        map.get("jti") == null){//jti是jwt令牌的唯一标识作为用户身份令牌
            //获取spring security返回的错误信息
            String error_description = (String) map.get("error_description");
            if(StringUtils.isNotEmpty(error_description)){
                if(error_description.equals("坏的凭证")){
                    ExceptionCast.cast(AuthCode.AUTH_CREDENTIAL_ERROR);
                }else if(error_description.indexOf("UserDetailsService returned null")>=0){
                    ExceptionCast.cast(AuthCode.AUTH_ACCOUNT_NOTEXISTS);
                }
            }
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_APPLYTOKEN_FAIL);
        }

        //拼装authToken并返回
        AuthToken authToken = new AuthToken();
        //访问令牌(jwt)
        String access_token = (String) map.get("access_token");
        //刷新令牌(jwt)
        String refresh_token = (String) map.get("refresh_token");
        //jti，作为用户的身份标识,也就是后面我们用于返回给到用户前端的凭证
        String jwt_token = (String) map.get("jti");

        authToken.setAccess_token(access_token);
        authToken.setRefresh_token(refresh_token);
        authToken.setJwt_token(jwt_token);
        return authToken;
    }

    private String getHttpBasic(String clientId, String clientSecret){
        //将客户端id和客户端密码拼接，按“客户端id:客户端密码”
        String string = clientId+":"+clientSecret;
        //进行base64编码
        byte[] encode = Base64.encode(string.getBytes());
        return "Basic "+new String(encode);
    }
}

