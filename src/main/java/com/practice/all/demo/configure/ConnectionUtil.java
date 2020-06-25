package com.practice.all.demo.configure;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {


/**
     * 获取RabbitMQ连接对象
     * @return
     */

    public static Connection getConnection() {

        ConnectionFactory factory = new ConnectionFactory();
        // 设置服务器地址
        factory.setHost("127.0.0.1");
        // 设置服务器用户名
        factory.setUsername("admin");
        // 设置服务器密码
        factory.setPassword("admin");
        // 设置服务器端口
        factory.setPort(5672);
        // 设置虚拟名称
        factory.setVirtualHost("vhost_mmr");

        try {
            // 创建连接对象
            return factory.newConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }
}
