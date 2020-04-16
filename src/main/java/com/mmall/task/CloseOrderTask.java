package com.mmall.task;

import com.mmall.service.IOrderService;
import com.mmall.util.BigDecimalUtil;
import com.mmall.util.PropertiesUtil;
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
    @Scheduled(cron = "0 */1 * * *?")
    public void closeOrderTaskV1() {
        log.info("关闭订单定时任务启动");
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour", "2"));
        iOrderService.closeOrder(hour);
        log.info("关闭订单定时任务结束");
    }
}
