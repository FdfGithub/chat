package com.chat.util;

import java.util.regex.Pattern;

public class RegularUtil {
    private static final String emailRegex = "/^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$/";
    private static final String telRegex = "/^1[3456789]\\d{9}$/";

    //判断是否是邮箱
    public static boolean isEmail(String str){
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(str).find();
    }
    //判断是否是手机号
    public static boolean isTel(String str){
        Pattern pattern = Pattern.compile(telRegex);
        return pattern.matcher(str).find();
    }
    //判断密码是否符合要求
}
