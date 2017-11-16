package com.transfer.db.entity;


import com.h9.common.db.entity.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * UserInfo:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/26
 * Time: 11:16
 */

@Entity
@Table(name = "Userinfo")
public class UserInfo {

    @Id
    @Column(name = "Userid")
    private Long id;

    @Column(name = "mobile")
    private String phone;

    @Column(name = "Password")
    private String password;

    @Column(name = "Username")    //用户昵称
    private String Username;

    @Column(name = "Userimg")
    private String Userimg;

    @Column(name = "MoneyCount")    ///用户余额
    private BigDecimal MoneyCount;

    @Column(name = "ImgId")
    private Long ImgId;

    @Column(name = "psw_state")
    private Integer psw_state;              //无值

    @Column(name = "IntegralCount")
    private Integer IntegralCount;      //V币数量

    @Column(name = "Vip")
    private Integer Vip;

    @Column(name = "CardCount")
    private Integer CardCount;           //无值不用考虑

    @Column(name = "Quota")
    private Integer Quota;

    @Column(name = "UnionPayState")
    private Integer UnionPayState;                //无值

    @Column(name = "Vipimg")
    private String Vipimg;    // 无值

    @Column(name = "OpenID")
    private String OpenID;

    @Column(name = "Growth")  // 成长值  无值
    private Integer Growth;

    @Column(name = "RegisterTime")
    private Date RegisterTime;

    @Column(name = "UserGuid")
    private String UserGuid;

    public UserInfo() {
    }


    public User corvert(){
        User user = new User();

        user.setPhone(phone);
        user.setCreateTime(RegisterTime);
        user.setAvatar(Userimg);
        user.setNickName(Username);
        user.setLoginCount(1);
        user.setLastLoginTime(RegisterTime);
        user.setPassword(password);
        user.setUuid(UserGuid);
        user.setH9UserId(id);
        user.setOpenId(OpenID);
        return user;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUserimg() {
        return Userimg;
    }

    public void setUserimg(String userimg) {
        Userimg = userimg;
    }

    public BigDecimal getMoneyCount() {
        return MoneyCount;
    }

    public void setMoneyCount(BigDecimal moneyCount) {
        MoneyCount = moneyCount;
    }

    public Long getImgId() {
        return ImgId;
    }

    public void setImgId(Long imgId) {
        ImgId = imgId;
    }

    public Integer getPsw_state() {
        return psw_state;
    }

    public void setPsw_state(Integer psw_state) {
        this.psw_state = psw_state;
    }

    public Integer getIntegralCount() {
        return IntegralCount;
    }

    public void setIntegralCount(Integer integralCount) {
        IntegralCount = integralCount;
    }

    public Integer getVip() {
        return Vip;
    }

    public void setVip(Integer vip) {
        Vip = vip;
    }

    public Integer getCardCount() {
        return CardCount;
    }

    public void setCardCount(Integer cardCount) {
        CardCount = cardCount;
    }

    public Integer getQuota() {
        return Quota;
    }

    public void setQuota(Integer quota) {
        Quota = quota;
    }

    public Integer getUnionPayState() {
        return UnionPayState;
    }

    public void setUnionPayState(Integer unionPayState) {
        UnionPayState = unionPayState;
    }

    public String getVipimg() {
        return Vipimg;
    }

    public void setVipimg(String vipimg) {
        Vipimg = vipimg;
    }

    public String getOpenID() {
        return OpenID;
    }

    public void setOpenID(String openID) {
        OpenID = openID;
    }

    public Integer getGrowth() {
        return Growth;
    }

    public void setGrowth(Integer growth) {
        Growth = growth;
    }

    public Date getRegisterTime() {
        return RegisterTime;
    }

    public void setRegisterTime(Date registerTime) {
        RegisterTime = registerTime;
    }

    public String getUserGuid() {
        return UserGuid;
    }

    public void setUserGuid(String userGuid) {
        UserGuid = userGuid;
    }
}
