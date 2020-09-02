package com.pace2car.springbootdemo.demo;

import cn.hutool.core.convert.Convert;

import java.time.LocalDateTime;

/**
 * @author Chen Jiahao
 * @since 2020/9/2 14:00
 */
public class HutoolTest {
    public static void main(String[] args) {
        String strDate = "2020-09-02";

        LocalDateTime localDateTime = Convert.toLocalDateTime(strDate);
        System.out.println(localDateTime);

    }
}
