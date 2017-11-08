package com.h9.admin.model.vo;

import com.h9.common.db.entity.Reward;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @author: George
 * @date: 2017/11/7 20:08
 */
public class RewardVO {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "兑奖码")
    private String code;

    @ApiModelProperty(value = "总金额")
    private BigDecimal money = new BigDecimal(0);

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "发起人userId")
    private Long userId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "参与人数")
    private int partakeCount = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getPartakeCount() {
        return partakeCount;
    }

    public void setPartakeCount(int partakeCount) {
        this.partakeCount = partakeCount;
    }

    public RewardVO() {
    }

    public RewardVO(Reward reward){
        BeanUtils.copyProperties(reward,this);
        this.productName = reward.getProduct().getName();
    }

    public static RewardVO toRewardVO(Reward reward){
        RewardVO rewardVO = new RewardVO();
        BeanUtils.copyProperties(reward,rewardVO);
        rewardVO.productName = reward.getProduct().getName();
        return  rewardVO;
    }
}
