package com.pace2car.springbootdemo.rabbit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * topic消息接受者
 * @author Pace2Car
 * @date 2019/3/28 10:08
 */
@Component
@RabbitListener(queues = "topic.message")
public class TopicReceiver {

    private Logger logger = LogManager.getLogger("topicReceiver");

    @RabbitHandler
    public void process(String hello) {
        logger.debug("topicRecerver 1:" + hello);
    }

}