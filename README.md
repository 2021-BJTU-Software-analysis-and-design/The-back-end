# 睿瞳公司人工智能产品网站项目介绍
+ ## 项目技术栈
| 模块       | **技术栈**                                                   |
| :--------- | ------------------------------------------------------------ |
| 数据访问层 | Spring Data JPA 、Mybatis、Spring Data Mongodb等             |
| 业务层     | Spring IOC、Aop事务控制、Spring Task任务调度、Feign、Ribbon、Spring AMQP、Spring Data Redis |
| 控制层     | Spring MVC、FastJSON、RestTemplate、Spring Security Oauth2+JWT等 |
| 微服务治理 | Eureka、Zuul、Hystrix、Spring Cloud Config等                 |
| 持久层     | MySQL、MongoDB、Redis、ElasticSearch                         |

+ ## 项目的模块及功能
#### 前端项目

> xc-ui-pc-static-portal    门户工程
>
> xc-ui-pc-sysmanage     系统管理前端工程
>
> xc-ui-pc-teach               课程管理前端工程
>
> xc-ui-pc-learning		  课程学习中心

#### 后端项目

>xc-framework-parent                      父工程，提供依赖管理
>
>xc-framework-common                  通用工程，提供各层封装
>
>xc-framework-model                      模型工程，提供统一的模型类管理
>
>xc-framework-utils                         工具类工程，提供本项目所使用的工具类
>
>xc-framework-api                           接口工程，统一管理本项目的服务接口
>
>xc-service-manage-cms                CMS页面管理系统(端口31001)
>
>xc‐service‐manage‐cms‐client     CMS页面客户端系统(端口31000)
>
>xc-govern-center						   Eureka服务(端口50101)
>
>xc-govern-gateway						zuul网关服务(端口50201)
>
>xc-service-manage-course           课程管理系统(端口31200)
>
>xc-service-manage-media			课程媒体资源管理服务(端口31400)
>
>Xc-service-manage-media-processor  课程媒体资源处理服务(端口31450)
>
>xc-service-base-filesystem            文件上传微服务(22100)
>
>xc-service-search							elasticsearch服务(端口40100)
>
>xc-service-ucenter						 用户认证中心服务(端口40300)
>
>xc-service-ucenter-auth				用户认证中心jwt服务(端口40400)

+ ## 项目运行及部署
    - 安装mysql，mongodb，redis，elasticsearch，nginx，ruby，rabbitMQ，ffmpeg. logstash
    - 将db里的数据库文件导入到数据库里
    - 将config里的配置文件放到相应的目录里，并修改部分配置文件，将一些路径修改为相应的文件路径。
    - 运行mysql，mongodb，redis，elasticsearch，nginx，ruby，rabbitMQ，ffmpeg, logstash
    - 运行后端所有服务程序
    - 运行前端所有程序
    - 打开www.ruitong.com,运行程序
    - 管理员账户super,密码111111
