package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 项目名：    mmall_learning
 * 包名：      com.mmall.common
 * 文件名：    RedisPool
 * 创建时间:   2020-04-12-11:08
 *
 * @author zhangsiqi
 * 描述：
 */

public class RedisPool {
    /**
     * jedis连接池
     */
    private static JedisPool pool;
    /**
     * 最大连接数
     */
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total", "20"));
    /**
     * jedispool中最大的idle状态的jedis实例个数
     */
    private static Integer maxIdel = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle", "10"));
    private static Integer minIdel = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle", "2"));
    /**
     * 在borrow一个jedis实例的时候，是否要紧盯验证操作，如果赋值true，则得到的Jedis实例肯定是可以动用的。
     */
    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow", "true"));
    /**
     * 在return一个jedis实例的时候，是否要紧盯验证操作，如果赋值true，则放回的Jedis实例肯定是可以动用的。
     */
    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return", "true"));

    private static String redisIp = PropertiesUtil.getProperty("redis.ip");
    private static Integer redisPort = Integer.parseInt(PropertiesUtil.getProperty("redis.port"));


    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdel);
        config.setMinIdle(minIdel);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnBorrow);
        // 连接耗尽时，是否阻塞，false抛出异常，true阻塞直到超时。默认为true
        config.setBlockWhenExhausted(true);

        pool = new JedisPool(config, redisIp, redisPort, 1000 * 2);
    }

    /**
     * 每次类加载创建连接池
     */
    static {
        initPool();
    }

    // 获得一个连接
    public static Jedis getJedis() {
        return pool.getResource();
    }

    public static void returnBrokenResource(Jedis jedis) {
        pool.returnBrokenResource(jedis);
    }

    public static void returnResource(Jedis jedis) {
        pool.returnResource(jedis);
    }

    public static void main(String[] args) {
        Jedis jedis = pool.getResource();
        jedis.setnx("a", "b");
        System.out.println(jedis.get("a"));
        returnResource(jedis);
        // 临时使用，销毁连接池中所有链接
        pool.destroy();
        System.out.println("jedis end");
    }

}
