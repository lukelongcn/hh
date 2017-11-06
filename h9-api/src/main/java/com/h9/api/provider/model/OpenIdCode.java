package com.h9.api.provider.model;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * OpenIdCode:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/1
 * Time: 14:31
 */
public class OpenIdCode
{
    private String openid;
    private String access_token;
    private String errmsg;
    private String errcode;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }
}
