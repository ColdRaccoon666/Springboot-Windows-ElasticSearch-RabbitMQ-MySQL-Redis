package com.practice.all.demo.configure;
import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import java.io.IOException;

/**
 * rabbitMQ 消费者
 * EXCHANGE_NAME ： 交换机名字
 */
public class Reciver {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SMS_QUEUE_NAME = "sms_queue_name";

    private static final String EXCHANGE_NAME = "test_exchange_directs";

    public void getReciver() throws IOException {

        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        boolean durable = true;   //进行持久化
        // 声明队列
        channel.queueDeclare(SMS_QUEUE_NAME, durable, false, false, null);

        channel.basicQos(1);
        // 指定路由key
        String routingKey = "error";
        // 绑定队列到交换机
        channel.queueBind(SMS_QUEUE_NAME, EXCHANGE_NAME, routingKey);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("reciver1 msg :" + msg);
                String sql = msg;
                jdbcTemplate.update(sql);

                try {
                    // 处理完成休眠两秒
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        boolean autoAck = true;
        channel.basicConsume(SMS_QUEUE_NAME, autoAck, consumer);
    }
}
