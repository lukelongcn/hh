package com.h9.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * SampleUser:刘敏华 shadow.liu@hey900.com
 * DateDeserializer: 2017-08-07
 * Time: 16:28
 */

@ApiModel("结果")
public class Result<T> {

    @ApiModelProperty(value = "状态码")
    private int code;  // 0 成功 1 失败
    @ApiModelProperty(value = "提示信息")
    private String msg;
    @ApiModelProperty(value = "数据")
    private T data;

    public Result(int code, String msg, T t) {
        this.code = code;
        this.msg = msg;
        this.data = t;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static Result success() {
        return new Result(SUCCESS_CODE, "成功");
    }

    public static <T>Result<T> success(T t) {
        return new Result<>(SUCCESS_CODE, "成功", t);
    }

    public static <T> Result<T> success(String msg, T t) {
        return new Result<>(0, msg, t);
    }

    public static <T>Result<T> success(String msg) {
        return new Result<>(SUCCESS_CODE, msg, null);
    }

    public static <T>Result<T> fail() {
        return new Result<>(FAILED_CODE, "失败");
    }

    public static <T> Result<T> fail(String msg,T t) {
        return new Result<>(FAILED_CODE, msg,t);
    }




    public static <T>Result<T> fail(String msg) {
        return new Result<>(FAILED_CODE, msg);
    }

    public static final int SUCCESS_CODE = 0;
    public static final int FAILED_CODE = 1;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    public Result(){}

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
