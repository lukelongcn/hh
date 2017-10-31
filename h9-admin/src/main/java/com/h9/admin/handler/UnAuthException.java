package com.h9.admin.handler;

/**
 * description: 用户没有访问权限时使用此异常
 */
public class UnAuthException extends RuntimeException{
    public UnAuthException(){}
    public UnAuthException(String msg){
        super(msg);
    }
}
