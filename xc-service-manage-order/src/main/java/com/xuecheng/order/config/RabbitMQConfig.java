package com.xuecheng.order.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    //添加选课任务交换机
    public static final String EX_LEARNING_ADDCHOOSECOURSE = "ex_learning_addchoosecourse";

    //添加选课消息队列,用于发送添加选课消息
    public static final String XC_LEARNING_ADDCHOOSECOURSE = "xc_learning_addchoosecourse";

    //完成添加选课消息队列,用于接收完成选课的消息
    public static final String XC_LEARNING_FINISHADDCHOOSECOURSE = "xc_learning_finishaddchoosecourse";

    //添加选课路由key,用于发送添加选课的消息
    public static final String XC_LEARNING_ADDCHOOSECOURSE_KEY = "addchoosecourse";

    //完成添加选课路由key
    public static final String XC_LEARNING_FINISHADDCHOOSECOURSE_KEY = "finishaddchoosecourse";

    /**
     * 交换机配置,用于发送添加选课消息以及接收完成添加选课的消息
     * @return the exchange
     */
    @Bean(EX_LEARNING_ADDCHOOSECOURSE)
    public Exchange EX_DECLARE() {
        return ExchangeBuilder.directExchange(EX_LEARNING_ADDCHOOSECOURSE).durable(true).build();
    }

    /**
     * 声明用于接收 "完成添加选课" 消息的队列
     * @return Queue
     */
    @Bean(XC_LEARNING_FINISHADDCHOOSECOURSE)
    public Queue QUEUE_XC_LEARNING_FINISHADDCHOOSECOURSE() {
        Queue queue = new Queue(XC_LEARNING_FINISHADDCHOOSECOURSE);
        return queue;
    }

    /**
     * 声明用于发送 "添加选课" 消息的队列
     * @return Queue
     */
    @Bean(XC_LEARNING_ADDCHOOSECOURSE)
    public Queue QUEUE_XC_LEARNING_ADDCHOOSECOURSE() {
        Queue queue = new Queue(XC_LEARNING_ADDCHOOSECOURSE);
        return queue;
    }


    /**
     * 将 "完成添加选课消息" 的队列绑定到交换机
     * @param queue 完成添加选课消息队列
     * @param exchange 交换机
     * @return BindingBuilder
     */
    @Bean
    public Binding BINDING_QUEUE_FINISHADDCHOOSECOURSE(
            @Qualifier(XC_LEARNING_FINISHADDCHOOSECOURSE) Queue queue,
            @Qualifier(EX_LEARNING_ADDCHOOSECOURSE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(XC_LEARNING_FINISHADDCHOOSECOURSE_KEY).noargs();
    }


    /**
     * 将 "添加选课" 消息的队列绑定到交换机
     * @param queue 添加选课消息队列
     * @param exchange 交换机
     * @return BindingBuilder
     */
    @Bean
    public Binding BINDING_QUEUE_ADDCHOOSECOURSE(
            @Qualifier(XC_LEARNING_ADDCHOOSECOURSE) Queue queue,
            @Qualifier(EX_LEARNING_ADDCHOOSECOURSE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(XC_LEARNING_ADDCHOOSECOURSE_KEY).noargs();
    }

}
