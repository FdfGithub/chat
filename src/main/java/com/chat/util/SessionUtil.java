package com.chat.util;




import com.chat.common.Const;
import com.chat.common.RequestHolder;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionUtil {
    public static void set(String key,Object value, Integer liveTime){
        HttpSession session = RequestHolder.getSession();
        session.setAttribute(key,value);
        session.setMaxInactiveInterval(liveTime);
    }

    public static Object get(String key){
        return RequestHolder.getSession().getAttribute(key);
    }

    public static void remove(String key){
        RequestHolder.getSession().removeAttribute(key);
    }

    public static void authLogin()  {
        if (getUserId()==null){
            throw new RuntimeException("未登录");
        }
    }

    public static Integer getUserId(){
        return (Integer) RequestHolder.getSession().getAttribute(Const.USER_ID);
    }
}
