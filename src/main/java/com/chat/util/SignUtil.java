package com.chat.util;

import java.time.LocalDate;

public class SignUtil {

    public static String returnSign(LocalDate date) {
        int month = date.getMonthValue();
        int dayOfMonth = date.getDayOfMonth();
        String sign = null;
        switch (month) {
            case 1:
                sign = dayOfMonth > 19 ? "水瓶座" : "摩羯座";
                break;
            case 2:
                sign = dayOfMonth > 18 ? "双鱼座" : "水瓶座";
                break;
            case 3:
                sign = dayOfMonth > 20 ? "白羊座" : "双鱼座";
                break;
            case 4:
                sign = dayOfMonth > 19 ? "金牛座" : "白羊座";
                break;
            case 5:
                sign = dayOfMonth > 20 ? "双子座" : "金牛座";
                break;
            case 6:
                sign = dayOfMonth > 21 ? "巨蟹座" : "双子座";
                break;
            case 7:
                sign = dayOfMonth > 22 ? "狮子座" : "巨蟹座";
                break;
            case 8:
                sign = dayOfMonth > 22 ? "处女座" : "狮子座";
                break;
            case 9:
                sign = dayOfMonth > 22 ? "天秤座" : "处女座";
                break;
            case 10:
                sign = dayOfMonth > 23 ? "天蝎座" : "天秤座";
                break;
            case 11:
                sign = dayOfMonth > 22 ? "射手座" : "天蝎座";
                break;
            case 12:
                sign = dayOfMonth > 21 ? "摩羯座" : "射手座";
                break;
        }
        return sign;
    }
}
