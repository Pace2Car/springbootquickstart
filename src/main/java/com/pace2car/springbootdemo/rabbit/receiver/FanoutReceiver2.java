package com.pace2car.springbootdemo.rabbit.receiver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * fanout消息接受者
 * @author Pace2Car
 * @date 2019/3/28 10:08
 */
@Component
@RabbitListener(queues = "fanout.B")
public class FanoutReceiver2 {

    private Logger logger = LogManager.getLogger("fanoutReceiver2");

    @RabbitHandler
    public void process(String hello) {
        logger.debug("fanoutReceiver B:" + hello);
    }

}
