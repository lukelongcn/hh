package com.h9.api.provider.model;

import com.h9.common.db.entity.user.User;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * WeChatUser:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/1
 * Time: 14:25
 */
public class WeChatUser {
    private String openid;
    private String nickname;
    private int sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private String unionid;

    public User convert(){
        User user = new User();
        user.setOpenId(getOpenid());
        user.setAvatar(getHeadimgurl());
        user.setNickName(getNickname());
        user.setUnionId(getUnionid());
        return user;
    }



    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
