package com.xuecheng.framework.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class FeignClientInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        try{
            //使用RequestContextHolder工具获取request相关变量
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null){
                //取出request
                HttpServletRequest request = attributes.getRequest();
                Enumeration<String> headerNames = request.getHeaderNames();
                if(headerNames!=null){
                    while (headerNames.hasMoreElements()){
                        //取出header key
                        String name = headerNames.nextElement();
                        String value = request.getHeader(name);

                        //向下传递header中的authorization
                        if(name.equals("authorization")){
                            requestTemplate.header(name,value);
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
