package com.h9.common.db.repo;


import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.lottery.Lottery;
import com.h9.common.db.entity.lottery.Reward;
import com.h9.common.modle.dto.LotteryFlowActivityDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
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
            "\tAND a1.lottery_count/a2.lottery_day_count  >= 8 \n" +
            "\tAND a1.user_id = a2.userId")
    List<Object> findBlackUser();

    default Specification<Lottery> buildActivitySpecification(LotteryFlowActivityDTO lotteryFlowActivityDTO){
        return  new Specification<Lottery>() {
            @Override
            public Predicate toPredicate(Root<Lottery> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(!StringUtils.isEmpty(lotteryFlowActivityDTO.getPhone())){
                    predicates.add(cb.equal(root.get("user").get("phone").as(String.class), lotteryFlowActivityDTO.getPhone()));
                }
                if(!StringUtils.isEmpty(lotteryFlowActivityDTO.getCode())){
                    predicates.add(cb.equal(root.get("reward").get("code").as(String.class), lotteryFlowActivityDTO.getCode()));
                }
                if(lotteryFlowActivityDTO.getStatus()!=null&& lotteryFlowActivityDTO.getStatus()!=0){
                    if(lotteryFlowActivityDTO.getStatus()==2){
                        predicates.add(cb.lessThanOrEqualTo(root.get("money").as(BigDecimal.class), BigDecimal.ZERO));
                    }else{
                        predicates.add(cb.greaterThan(root.get("money").as(BigDecimal.class), BigDecimal.ZERO));
                    }
                }
                Predicate[] pre = new Predicate[predicates.size()];
                return query.where(predicates.toArray(pre)).getRestriction();
            }
        };

    }
}