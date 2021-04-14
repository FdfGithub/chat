package com.chat.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    private static final String DEFAULT = "yyyy-MM-dd HH:mm:ss";

    //date转str
    public static String dateTimeToStr(LocalDateTime date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(date);
    }

    public static String dateTimeToStr(LocalDateTime date) {
        return dateTimeToStr(date, DEFAULT);
    }

    //str转date
    public static LocalDateTime strToDateTime(String str, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(str, formatter);
    }

    public static LocalDate strToDate(String str, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(str, formatter);
    }


    public static String dateToStr(LocalDate date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(date);
    }

    public static LocalDateTime strToDateTime(String str) {
        return strToDateTime(str, DEFAULT);
    }

//    public static void main(String[] args) {
//        String str = dateToStr(LocalDate.now(), "yyyy-MM-dd");
//        System.out.println(str);
//    }

    /*
     * 上午9:00
     *
     * */
    public static String returnNow(LocalDateTime dateTime) {
        int hour = dateTime.getHour();
        String minute = dateTimeToStr(dateTime, "mm");
        String prefix = "上午";
        if (hour > 12) {
            prefix = "下午";
            hour -= 12;
        } else if (hour == 12) {
            prefix = "中午";
        }
        return prefix + hour + ":" + minute;
    }

    public static String returnOld(String oldTime) {
        LocalDateTime oldT = DateTimeUtil.strToDateTime(oldTime);
        LocalDateTime newT = LocalDateTime.now();
        int week = oldT.getDayOfWeek().getValue();
        Duration duration = Duration.between(oldT, newT);
        long days = duration.toDays();
        if (days<0){
            throw new RuntimeException("无法处理的时间");
        }
        //和当前时间做对比
        if (days < 1) {//同一天内，不用变化
            return returnNow(oldT);
        } else if (days == 1) {//不同天的，昨天
            return "昨天";
        } else if (days > 1 && days <= 7 - week) {//同一个星期的：星期一、星期二等等
            //打印星期几
            switch (week) {
                case 1:
                    return "星期一";
                case 2:
                    return "星期二";
                case 3:
                    return "星期三";
                case 4:
                    return "星期四";
                case 5:
                    return "星期五";
                case 6:
                    return "星期六";
                case 7:
                    return "星期七";
            }
        }
        //再下去就指定日期
        return oldT.toLocalDate().toString();
    }
}
