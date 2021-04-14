package com.chat.conf;

import com.chat.common.Const;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object token = request.getSession().getAttribute("token_" + Const.REGISTER_CODE);
        if (token != null && (boolean) token) {
            return true;
        }
        response.sendRedirect("/safe.html");
        return false;
    }
}
