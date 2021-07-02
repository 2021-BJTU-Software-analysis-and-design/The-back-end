package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer04_topcis {
    //队列名称
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_TOPICS_INFORM = "exchange_topics_inform";
    private static final String ROUTINGKEY_INFORM_EMAIL = "inform.#.email.#";
    private static final String ROUTINGKEY_INFORM_SMS = "inform.#.sms.#";

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
             * 声明交换机
             * 1、交换机名称
             * 2、交换机类型：fanout、topic、direct、headers
             */
            channel.exchangeDeclare(EXCHANGE_TOPICS_INFORM, BuiltinExchangeType.TOPIC);

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
            channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);
            channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);

            /**
             * 将交换机和队列进行绑定
             */
            channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_TOPICS_INFORM, ROUTINGKEY_INFORM_SMS);
            channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_TOPICS_INFORM, ROUTINGKEY_INFORM_EMAIL);

            //发布消息到EMAIL
            for (int i = 0; i < 5; i++) {
                String message = "inform to email " + i;
                channel.basicPublish(EXCHANGE_TOPICS_INFORM, "inform.email", null, message.getBytes());
                System.out.println("Send Message is: " + message);
            }

            //发布消息SMS
            for (int i = 0; i < 5; i++) {
                String message = "inform to sms " + i;
                channel.basicPublish(EXCHANGE_TOPICS_INFORM, "inform.sms", null, message.getBytes());
                System.out.println("Send Message is: " + message);
            }

            //发布消息到cms和email
            for (int i = 0; i < 5; i++) {
                String message = "inform to sms and email " + i;
                channel.basicPublish(EXCHANGE_TOPICS_INFORM, "inform.sms.email", null, message.getBytes());
                System.out.println("Send Message is: " + message);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //关闭通道和连接
            if (channel != null) {
                channel.close();
            }
            if (channel != null) {
                connection.close();
            }
        }
    }
}
