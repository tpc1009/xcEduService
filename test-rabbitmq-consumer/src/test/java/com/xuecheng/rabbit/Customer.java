package com.xuecheng.rabbit;

import com.rabbitmq.client.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author tpc
 * @date 2020/3/21 9:48
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Customer {

    private static final String QUEUE ="queue";

    @Test
    public void customer1() throws Exception {
        //创建工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");

        //创建连接
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //声名队列
        channel.queueDeclare(QUEUE,true,false,false,null);

        //定义消费
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //交换机
                String exchange = envelope.getExchange();
                //路由key
                String routingKey = envelope.getRoutingKey();
                //消息id l
                long deliveryTag = envelope.getDeliveryTag();
                //消息内容
                String msg = new String(body, "utf-8");
                System.out.println("receive message.." + msg);
            }
        };

        //监听队列
        channel.basicConsume(QUEUE,true,defaultConsumer);

    }
}
