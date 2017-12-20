package com.h9.common.common;

import com.h9.common.base.Result;

/**
 * Created with IntelliJ IDEA.
 * Description:用于业务回滚
 * ServiceException:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/7
 * Time: 10:22
 */
public class ServiceException extends Exception{
    private int code = 1;

    public ServiceException(int code) {
        this.code = code;
    }

    public ServiceException(Result message) {
        this(message.getCode(), message.getMsg());
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(int code,String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
