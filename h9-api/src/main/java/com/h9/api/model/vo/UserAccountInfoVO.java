package com.h9.api.model.vo;

import com.h9.common.db.entity.User;
import com.h9.common.db.entity.UserAccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by itservice on 2017/11/2.
 */
public class UserAccountInfoVO {
    private String balance;
    private String vb;
    private String cardNum;
    private String imgUrl;
    private String nickName;
    private Integer withdrawalCount;
    private String tel;

    public UserAccountInfoVO (User user, UserAccount userAccount,String cardNum,String tel){
        this.balance = userAccount.getBalance().toString();
        this.vb = userAccount.getvCoins().toString();
        this.cardNum = cardNum;
        this.imgUrl = user.getAvatar();
        this.nickName = user.getNickName();
        this.withdrawalCount = 10000;
        this.tel = tel;
    }


    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getWithdrawalCount() {
        return withdrawalCount;
    }

    public void setWithdrawalCount(Integer withdrawalCount) {
        this.withdrawalCount = withdrawalCount;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getVb() {
        return vb;
    }

    public void setVb(String vb) {
        this.vb = vb;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }


}
