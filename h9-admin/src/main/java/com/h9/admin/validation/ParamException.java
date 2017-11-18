package com.h9.admin.validation;

/**
 * @author: George
 * @date: 2017/11/18 11:31
 */
public class ParamException extends RuntimeException{
    public ParamException() {
    }

    public ParamException(String message) {
        super(message);
    }
}
