package com.h9.common.modle.vo.admin.finance;

import com.h9.common.db.entity.withdrawals.WithdrawalsRecord;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: George
 * @date: 2017/11/9 14:06
 */
public class WithdrawRecordVO {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "订单编号")
    private Long orderId;

    @Column(name = "user_id", columnDefinition = "bigint(20) default null COMMENT '用户id'")
    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "银行名称")
    private String bankName;

    @ApiModelProperty(value = "银行卡号")
    private String bankCardNo;

    @ApiModelProperty(value = "提现金额")
    private BigDecimal money = new BigDecimal(0);

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


    @ApiModelProperty(value = "转账成功时间")
    private Date finishTime;

    @ApiModelProperty(value = "提现状态 ： 1提现中  2银行转账中 3银行转账完成 ，4 提现异常,5退回")
    private Integer status = 1;

    @ApiModelProperty(value = "开户省")
    private String provice;

    @ApiModelProperty(value = "开户城市")
    private String city;

    @ApiModelProperty(value = "提现描述")
    private String remarks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProvice() {
        return provice;
    }

    public void setProvice(String provice) {
        this.provice = provice;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

/* public static WithdrawRecordVO toWithdrawRecordVO(Map map){
     *//*   WithdrawRecordVO withdrawRecordVO = new WithdrawRecordVO();
        withdrawRecordVO.setBankCardNo(MapUtils.getString(map,"no"));
        withdrawRecordVO.setBankName(MapUtils.getString(map,"bank_name"));
        BeanUtils.
        withdrawRecordVO.setCity(MapUtils.getString(map,"city"));
        withdrawRecordVO.setProvince(MapUtils.getString(map,"provice"));
        withdrawRecordVO.setCreateTime(DateUtil.formatDate(MapUtils.getString(map,"create_time"),DateUtil.FormatType.SECOND));
        withdrawRecordVO.setFinishTime(DateUtil.formatDate(MapUtils.getString(map,"finish_time"),DateUtil.FormatType.SECOND));
        withdrawRecordVO.setId(MapUtils.getLong(map,"id"));
        withdrawRecordVO.setMoney(new BigDecimal(MapUtils.getString(map,"money")));*//*
    }*/

    public static List<WithdrawRecordVO> toWithdrawRecordVOs(List<Map> maps) throws InvocationTargetException, IllegalAccessException {
        if(maps==null){
            return null;
        }else{
            List<WithdrawRecordVO> withdrawRecordVOS = new ArrayList<>();
            for(Map map:maps){
                WithdrawRecordVO withdrawRecordVO = new WithdrawRecordVO();
                org.apache.commons.beanutils.BeanUtils.populate(withdrawRecordVO,map);
                withdrawRecordVOS.add(withdrawRecordVO);
            }
            return withdrawRecordVOS;
        }
    }

    public WithdrawRecordVO() {
    }

    public WithdrawRecordVO(WithdrawalsRecord withdrawalsRecord) {
        BeanUtils.copyProperties(withdrawalsRecord,this);
        this.bankCardNo = withdrawalsRecord.getBankNo();
        this.provice = withdrawalsRecord.getProvince();
    }

}
