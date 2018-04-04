package com.h9.common.db.entity.user;

import com.h9.common.base.BaseEntity;
import com.h9.common.db.entity.order.Coupon;
import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:</p>
 *
 * @author LiYuan
 * @Date 2018/4/3
 */
@Data
@Entity
@Table(name = "user_coupon")
public class UserCoupon  extends BaseEntity {

    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "user_id",  columnDefinition = "bigint(20) default 0 COMMENT '用户id'")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "coupon_id",  referencedColumnName = "id", columnDefinition = "bigint(20)  COMMENT '对应优惠券'", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Coupon couponId;

    /**
     * 使用状态 1未使用 2已使用 3已过期
     * @see UserCoupon.statusEnum
     */
    @Column(name = "state", nullable = false, columnDefinition = "int default 1 COMMENT '使用状态 1未使用 2已使用 3已过期'")
    private Integer state = 1;

    public enum statusEnum {

        ENABLE(1, "未使用"),
        BAN(0, "已使用"),
        FINISH(2, "已过期");

        private int code;
        private String desc;

        statusEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public static UserCoupon.statusEnum findByCode(int code) {
            UserCoupon.statusEnum[] values = values();
            for (UserCoupon.statusEnum enumEl : values) {
                if (enumEl.code == code) {
                    return enumEl;
                }
            }
            return null;
        }
    }
}
