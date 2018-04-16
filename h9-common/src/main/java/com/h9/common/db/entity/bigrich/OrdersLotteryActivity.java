package com.h9.common.db.entity.bigrich;


import com.h9.common.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 订单抽奖表，用于大富贵活动
 */
@Table(name = "orders_lottery")
@Entity
@Data
public class OrdersLotteryActivity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "activity_number", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '期数'")
    private String number;

    @Column(name = "join_count", columnDefinition = "int default 0 COMMENT '参与人数'")
    private int joinCount;

    @Column(name = "start_time", nullable = false, columnDefinition = "datetime COMMENT '开始时间'")
    private Date startTime;

    @Column(name = "end_time", nullable = false, columnDefinition = "datetime  COMMENT '结束时间'")
    private Date endTime;

    /**
     * 状态 1 启用 0禁用 2结束
     *
     * @see statusEnum
     */
    @Column(name = "status", nullable = false, columnDefinition = "int  COMMENT '状态 1 启用 0禁用 2结束'")
    private int status;

//    @Column(name = "winner_user", columnDefinition = "varchar(200)  COMMENT '中奖名单 json 对象 {id:money} 表示'")
//    private String winnerUser;

    @Column(name = "start_lottery_time", nullable = false, columnDefinition = "datetime COMMENT '开始抽奖时间'")
    private Date startLotteryTime;

    @Column(name = "money", columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '金额'")
    private BigDecimal money = new BigDecimal(0);

    @Column(name = "winner_user_id", columnDefinition = "bigint COMMENT '中奖人Id'")
    private Long winnerUserId;

    @Column(name = "del_flag", columnDefinition = "int COMMENT '删除标记 1 删除 0 未删除'")
    private Integer delFlag=0;

    public enum statusEnum {

        ENABLE(1, "启用"),
        BAN(0, "禁用"),
        FINISH(2, "已开奖");

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

        public static statusEnum findByCode(int code) {
            statusEnum[] values = values();
            for (statusEnum enumEl : values) {
                if (enumEl.code == code) {

                    return enumEl;
                }
            }
            return null;
        }
    }

}
