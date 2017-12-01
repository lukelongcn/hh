package com.h9.common.modle.vo.admin.finance;

import java.util.List;

/**
 * @author: George
 * @date: 2017/11/29 11:29
 */
public class UserVO {
    private UserInfoVO userInfoVO;
    private UserExtendsInfoVO userExtendsInfoVO;
    private List<UserBankInfoVO> userBankInfoVOList;
    private List<UserAddressInfoVO> userAddressInfoVOList;

    public UserVO() {
    }

    public UserInfoVO getUserInfoVO() {
        return userInfoVO;
    }

    public void setUserInfoVO(UserInfoVO userInfoVO) {
        this.userInfoVO = userInfoVO;
    }

    public UserExtendsInfoVO getUserExtendsInfoVO() {
        return userExtendsInfoVO;
    }

    public void setUserExtendsInfoVO(UserExtendsInfoVO userExtendsInfoVO) {
        this.userExtendsInfoVO = userExtendsInfoVO;
    }

    public List<UserBankInfoVO> getUserBankInfoVOList() {
        return userBankInfoVOList;
    }

    public void setUserBankInfoVOList(List<UserBankInfoVO> userBankInfoVOList) {
        this.userBankInfoVOList = userBankInfoVOList;
    }

    public List<UserAddressInfoVO> getUserAddressInfoVOList() {
        return userAddressInfoVOList;
    }

    public void setUserAddressInfoVOList(List<UserAddressInfoVO> userAddressInfoVOList) {
        this.userAddressInfoVOList = userAddressInfoVOList;
    }
}











