package com.pace2car.springbootdemo.demo;

import org.joda.time.DateTimeConstants;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.Locale;

/**
 * @author Chen Jiahao
 * @since 2020/9/28 10:33
 */
public class LocalDateTimeTest {
    public static void main(String[] args) {
        // ISO标准
        WeekFields weekFields = WeekFields.ISO;
//        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        LocalDateTime now = LocalDateTime.now();
        // 获取年的第几周
        int weekNumber = now.get(weekFields.weekOfWeekBasedYear());
        System.out.println(weekNumber);
//        System.out.println(now.getMonthValue());

        // 获取下一周的年的第几周
        System.out.println(now.plusWeeks(1).get(weekFields.weekOfWeekBasedYear()));

        // 获取本周的某一天
        System.out.println(now.with(DayOfWeek.MONDAY));

        System.out.println(now.with(WeekFields.of(Locale.CHINA).getFirstDayOfWeek()));
        System.out.println(now.with(WeekFields.of(Locale.FRANCE).getFirstDayOfWeek()));
        System.out.println(now.with(WeekFields.of(Locale.US).getFirstDayOfWeek()));

    }
}
