package com.h9.handle;

/**
 * description: 用户没有访问权限时使用此异常
 */
public class UnAuthException extends RuntimeException{
    private int code = 401;

    public UnAuthException(){}

    public UnAuthException(int code,String msg){
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
