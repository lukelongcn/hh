package com.h9.common.db.entity.lottery;

import com.h9.common.base.BaseEntity;
import com.h9.common.base.BaseRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 大富贵操作 中奖人 的操作记录表
 */
@Entity
@Table(name = "winner_op_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WinnerOptRecord extends BaseEntity{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "orders_lottery_activity_id")
    private Long OrdersLotteryActivityId;

    @Column(name = "winner_user_id")
    private Long winnerUserId;

    @Column(name = "opt_user_id")
    private Long optUserId;

}
