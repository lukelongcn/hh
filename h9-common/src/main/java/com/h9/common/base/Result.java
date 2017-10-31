package com.h9.common.base;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * SampleUser:刘敏华 shadow.liu@hey900.com
 * DateDeserializer: 2017-08-07
 * Time: 16:28
 */
public class Result<T> {


    private int code;  // 0 成功 1 失败
    private String msg;
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
        return new Result(SUCCESS, "成功");
    }

    public static <T> Result success(T t) {
        return new Result(SUCCESS, "成功", t);
    }

    public static <T> Result success(String msg, T t) {
        return new Result(0, msg, t);
    }

    public static  Result success(String msg) {
        return new Result(SUCCESS, msg, null);
    }

    public static <T> Result fail() {
        return new Result(FAILD, "失败");
    }

    public static <T> Result fail(String msg,T t) {
        return new Result(FAILD, msg,t);
    }




    public static <T> Result fail(String msg) {
        return new Result(FAILD, msg);
    }

    public static final int SUCCESS = 0;
    public static final int FAILD = 1;

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
