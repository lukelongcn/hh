package com.h9.common.db.entity.coupon;

import com.h9.common.base.BaseEntity;
import com.h9.common.db.entity.order.Goods;
import lombok.Data;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:优惠券表</p>
 *
 * @author LiYuan
 * @Date 2018/4/3
 */
@Data
@Entity
@Table(name = "coupon")
public class Coupon extends BaseEntity {

    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "title", nullable = false, columnDefinition = "varchar(256) default '' COMMENT '标题'")
    private String title;

    @Column(name = "coupon_type", nullable = false, columnDefinition = "int  COMMENT '优惠券类型 1 免单劵'")
    private Integer couponType = 1;


    @Column(name = "start_time", columnDefinition = "datetime COMMENT '开始时间'")
    private Date startTime;

    @Column(name = "end_time", columnDefinition = "datetime  COMMENT '结束时间'")
    private Date endTime;

    @Column(name = "value", columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '面值'")
    private BigDecimal value = new BigDecimal(0);

    @Column(name = "use_condition", columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '使用条件'")
    private BigDecimal useCondition = new BigDecimal(0);

    @Column(name = "left_count", columnDefinition = "int default 0 COMMENT '剩余张数'")
    private int leftCount;

    @Column(name = "ask_count", columnDefinition = "int default 0 COMMENT '制券张数'")
    private int askCount;

    @Column(name = "send_flag", columnDefinition = "int comment '1 为没有开始赠送，2 为已经赠送了部份'")
    private int sendFlag;


}
