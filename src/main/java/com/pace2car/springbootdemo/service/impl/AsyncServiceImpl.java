package com.pace2car.springbootdemo.service.impl;

import com.pace2car.springbootdemo.service.AsyncService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Pace2Car
 * @date 2019/1/10 14:39
 */
@Service
public class AsyncServiceImpl implements AsyncService {

    private Logger logger = LogManager.getLogger("asyncService");

    @Override
    @Async("asyncServiceExecutor")
    public void executeAsync() {
        logger.info("start executeAsync");
        try{
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        logger.info("end executeAsync");

    }
}
