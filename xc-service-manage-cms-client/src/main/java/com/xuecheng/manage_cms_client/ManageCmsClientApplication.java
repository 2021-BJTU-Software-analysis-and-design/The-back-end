package com.xuecheng.manage_cms_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages={"com.xuecheng.framework"})//扫描common下的所有类
@ComponentScan(basePackages={"com.xuecheng.manage_cms_client"})
public class ManageCmsClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCmsClientApplication.class,args);
    }
}
