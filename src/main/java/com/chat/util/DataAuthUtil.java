package com.chat.util;

import org.apache.commons.lang3.StringUtils;

public class DataAuthUtil {
    //如果传的是一个对象，反射获取属性
    public static void authParams(Object ... objs){
        if (objs==null||objs.length==0){
            throw new RuntimeException("参数错误");
        }
        for (Object obj : objs) {
            if (obj instanceof String){
                if (StringUtils.isEmpty((String) obj)){
                    throw new RuntimeException("参数错误");
                }
            }else {
                if (obj==null){
                    throw new RuntimeException("参数错误");
                }
            }
        }
    }
}
