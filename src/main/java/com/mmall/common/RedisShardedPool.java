package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.*;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名：    mmall_learning
 * 包名：      com.mmall.common
 * 文件名：    RedisShardedPool
 * 创建时间:   2020-04-13-21:05
 *
 * @author zhangsiqi
 * 描述：
 */

public class RedisShardedPool {
    /**
     * Sharaded jedis连接池
     */
    private static ShardedJedisPool pool;
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

    private static String redis1Ip = PropertiesUtil.getProperty("redis1.ip");
    private static Integer redis1Port = Integer.parseInt(PropertiesUtil.getProperty("redis1.port"));
    private static String redis2Ip = PropertiesUtil.getProperty("redis2.ip");
    private static Integer redis2Port = Integer.parseInt(PropertiesUtil.getProperty("redis2.port"));


    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdel);
        config.setMinIdle(minIdel);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnBorrow);
        // 连接耗尽时，是否阻塞，false抛出异常，true阻塞直到超时。默认为true
        config.setBlockWhenExhausted(true);

        JedisShardInfo info1 = new JedisShardInfo(redis1Ip, redis1Port, 1000 * 2);
        JedisShardInfo info2 = new JedisShardInfo(redis2Ip, redis2Port, 1000 * 2);
        List<JedisShardInfo> jedisShardInfoList = new ArrayList<JedisShardInfo>(2);
        jedisShardInfoList.add(info1);
        jedisShardInfoList.add(info2);

        pool = new ShardedJedisPool(config, jedisShardInfoList, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
    }

    /**
     * 每次类加载创建连接池
     */
    static {
        initPool();
    }

    // 获得一个连接
    public static ShardedJedis getJedis() {
        return pool.getResource();
    }

    public static void returnBrokenResource(ShardedJedis jedis) {
        pool.returnBrokenResource(jedis);
    }

    public static void returnResource(ShardedJedis jedis) {
        pool.returnResource(jedis);
    }

    public static void main(String[] args) {
        ShardedJedis jedis = pool.getResource();
        for (int i = 0; i < 10; i++) {
            jedis.set("key" + i, "value" + i);
        }
        returnResource(jedis);
        System.out.println("Sharded Jedis end");
    }
}
