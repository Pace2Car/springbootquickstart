package com.pace2car.springbootdemo.demo;

/**
 * 输出第二天
 *
 * @author Chen Jiahao
 * @since 2021/6/11 11:07
 */
public class NextDayDemo {

    public static void main(String[] args) {
        System.out.println(getNextDay("2020-2-28"));
    }

    private static String getNextDay(String date) {
        String[] dateSplit = date.split("-");
        int year = Integer.valueOf(dateSplit[0]);
        int month = Integer.parseInt(dateSplit[1]);
        int day = Integer.parseInt(dateSplit[2]);

        if (year % 4 == 0 || year % 400 == 0) {
            switch (month) {
                case 2:
                    if (day < 29) {
                        day++;
                    } else {
                        month = 3;
                        day = 1;
                    }
                    break;
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                    if (day < 31) {
                        day++;
                    } else {
                        day = 1;
                        month++;
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    if (day < 30) {
                        day++;
                    } else {
                        day = 1;
                        month++;
                    }
                    break;
                case 12:
                    if (day < 31) {
                        day++;
                    } else {
                        year++;
                        month = 1;
                        day = 1;
                    }
                    break;
            }
        } else {
            switch (month) {
                case 2:
                    if (day < 28) {
                        day++;
                    } else {
                        month = 3;
                        day = 1;
                    }
                    break;
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                    if (day < 31) {
                        day++;
                    } else {
                        day = 1;
                        month++;
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    if (day < 30) {
                        day++;
                    } else {
                        day = 1;
                        month++;
                    }
                    break;
                case 12:
                    if (day < 31) {
                        day++;
                    } else {
                        year++;
                        month = 1;
                        day = 1;
                    }
                    break;
            }
        }
        return year + "-" + month + "-" + day;
    }

}
