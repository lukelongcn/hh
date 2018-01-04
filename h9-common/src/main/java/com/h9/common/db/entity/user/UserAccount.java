package com.h9.common.db.entity.user;

import com.h9.common.base.BaseEntity;
import com.h9.common.utils.MoneyUtils;

import javax.persistence.*;

import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/28
 * Time: 10:05
 */

@Entity
@Table(name = "user_account")
public class UserAccount extends BaseEntity {


    @Id
//    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
//    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    @Column(name = "user_id", columnDefinition = "bigint(20)  COMMENT ''")
    private Long userId;

    @Column(name = "balance",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '用户余额'")
    private BigDecimal balance = new BigDecimal(0);

    @Column(name = "v_coins",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT 'v币个数'")
    private BigDecimal vCoins = new BigDecimal(0);

    @Column(name = "cash_back_count",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '签到累计奖励金额'")
    private BigDecimal cashBackCount = new BigDecimal(0);

    public BigDecimal getCashBackCount() {
        return cashBackCount;
    }

    public void setCashBackCount(BigDecimal cashBackCount) {
        this.cashBackCount = cashBackCount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getvCoins() {
        return new BigDecimal(MoneyUtils.formatMoney(vCoins, "0"));
    }

    public void setvCoins(BigDecimal vCoins) {
        this.vCoins = vCoins;
    }
}
