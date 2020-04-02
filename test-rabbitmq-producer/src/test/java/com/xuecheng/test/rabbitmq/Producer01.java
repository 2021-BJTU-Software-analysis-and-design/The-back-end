package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer01 {
    //队列名称
    private static final String QUEUE = "helloworld2";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = null;
        Channel channel = null;
        try {
            //构建连接工厂，并设置一些基本的链接信息
            ConnectionFactory factory = new ConnectionFactory();

            factory.setHost("localhost");
            factory.setPort(5672);
            factory.setUsername("guest");
            factory.setPassword("guest");
            //rabbitMQ默认的虚拟机名称为“/”，虚拟机相当于一个独立的mq服务
            factory.setVirtualHost("/");

            //创建与RabbitMQ服务的TCP连接
            connection = factory.newConnection();

            //创建与Exchange的通道，每个连接可以创建多个通道，每个通道代表一个会话任务
            channel = connection.createChannel();

            /**
             * 声明队列，如果Rabbit中没有此队列，将自动创建
             * String queue, boolean durable, boolean exclusive, boolean autoDelete,Map<String, Object> arguments
             *
             * param1:队列名称
             * param2:是否持久化
             * param3:队列是否独占此连接
             * param4:队列不再使用时是否自动删除此队列
             * param5:队列参数
             */
            channel.queueDeclare(QUEUE,true,false,false,null);
            String message = "hello world 小明" + System.currentTimeMillis();

            /**
             * 消息发布方法
             消息发布方法
             * param1：Exchange的名称，如果没有指定，则使用Default Exchange
             * param2:routingKey,消息的路由Key，是用于Exchange（交换机）将消息转发到指定的消息队列
             * param3:消息包含的属性
             * param4：消息体
             * 这里没有指定交换机，消息将发送给默认交换机，每个队列也会绑定那个默认的交换机，但是不能显示绑定或解除绑定
             * 默认的交换机，routingKey等于队列名称
             */
            channel.basicPublish("",QUEUE,null,message.getBytes());
            System.out.println("Send Message is: ' " + message + " '");

        }catch ( Exception ex){
            ex.printStackTrace();
        }finally {
            //关闭通道和连接
            if(channel != null){
                channel.close();
            }
            if(channel != null){
                connection.close();
            }
        }
    }
}
