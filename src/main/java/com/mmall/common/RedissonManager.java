package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 项目名：    mmall_learning
 * 包名：      com.mmall.common
 * 文件名：    RedissionManager
 * 创建时间:   2020-04-17-18:04
 *
 * @author zhangsiqi
 * 描述：
 */
@Component
@Slf4j
public class RedissonManager {
    private Config config = new Config();

    private Redisson redisson = null;

    public Redisson getRedisson() {
        return redisson;
    }

    private static String redis1Ip = PropertiesUtil.getProperty("redis1.ip");
    private static String redis1Port = PropertiesUtil.getProperty("redis1.port");
    private static String redis2Ip = PropertiesUtil.getProperty("redis2.ip");
    private static String redis2Port = PropertiesUtil.getProperty("redis2.port");

    @PostConstruct
    private void init() {
        try {
            config.useSingleServer().setAddress(redis1Ip + ":" + redis1Port);
            redisson = (Redisson) Redisson.create(config);
            log.info("初始化Redisson结束");
        } catch (Exception e) {
            log.error("redisson init error", e);
            e.printStackTrace();
        }
    }

}
