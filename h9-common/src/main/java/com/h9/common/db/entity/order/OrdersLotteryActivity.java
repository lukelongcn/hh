package com.h9.common.db.entity.order;


import com.alibaba.fastjson.JSONObject;
import com.h9.common.base.BaseEntity;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * 状态 1 启用 0禁用
     */
    @Column(name = "status", nullable = false, columnDefinition = "int  COMMENT '状态 1 启用 0禁用'")
    private int status;

    @Column(name = "winner_user", columnDefinition = "varchar(200)  COMMENT '中奖名单 json 对象 {id:money} 表示'")
    private String winnerUser;

    @Column(name = "start_lottery_time", nullable = false, columnDefinition = "datetime COMMENT '开始抽奖时间'")
    private Date startLotteryTime;

    public Map<Long,BigDecimal> getWinnerUser() {
        if (StringUtils.isNotBlank(winnerUser)) {
            Map<Long,BigDecimal> map = JSONObject.parseObject(winnerUser, Map.class);
            return map;
        }
        return null;
    }

    public void setWinnerUser(Map<Long,BigDecimal> winnerUserMap) {
        if (winnerUserMap == null) {
            return;
        }
        String json = JSONObject.toJSONString(winnerUserMap);
        this.winnerUser = json;

    }
}
