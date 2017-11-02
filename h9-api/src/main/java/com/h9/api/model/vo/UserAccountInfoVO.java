package com.h9.api.model.vo;

import com.h9.common.db.entity.User;
import com.h9.common.db.entity.UserAccount;

/**
 * Created by itservice on 2017/11/2.
 */
public class UserAccountInfoVO {
    private String balance;
    private String vb;
    private String cardNum;
    private String imgUrl;
    private String nickName;

    public UserAccountInfoVO (User user, UserAccount userAccount){
        this.balance = userAccount.getBalance().toString();
        this.vb = userAccount.getvCoins().toString();
        this.cardNum = "2";
        this.imgUrl = user.getAvatar();
        this.nickName = user.getNickName();
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
