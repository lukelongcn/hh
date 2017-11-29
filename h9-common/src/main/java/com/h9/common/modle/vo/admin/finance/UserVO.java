package com.h9.common.modle.vo.admin.finance;

import com.h9.common.db.entity.*;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * @author: George
 * @date: 2017/11/29 11:29
 */
public class UserVO {
    private UserInfoVO userInfoVO;
    private UserExtendsVO userExtendsVO;
    private UserBankVO userBankVO;
    private UserAddressVO userAddressVO;

    public UserVO() {
    }

    public UserVO(User user,UserExtends userExtends, UserBank userBank, Address address) {
        this.userInfoVO = new UserInfoVO(user);
        this.userExtendsVO = new UserExtendsVO(userExtends);
        this.userBankVO = new UserBankVO(userBank);
        this.userAddressVO = new UserAddressVO(address);
    }

    public UserInfoVO getUserInfoVO() {
        return userInfoVO;
    }

    public void setUserInfoVO(UserInfoVO userInfoVO) {
        this.userInfoVO = userInfoVO;
    }

    public UserExtendsVO getUserExtendsVO() {
        return userExtendsVO;
    }

    public void setUserExtendsVO(UserExtendsVO userExtendsVO) {
        this.userExtendsVO = userExtendsVO;
    }

    public UserBankVO getUserBankVO() {
        return userBankVO;
    }

    public void setUserBankVO(UserBankVO userBankVO) {
        this.userBankVO = userBankVO;
    }

    public UserAddressVO getUserAddressVO() {
        return userAddressVO;
    }

    public void setUserAddressVO(UserAddressVO userAddressVO) {
        this.userAddressVO = userAddressVO;
    }
}

class UserInfoVO {

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "微信openId")
    private String openId;

    @ApiModelProperty(value = "用户头像")
    private String avatar ;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "最后登陆时间")
    private Date lastLoginTime;

    @ApiModelProperty(value = "登录次数")
    private Integer loginCount;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "是否为管理员")
    private String isAdmin;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = (status!=null&&status==User.StatusEnum.ENABLED.getId())?"正常":"禁用";
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = (isAdmin!=null&&isAdmin==User.IsAdminEnum.ADMIN.getId())?"管理员":"普通用户";
    }

    public UserInfoVO() {
    }

    public UserInfoVO(User user) {
        BeanUtils.copyProperties(user,this);
    }
}

class UserExtendsVO {

    @ApiModelProperty(value = "性别")
    private String sex ;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "情感")
    private String marriageStatus;

    @ApiModelProperty(value = "学历")
    private String education;

    @ApiModelProperty(value = "职业")
    private String job;

    public String getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex==null?null:UserExtends.SexEnum.getNameByCode(sex);
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getMarriageStatus() {
        return marriageStatus;
    }

    public void setMarriageStatus(String marriageStatus) {
        this.marriageStatus = marriageStatus;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public UserExtendsVO() {
    }

    public UserExtendsVO(UserExtends userExtends) {
        BeanUtils.copyProperties(userExtends,this);
    }
}

class UserBankVO {

    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(32) default '' COMMENT '持卡人名'")
    private String name;

    @Column(name = "no", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '银行卡号'")
    private String no;

    @Column(name = "provice", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '开户省'")
    private String province;

    @Column(name = "city", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '开户城市'")
    private String city;

    @Column(name = "status", nullable = false, columnDefinition = "tinyint default 1 COMMENT '1:正常 2禁用 3解绑'")
    private Integer status = 1;

    @Column(name = "default_select", nullable = false, columnDefinition = "int default 0 COMMENT '默认选择的银行卡 1 默认 0 为不是默认'")
    private Integer defaultSelect;

    /**
     * description: 对应CardInfo 表id
     */
    @Column(name = "card_id")
    private Long cardId;

    public UserBankVO() {
    }

    public UserBankVO(UserBank userBank) {
    }
}

class UserAddressVO {
    public UserAddressVO() {
    }

    public UserAddressVO(Address address) {
    }
}

