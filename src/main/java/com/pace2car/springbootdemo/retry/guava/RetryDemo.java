package com.pace2car.springbootdemo.retry.guava;

import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.google.common.base.Predicates;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @author Pace2Car
 * @date 2019/9/17 14:56
 */
@Slf4j
public class RetryDemo {

    public static void main(String[] args) {
        Callable<Boolean> callable = () -> {
            // do something useful here
            log.info("call...");
            throw new RuntimeException();
        };

        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfRuntimeException()
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                .build();
        try {
            retryer.call(callable);
        } catch (RetryException | ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("do nothing");
        }
    }
}
