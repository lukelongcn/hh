package com.h9.common.db.entity.coupon;

import com.h9.common.base.BaseEntity;
import com.h9.common.db.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Ln on 2018/4/18.
 */
@Table(name = "coupon_send_record")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponSendRecord extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_user_id",referencedColumnName = "id",columnDefinition = "bigint comment 'userId'",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User sendUser;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receive_user_id",referencedColumnName = "id",columnDefinition = "bigint comment 'userId'",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User receiveUser;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_coupon_id",referencedColumnName = "id",columnDefinition = "bigint comment 'user_coupon_id'",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserCoupon userCoupon;

    @Column(name = "status",columnDefinition = "int comment '状态, 1 领取成功，0 未领取'")
    private Integer status;

    @Column(name = "receive_date",columnDefinition = "datetime comment '领取时间'")
    private Date receiveDate;
}
