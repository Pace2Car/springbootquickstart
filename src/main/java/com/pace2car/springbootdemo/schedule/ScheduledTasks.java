package com.pace2car.springbootdemo.schedule;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Pace2Car
 * @date 2019/1/15 10:35
 */
@Component
public class ScheduledTasks {

    private Logger logger = LogManager.getLogger("schediledTasks");

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 定时任务
     * 输出当前时间
     * 启动后延迟一秒输出，间隔60秒
     * 也可通过cron表达式控制@Scheduled(cron="* * * * * * *")
     */
    @Scheduled(initialDelay = 1000, fixedRate = 60000)
    public void reportCurrentTime() {
        logger.info("现在时间 : " + DATE_FORMAT.format(new Date()));
    }
}
