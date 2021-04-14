package com.chat.common;

public enum StatusCode {
    SUCCESS(2000,"成功"),
    ERROR(2001,"失败"),
    NO_REGISTER(203,"用户未注册"),
    REGISTERED(204,"用户已存在")
    ;

    private int code;
    private String msg;

    StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
