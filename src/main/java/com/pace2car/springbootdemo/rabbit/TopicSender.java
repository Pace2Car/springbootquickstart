package com.pace2car.springbootdemo.rabbit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * topic消息发送者
 *
 * @author Pace2Car
 * @date 2019/3/28 10:05
 */
@Component
public class TopicSender {

    private Logger logger = LogManager.getLogger("topicSender");

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send1() {
        String context = "hi, i am topic message 1";
        logger.debug("Sender:" + context);
        rabbitTemplate.convertAndSend("exchange", "topic.message", context);
    }

    public void send2() {
        String context = "hi, i am topic message 2";
        logger.debug("Sender:" + context);
        rabbitTemplate.convertAndSend("exchange", "topic.messages", context);
    }

}
