package com.xuecheng.rabbit;

import com.xuecheng.rabbitmq.config.RabbitMqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author tpc
 * @date 2020/3/22 16:30
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Producer_topics_springboot {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendEmail(){
        String message = "send email message to user";

        //发送消息
        this.rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_TOPICS_INFORM,"inform.email",message);
    }
}
