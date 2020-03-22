package com.xuecheng.rabbitmq.mq;


import com.xuecheng.rabbitmq.config.RabbitMqConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import sun.plugin2.message.Message;

import java.nio.channels.Channel;

/**
 * @author tpc
 * @date 2020/3/22 16:36
 */
@Component
public class ReceiveHandler {

    //监听消息
    @RabbitListener(queues = {RabbitMqConfig.QUEUE_INFORM_EMAIL})
    public void  send_email(String msg, Message message, Channel channel){
        System.out.println("receive message is "+msg);
    }
}
