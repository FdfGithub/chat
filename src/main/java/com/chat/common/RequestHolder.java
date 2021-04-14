package com.chat.common;

import javax.servlet.http.HttpSession;

public class RequestHolder {
    private final static ThreadLocal<HttpSession> requestHolder = new ThreadLocal<>();

    public static void add(HttpSession session){
        requestHolder.set(session);
    }

    public static HttpSession getSession(){
        return requestHolder.get();
    }

    public static void remove(){
        requestHolder.remove();
    }
}
