package com.chat.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServerResponse {
    public static Result createSuccess() {
        return createSuccess(null, null);
    }

    public static Result createSuccess(String message) {
        return createSuccess(message, null);
    }

    public static Result createSuccess(Object data) {
        return createSuccess(null, data);
    }

    public static Result createSuccess(String message, Object data) {
        return create(true, StatusCode.SUCCESS.getCode(), message, data);
    }


    @RestControllerAdvice
    static class BaseExceptionHandler {
        /**
         * 异常处理
         */
        @ExceptionHandler(Exception.class)
        public Result error(Exception e) {
            e.printStackTrace();
            return create(false, StatusCode.ERROR.getCode(), e.getMessage(), null);
        }
    }

    private static Result create(boolean flag, int code, String message, Object data) {
        return Result.getInstance().setFlag(flag).setCode(code).setMessage(message).setData(data);
    }
}
