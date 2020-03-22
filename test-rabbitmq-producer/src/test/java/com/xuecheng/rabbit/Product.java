package com.xuecheng.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author tpc
 * @date 2020/3/21 9:40
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class Product {

    private static final String QUEUEN ="queue";

    @Test
    public void product() throws IOException, TimeoutException {
        //创建工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        //虚拟主机
        factory.setVirtualHost("/");
        //创建连接tcp
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //创建队列
        channel.queueDeclare(QUEUEN,true,false,false,null);

        String message = "helllo world";

        //发布消息
        channel.basicPublish("",QUEUEN,null,message.getBytes());
        System.out.println(message);
    }
}
