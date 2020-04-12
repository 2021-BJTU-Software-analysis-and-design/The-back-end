package com.xuecheng.govern.center;

        import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;
        import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer  //标识这是一个EurekaServer
@SpringBootApplication
public class GorvernCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(GorvernCenterApplication.class,args);
    }
}
