package com.xuecheng.test.rabbitmq.mq;

        import com.xuecheng.test.rabbitmq.config.RabbitmqConfig;
        import org.springframework.amqp.core.Message;
        import org.springframework.amqp.rabbit.annotation.RabbitListener;
        import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
        import com.rabbitmq.client.Channel;
        import org.springframework.stereotype.Component;

@Component
public class ReceiveHandler {

    //监听email队列
    @RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_EMAIL})
    public void receive_email(String msg, Message message, Channel channel){
        System.out.println("receive: "+msg);
    }

    //监听sms队列
    @RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_SMS})
    public void receive_sms(String msg, Message message,Channel channel){
        System.out.println("receive: "+msg);
    }

}
