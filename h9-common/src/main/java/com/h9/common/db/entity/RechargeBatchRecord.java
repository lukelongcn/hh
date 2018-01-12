package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by itservice on 2018/1/11.
 */
@Table(name = "recharge_batch_record")
@Entity
@Data
@Accessors(chain = true)
public class RechargeBatchRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "batch_no")
    private String batchNo;

    @Column(name = "recharge_batch_id")
    private Long rechargeBatchId;

    @Column(name = "phone")
    private String phone;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "money")
    private BigDecimal money;

    /**
     * description: 1 为充值 0为未充值 3 为 查无此人
     */
    @Column(name = "status")
    private Integer status = 0;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "opt_user")
    private Long optUserId;


    public enum RechargeStatusEnum {

        RECHARGE(1, "已充值"),
        NOT_RECHARGE(2, "未充值"),
        NOT_USER(3,"查无此人");

        private int code;
        private String desc;

        RechargeStatusEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }


        public String getDesc() {
            return desc;
        }

        public static RechargeStatusEnum findByCode(int code) {
            RechargeStatusEnum[] values = values();
            for (RechargeStatusEnum smsTypeEnum : values) {
                if (code == smsTypeEnum.getCode()) {
                    return smsTypeEnum;
                }
            }
            return null;
        }
    }

}
