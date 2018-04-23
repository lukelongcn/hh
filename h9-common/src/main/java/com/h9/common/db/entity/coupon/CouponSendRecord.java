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
@Table(name = "coupon_sr_record")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponSendRecord extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "id",columnDefinition = "bigint comment 'userId'",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "receive_user_id",referencedColumnName = "id",columnDefinition = "bigint comment 'userId'",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
//    private User receiveUser;

    @JoinColumn(name = "user_coupon_id")
    private String userCoupon;

//    @Column(name = "receive_date",columnDefinition = "datetime comment '领取时间'")
//    private Date receiveDate;

    @Column(name = "uuid",columnDefinition = "varchar(200) comment 'uuid 赠送时生成的UUID'")
    private String uuid;

    /**
     * 1 赠送
     * 2 领取
     */
    @Column(name = "opt_type")
    private Integer optType;
}
