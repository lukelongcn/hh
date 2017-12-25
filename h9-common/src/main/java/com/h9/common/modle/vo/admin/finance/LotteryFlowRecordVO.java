package com.h9.common.modle.vo.admin.finance;

import com.h9.common.db.entity.LotteryFlowRecord;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: George
 * @date: 2017/11/8 13:52
 */
public class LotteryFlowRecordVO {
    @ApiModelProperty(value = "id" )
    private Long id;

    @ApiModelProperty(value = "昵称" )
    private String nickName;

    @ApiModelProperty(value = "兑奖码" )
    private String code;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "用户余额" )
    private BigDecimal balance = new BigDecimal(0);

    @ApiModelProperty(value = "金额" )
    private BigDecimal money = new BigDecimal(0);

    @ApiModelProperty(value = "转账时间" )
    private Date createTime;

    @ApiModelProperty(value = "发奖时间" )
    private Date transferTime;

    @ApiModelProperty(value = "转账状态，1：成功，2：失败" )
    private Integer status;

    @ApiModelProperty(value = "操作人" )
    private String operator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public LotteryFlowRecordVO() {
    }

    public LotteryFlowRecordVO(LotteryFlowRecord lotteryFlowRecord,BigDecimal balance) {
        BeanUtils.copyProperties(lotteryFlowRecord, this);
        this.operator = lotteryFlowRecord.getUser() == null ? null :lotteryFlowRecord.getUser().getNickName();
        this.balance = balance;
    }

    public static List<LotteryFlowRecordVO> toLotteryFlowRecordVOs(List<Map> maps) throws InvocationTargetException, IllegalAccessException {
        if(maps==null){
            return null;
        }else{
            List<LotteryFlowRecordVO> lotteryFlowFinanceVOS = new ArrayList<>();
            for(Map map:maps){
                LotteryFlowRecordVO lotteryFlowFinanceVO = new LotteryFlowRecordVO();
                org.apache.commons.beanutils.BeanUtils.populate(lotteryFlowFinanceVO,map);
                lotteryFlowFinanceVOS.add(lotteryFlowFinanceVO);
            }
            return lotteryFlowFinanceVOS;
        }
    }
}
