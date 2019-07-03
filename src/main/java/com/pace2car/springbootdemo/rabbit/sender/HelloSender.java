package com.pace2car.springbootdemo.rabbit.sender;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 消息发送者
 * @author Pace2Car
 * @date 2019/3/28 10:05
 */
@Component
public class HelloSender {

    private Logger logger = LogManager.getLogger("helloSender");

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String context = "hello rabbitmq";
        logger.debug("sender:" + context);
        rabbitTemplate.convertAndSend("hello", context);
    }

    public void send(Object message) {
        String context = "hello rabbitmq：" + message.toString();
        logger.debug("sender:" + context);
        rabbitTemplate.convertAndSend("hello", context);
    }

}
