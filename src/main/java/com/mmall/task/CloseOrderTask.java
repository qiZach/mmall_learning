package com.mmall.task;

import com.mmall.common.Const;
import com.mmall.service.IOrderService;
import com.mmall.util.BigDecimalUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.util.RedisShardedPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 项目名：    mmall_learning
 * 包名：      com.mmall.task
 * 文件名：    CloseOrderTask
 * 创建时间:   2020-04-16-15:48
 *
 * @author zhangsiqi
 * 描述：
 */

@Slf4j
@Component
public class CloseOrderTask {

    @Autowired
    private IOrderService iOrderService;

    /**
     * 每一分钟执行一次
     */
    //@Scheduled(cron = "0 */1 * * *?")
    public void closeOrderTaskV1() {
        log.info("关闭订单定时任务启动");
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour", "2"));
        //iOrderService.closeOrder(hour);
        log.info("关闭订单定时任务结束");
    }

    public void closeOrderTaskV2() {
        log.info("关闭订单定时任务启动");
        // 超时时间五秒
        long lockTimeout = Long.parseLong(PropertiesUtil.getProperty("lock.timeout"));
        // 设置锁 key: CLOSE_ORDER_TASK_LOCK value: System.currentTimeMillis + lockTimeout
        Long setnxResult = RedisShardedPoolUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,
                String.valueOf(System.currentTimeMillis() + lockTimeout));
        if (setnxResult != null && setnxResult.intValue() == 1) {
            // 返回值是1,成功获得锁, 进行关闭订单业务
            closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        } else {
            log.info("没有获得分布式锁：{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        }
        log.info("关闭订单定时任务结束");
    }

    private void closeOrder(String lockName) {
        // 有效期五十秒
        RedisShardedPoolUtil.expire(lockName, 5);
        log.info("获取{},ThreadName:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour", "2"));
        iOrderService.closeOrder(hour);
        RedisShardedPoolUtil.del(lockName);
        log.info("释放{},ThreadName:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
    }
}
