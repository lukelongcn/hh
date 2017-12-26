package com.h9.common.db.repo;


import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.Lottery;
import com.h9.common.db.entity.LotteryFlow;
import com.h9.common.db.entity.Reward;
import com.h9.common.db.entity.User;
import com.h9.common.modle.dto.LotteryFlowActivityDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
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


    @Query(nativeQuery = true, value = "select a1.* from (select user.id user_id,count(*) lottery_count ,sum(lottery.money) lottery_money  from user,lottery where lottery.user_id  = user.id  group by user.id ) a1 where a1.lottery_money > 260;")
    List<?> findBlackUser();

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