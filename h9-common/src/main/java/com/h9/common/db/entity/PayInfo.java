package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;

import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * PayInfo:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/11
 * Time: 16:32
 */
@Data
@Entity
@Table(name = "pay_info")
public class PayInfo extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-parentSeq", sequenceName = "h9-parent_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-parentSeq")
    private Long id;

    @Column(name = "money",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '支付金额'")
    private BigDecimal money = new BigDecimal(0);
    
    @Column(name = "order_id", columnDefinition = "bigint(20) default null COMMENT ''")
    private Long orderId;

    @Column(name = "order_type",nullable = false,columnDefinition = "int default 0 COMMENT '订单类型'")
    private Integer orderType;

    @Column(name = "pay_order_id", columnDefinition = "bigint(20) default null COMMENT '支付订单号'")
    private Long payOrderId;


    public enum OrderTypeEnum{
        Recharge(0,"充值");

        OrderTypeEnum(int id,String name){
            this.id = id;
            this.name = name;
        }

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }


    }



}
