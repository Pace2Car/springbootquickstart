package com.pace2car.springbootdemo.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 研究多线程任务返回执行结果的使用
 *
 * @author Pace2Car
 * @since 2021/7/20 14:26
 */
public class FutureDemo {

    public static void main(String[] args) {

        List<CompletableFuture<String>> futures = new ArrayList<>();
        futures.forEach(future -> {
            future.isCancelled();
        });
    }
}
