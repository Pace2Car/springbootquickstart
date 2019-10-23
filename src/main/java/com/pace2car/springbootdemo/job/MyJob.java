package com.pace2car.springbootdemo.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * @author Pace2Car
 * @date 2019/6/27 11:32
 */
public class MyJob implements SimpleJob {

    Logger logger = LoggerFactory.getLogger(MyJob.class);

    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("elasticJob...现在时间：{}", LocalDateTime.now());

        logger.info(String.format("Thread ID: %s, 作业分片总数: %s, " +
                        "当前分片项: %s.当前参数: %s," +
                        "作业名称: %s.作业自定义参数: %s",
                Thread.currentThread().getId(),
                shardingContext.getShardingTotalCount(),
                shardingContext.getShardingItem(),
                shardingContext.getShardingParameter(),
                shardingContext.getJobName(),
                shardingContext.getJobParameter()
        ));
    }
}
