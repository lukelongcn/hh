package com.h9.common.db.entity.order;

import com.h9.common.base.BaseEntity;
import com.h9.common.db.entity.lottery.OrdersLotteryActivity;
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

    @Column(name = "coupon_type", nullable = false, columnDefinition = "varchar(256) default '' COMMENT '优惠券类型'")
    private String couponType;

    /**
     * 状态 1 未生效 0生效中 2已失效
     * @see Coupon.statusEnum
     */
    @Column(name = "status",  columnDefinition = "int  COMMENT '状态 1 未生效 0生效中 2已失效'")
    private int status;

    @Column(name = "start_time",  columnDefinition = "datetime COMMENT '开始时间'")
    private Date startTime;

    @Column(name = "end_time",  columnDefinition = "datetime  COMMENT '结束时间'")
    private Date endTime;

    @Column(name = "value", columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '面值'")
    private BigDecimal value = new BigDecimal(0);

    @Column(name = "use_condition", columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '使用条件'")
    private BigDecimal useCondition = new BigDecimal(0);

    @Column(name = "left_count", columnDefinition = "int default 0 COMMENT '剩余张数'")
    private int leftCount;

    @Column(name = "ask_count", columnDefinition = "int default 0 COMMENT '制券张数'")
    private int askCount;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "goods_id",referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT '商品id'",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Goods goodsId;



    public enum statusEnum{

        ENABLE(1,"未生效"),
        BAN(0,"生效中"),
        FINISH(2,"已失效");

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

        public static Coupon.statusEnum findByCode(int code){
            Coupon.statusEnum[] values = values();
            for(Coupon.statusEnum enumEl : values){
                if(enumEl.code == code){
                    return enumEl;
                }
            }
            return null;
        }
    }
}
