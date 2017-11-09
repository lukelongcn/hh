package com.h9.common.modle.vo;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * Config:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/9
 * Time: 14:25
 */
public class Config {
    private String key;
    private String val;

    public Config(String key, String name) {
        this.key = key;
        this.val = name;
    }

    public Config() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
