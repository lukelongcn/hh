package com.h9.common.db.repo;


import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.Lottery;
import com.h9.common.db.entity.Reward;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: LotteryRepository
 * @Description: Lottery 的查询
 * @author: shadow.liu
 * @date: 2016年6月27日 下午3:18:36
 */
@Repository
public interface LotteryRepository extends BaseRepository<Lottery> {
    @Query("select l from Lottery l where l.user.id=?1 and l.reward.id=?2")
    Lottery findByUserIdAndReward(Long userId, Long id);

    @Query("select l from Lottery l where l.reward = ?1 order by l.createTime asc ")
    List<Lottery> findByReward(Reward reward);

    @Query("select count(l) from Lottery l where l.reward = ?1")
    BigDecimal findByRewardCount(Reward reward);


    @Query("select l.createTime from Lottery as l where l.reward = ?1 order by l.createTime desc")
    List<Date> findByRewardLastTime(Reward reward, Pageable pageable);


    default Date findByRewardLastTime(Reward reward) {
        return findByRewardLastTime(reward, pageRequest(1, 1)).get(0);
    }

    @Query("select count(l.id) from Lottery as l where l.reward.id = ?1 ")
    int findCountByReward(long rewardId);


    @Query(nativeQuery = true, value = "SELECT \n" +
            "\ta3.user_id\n" +
            "FROM \n" +
            "\t(\n" +
            "\t\tSELECT \n" +
            "\t\t\tlottery.user_id, \n" +
            "\t\t\tlottery.create_time, \n" +
            "\t\t\tcount(*) lottery_count, \n" +
            "\t\t\tsum(lottery.money) lottery_sum_money \n" +
            "\t\tFROM \n" +
            "\t\t\tlottery \n" +
            "\t\tWHERE \n" +
            "\t\t\t(\n" +
            "\t\t\t\tDATE_FORMAT(lottery.create_time, '%H:%m:%s') >= '00:00:00' \n" +
            "\t\t\t\tAND DATE_FORMAT(lottery.create_time, '%H:%m:%s') <= '10:50:00'\n" +
            "\t\t\t) \n" +
            "\t\t\tOR (\n" +
            "\t\t\t\tDATE_FORMAT(lottery.create_time, '%H:%m:%s') > '14:30:00' \n" +
            "\t\t\t\tAND DATE_FORMAT(lottery.create_time, '%H:%m:%s') < '16:50:00'\n" +
            "\t\t\t) \n" +
            "\t\tGROUP BY \n" +
            "\t\t\tlottery.user_id\n" +
            "\t) a3, \n" +
            "\t(\n" +
            "\t\tSELECT \n" +
            "\t\t\tlottery.user_id, \n" +
            "\t\t\tcount(*) lottery_sum \n" +
            "\t\tFROM \n" +
            "\t\t\tlottery \n" +
            "\t\tGROUP BY \n" +
            "\t\t\tlottery.user_id\n" +
            "\t) a4 \n" +
            "WHERE \n" +
            "\ta3.user_id = a4.user_id \n" +
            "\tAND a3.lottery_count / a4.lottery_sum > 0.33 \n" +
            "union \n" +
            "SELECT \n" +
            "\ta1.user_id\n" +
            "FROM \n" +
            "\t(\n" +
            "\t\tSELECT \n" +
            "\t\t\tuser.id user_id, \n" +
            "\t\t\tcount(*) lottery_count, \n" +
            "\t\t\tsum(lottery.money) lottery_money, \n" +
            "\t\t\tlottery.create_time \n" +
            "\t\tFROM \n" +
            "\t\t\tuser, \n" +
            "\t\t\tlottery \n" +
            "\t\tWHERE \n" +
            "\t\t\tlottery.user_id = user.id \n" +
            "\t\tGROUP BY \n" +
            "\t\t\tuser.id\n" +
            "\t) a1, \n" +
            "\t(\n" +
            "\t\tSELECT \n" +
            "\t\t\tid, \n" +
            "\t\t\tlottery.user_id userId, \n" +
            "\t\t\tcount(*) lottery_day_count \n" +
            "\t\tFROM \n" +
            "\t\t\tlottery \n" +
            "\t\tGROUP BY \n" +
            "\t\t\tDATE_FORMAT(lottery.create_time, '%Y-%m-%d')\n" +
            "\t) a2 \n" +
            "WHERE \n" +
            "\ta1.lottery_money > 260 \n" +
            "\tAND a2.lottery_day_count >= 5 \n" +
            "\tAND a1.lottery_count >= 8 \n" +
            "\tAND a1.user_id = a2.userId")
    List<Object> findBlackUser();

}