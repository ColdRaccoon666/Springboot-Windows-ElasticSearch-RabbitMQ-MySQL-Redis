package com.practice.all.demo.configure;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * rabbitMQ 生产者
 * EXCHANGE_NAME ： 交换机名字
 */
public class Send {

    private static final String EXCHANGE_NAME = "test_exchange_directs";

    public void addMQ(String id,String name) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        // 声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "direct"); //指定分发类型为direct

        // 发送消息
        String sql = "insert into stutwo values("+id+",'"+ name +"')";
        // 指定路由key
        String routingKey = "error";

        channel.basicPublish(EXCHANGE_NAME, routingKey, null, sql.getBytes());
        System.out.println("发送消息：" + sql);

        channel.close();
        connection.close();
    }

    public void amendMQ(String id,String name) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        // 声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "direct"); //指定分发类型为direct

        // 发送消息
        String sql = "update stutwo set name = '"+name+"' where id= "+id+" ";
        // 指定路由key
        String routingKey = "error";

        channel.basicPublish(EXCHANGE_NAME, routingKey, null, sql.getBytes());
        System.out.println("发送消息：" + sql);

        channel.close();
        connection.close();
    }

    public void delMQ(String id) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        // 声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "direct"); //指定分发类型为direct

        // 发送消息
        String sql = "delete from stutwo  where id= "+id+" ";
        // 指定路由key
        String routingKey = "error";

        channel.basicPublish(EXCHANGE_NAME, routingKey, null, sql.getBytes());
        System.out.println("发送消息：" + sql);

        channel.close();
        connection.close();
    }
}
