package com.chat.common;

import java.io.Serializable;

/**
 * 统一返回对象
 */
public class Result implements Serializable {
    private boolean flag;
    private Integer code;
    private String message;
    private Object data;
    private static Result instance;

    private Result() {
    }

    public static Result getInstance(){
        if (instance==null){
            synchronized (Result.class){
                if (instance==null){
                    instance = new Result();
                }
            }
        }
        return instance;
    }

    public boolean isFlag() {
        return flag;
    }

    public Result setFlag(boolean flag) {
        this.flag = flag;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public Result setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }
}
