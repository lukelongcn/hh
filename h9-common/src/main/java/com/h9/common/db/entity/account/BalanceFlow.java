package com.h9.common.db.entity.account;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import java.math.BigDecimal;
import java.security.Signature;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:余额流水
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/27
 * Time: 17:30
 */

@Entity
@Table(name = "balance_flow")
public class BalanceFlow extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "balance",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '变更后的余额'")
    private BigDecimal balance = new BigDecimal(0);

    @Column(name = "money",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '变更金额'")
    private BigDecimal money = new BigDecimal(0);


    /**
     * description: {"11":"vb积分兑换酒元","1":"提现","12":"酒元余额兑换","2":"银联退回","3":"小品会","5":"滴滴兑换","6":"充话费","8":"大转盘","9":"抢红包（临时账号）","10":"抢红包"}
     * @see FlowType
     */
    @Column(name = "flow_type",columnDefinition = "bigint(20) default null COMMENT '流水类型'")
    private Long flowType;

    public static class FlowType{
        public static final long WITHDRAW = 1;
        public static final long REFUND = 2;
        public static final long XIAPPINHUI = 3;
        public static final long DIDI_CONVERT = 5;
        public static final long MOBILE_RECHAGE = 6;
        public static final long DA_ZHUAN_PAN = 8;
        public static final long ACCOUNT_TRANSFER = 9;
        /**
         * description: 抢红包
         */
        public static final long LOTTERY = 10;
    }

    @Column(name = "remarks", columnDefinition = "varchar(64) default '' COMMENT '资金变动备注'")
    private String remarks;

    @JoinColumn(name = "user_id",columnDefinition = "bigint(20) default 0 COMMENT '资金变更用户'")
    private Long userId;

    @Column(name = "order_id", columnDefinition = "bigint(20) default null COMMENT '订单id'")
    private Long orderId;


    @Column(name = "order_no", columnDefinition = "varchar(32) default '' COMMENT '订单号'")
    private String orderNo;

    @Column(name ="withdrawals_id",columnDefinition = "bigint(20) default null COMMENT '提现id' ")
    private Long withdrawals_id;

    public Long getWithdrawals_id() {
        return withdrawals_id;
    }

    public void setWithdrawals_id(Long withdrawals_id) {
        this.withdrawals_id = withdrawals_id;
    }

    public Long getFlowType() {
        return flowType;
    }

    public void setFlowType(Long flowType) {
        this.flowType = flowType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public  enum BalanceFlowTypeEnum {
        WITHDRAW(1,"提现"),
        RETURN(2,"银联退回"),
        XIAOPINHUI(3,"小品会"),
        RECHARGE_PHONE_FARE(6,"充话费"),
        DIDI_EXCHANGE(5,"滴滴兑换"),
        ROUNDABOUT(8,"大转盘"),
        TEMP_RED_BAG(9,"抢红包(历临时账户)"),
        RED_BAG(10,"抢红包"),
        VB_TO_MONEY(11,"vb兑换酒元"),
        REFUND(13,"退款"),
        SIGN(14,"签到");

        BalanceFlowTypeEnum(long id,String name){
            this.id = id;
            this.name = name;
        }

        private long id;
        private String name;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
