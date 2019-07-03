package com.pace2car.springbootdemo.rabbit.sender;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * fanout消息发送者
 *
 * @author Pace2Car
 * @date 2019/3/28 10:05
 */
@Component
public class FanoutSender {

    private Logger logger = LogManager.getLogger("fanout");

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String context = "hi, i am fanout message";
        logger.debug("sender:" + context);
        rabbitTemplate.convertAndSend("fanoutExchange", "", context);
    }

}
