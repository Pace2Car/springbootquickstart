package com.pace2car.springbootdemo.rabbit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息发送者
 * @author Pace2Car
 * @date 2019/3/28 10:05
 */
@Component
public class HelloSender2 {

    private Logger logger = LogManager.getLogger("helloSender2");

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String context = "hello rabbitmq";
        logger.debug("Sender2:" + context);
        rabbitTemplate.convertAndSend("hello", context);
    }

    public void send(Object message) {
        String context = "hello rabbitmq：" + message.toString();
        logger.debug("Sender2:" + context);
        rabbitTemplate.convertAndSend("hello", context);
    }

}
