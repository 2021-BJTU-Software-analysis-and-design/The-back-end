package com.xuecheng.govern.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.govern.gateway.service.AuthService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginFilterTest extends ZuulFilter {
    private static final Logger LOG = LoggerFactory.getLogger(LoginFilterTest.class);

    @Autowired
    AuthService authService;

    @Override
    public String filterType() {
        return "pre";
    }
    @Override
    public int filterOrder() {
        return 2;//int值来定义过滤器的执行顺序，数值越小优先级越高
    }
    @Override
    public boolean shouldFilter() {// 该过滤器需要执行
        return true;
    }
    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletResponse response = requestContext.getResponse();         //获取响应对象
        HttpServletRequest request = requestContext.getRequest();           //获取请求对象

        //查询身份令牌
        String jti = authService.getTokenFromCookie(request);
        if(jti == null){
            this.access_denied();
        }

        //从redis中校验身份令牌是否过期
        long expire = authService.getExpire(jti);
        if(expire<=0){
            //拒绝访问
            access_denied();
        }

        //查询jwt令牌
        String jwt = authService.getJwtFromHeader(request);
        if(jwt == null){
            //拒绝访问
            this.access_denied();
        }
        return null;
    }

    //拒绝访问
    private void access_denied(){
        //上下文对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        requestContext.setSendZuulResponse(false);//拒绝访问
        //设置响应内容
        ResponseResult responseResult =new ResponseResult(CommonCode.UNAUTHENTICATED);
        String responseResultString = JSON.toJSONString(responseResult);
        requestContext.setResponseBody(responseResultString);
        //设置状态码
        requestContext.setResponseStatusCode(200);
        HttpServletResponse response = requestContext.getResponse();
        response.setContentType("application/json;charset=gbk");
    }
}
