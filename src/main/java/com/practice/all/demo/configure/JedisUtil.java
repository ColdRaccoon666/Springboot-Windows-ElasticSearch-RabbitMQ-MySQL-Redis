package com.practice.all.demo.configure;

import redis.clients.jedis.Jedis;

public class JedisUtil {

    /**
     * 查询单个redis的一个value
    * @param id
     * @return
     */

    public String queryJedis(String id){
        Jedis jedis = null;
        try {
            jedis = new Jedis("127.0.0.1", 6379);
            // 连接测试
            System.out.println(jedis.ping());
            //设置 redis 字符串数据
            /*jedis.set("znsdkey", "www.znsd.com");*/
            System.out.println(jedis.get(id));
            String name = jedis.get(id);
            return name;
            // 获取存储的数据并输出
            /*System.out.println("redis 存储的字符串为: "+ jedis.get("znsdkey"));*/
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 删除一个key
     * @param id
     */

    public void delJedis(String id){
        Jedis jedis = null;
        try {
            jedis = new Jedis("127.0.0.1", 6379);
            // 连接测试
            System.out.println(jedis.ping());
            System.out.println(jedis.get(id));
            jedis.del(id);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 给redis设置一个key
     * @param id
     * @param name
     */

    public void setJedis(String id,String name){
        Jedis jedis = null;
        try {
            jedis = new Jedis("127.0.0.1", 6379);
            // 连接测试
            System.out.println(jedis.ping());
            jedis.set(id,name);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
