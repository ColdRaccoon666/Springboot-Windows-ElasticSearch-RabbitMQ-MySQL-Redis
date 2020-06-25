# Springboot 整合 Windows版本的 ElasticSearch , RabbitMQ , MySQL , Redis

## 1. 项目介绍

用与处理高并发的业务；

用户发送请求分为``增删查改``操作：

``查``：先进入redis缓存中拿数据，redis中没有则进入ElasticSearch中拿，拿到的数据加入到redis中，并返回给用户。如果redis中有数据的话，直接返回给用户。用户可以直接通过模糊查询往ElasticSearch中查找数据。同时通过插件``logstash``将MySQL中的数据实时同步到Elastic Search中去；

``增``：判断要增加的数据是否重复，执行一遍``查``的过程，有重复的则不能增加，没有则将请求发送到RabbitMQ中，并发送消息给用户。rabbitMQ中的信息等系统有足够内存的时间再Reciver,连接数据库进行操作；

``改``：执行一遍``查``的过程，如果在redis中找到该数据则将该缓存索引进行删除，没有则进入ElasticSearch，有数据则将请求发送到RabbMq中，等待处理；

``删``：执行一遍``查``的过程，如果在redis中找到该数据则将该缓存索引进行删除，没有则进入ElasticSearch，有数据则将请求发送到RabbMq中，等待处理；



![springboot全整合.png](https://i.loli.net/2020/06/25/5Ocmo71gnwbQqNr.png)



## 2. 所用的版本：



| springboot |  ElasticSearch  | rabbitMQ |MySQL|Redis|
| :--------: | :--: | :------: | :------: | :------: |
| 2.0.7.RELEASE | 5.0.0 | rabbitmq-server-3.7.5 |MySQL 5.5|redis 3.0.504|



### 注意：

1. spring boot整合Elastic Search一点要注意版本兼容的问题（为了达到适配的搞了一下午。。。），版本号已经在上面了。
2. 将MySQL的数据同步到Elastic Search中的插件版本要跟Elastic Search一致，我用的插件时``logstash-5.0.0``，之前用的是``elasticsearch-jdbc-2.3.2.0-dist``，版本太低了，适配不了就换了。
3. rbbitMQ安装时要安装Erlang，我这边安装的版本是``otp_win64_20.3``，也就是``erl9.3``，要不然不适配。