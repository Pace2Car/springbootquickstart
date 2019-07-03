package com.pace2car.springbootdemo.rabbit.receiver;

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
@RabbitListener(queues = "topic.messages")
public class TopicReceiver2 {

    private Logger logger = LogManager.getLogger("topicReceiver2");

    @RabbitHandler
    public void process(String hello) {
        logger.debug("topicReceiver 2:" + hello);
    }

}
