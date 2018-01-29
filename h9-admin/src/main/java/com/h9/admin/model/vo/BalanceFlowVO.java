package com.h9.admin.model.vo;

import com.h9.common.db.entity.BalanceFlow;
import com.h9.common.db.entity.account.VCoinsFlow;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * Created by Gonyb on 2017/11/10.
 */
public class BalanceFlowVO {
    @ApiModelProperty(value = "id ")
    private Long id;
    @ApiModelProperty(value = "用户id ")
    private Long userId;
    @ApiModelProperty(value = "订单id ")
    private Long orderId;
    @ApiModelProperty(value ="剩余金额")
    private BigDecimal balance = new BigDecimal(0);
    @ApiModelProperty(value ="变动金额")
    private BigDecimal money = new BigDecimal(0);
    @ApiModelProperty(value ="备注")
    private String remarks ;
    @ApiModelProperty(value ="流水类型")
    private Long flowType;
    @ApiModelProperty(value ="流水类型描述 如果为null 表示类型还未定义")
    private String flowTypeDesc;
    @ApiModelProperty(value ="创建时间")
    private Date createTime;
    @ApiModelProperty(value ="修改时间")
    private Date updateTime;

    public static BalanceFlowVO toBalanceFlowVOByBalanceFlow(BalanceFlow balanceFlow){
        BalanceFlowVO balanceFlowVO = new BalanceFlowVO();
        BeanUtils.copyProperties(balanceFlow,balanceFlowVO);
        return balanceFlowVO;
    }
    public static BalanceFlowVO toBalanceFlowVOByVCoinsFlow(VCoinsFlow vCoinsFlow){
        BalanceFlowVO balanceFlowVO = new BalanceFlowVO();
        BeanUtils.copyProperties(vCoinsFlow,balanceFlowVO);
        balanceFlowVO.setFlowType(vCoinsFlow.getvCoinsflowType());
        return balanceFlowVO;
    }

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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getFlowType() {
        return flowType;
    }

    public void setFlowType(Long flowType) {
        this.flowType = flowType;
    }

    public String getFlowTypeDesc() {
        return flowTypeDesc;
    }

    public void setFlowTypeDesc(String flowTypeDesc) {
        this.flowTypeDesc = flowTypeDesc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
