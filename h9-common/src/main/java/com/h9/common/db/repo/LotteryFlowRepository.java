package com.h9.common.db.repo;


import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.Lottery;
import com.h9.common.db.entity.LotteryFlow;
import com.h9.common.db.entity.Reward;
import com.h9.common.modle.dto.LotteryFlowDTO;
import com.h9.common.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: LotteryFlowRepository
 * @Description: LotteryFlow 的查询
 * @author: shadow.liu
 * @date: 2016年6月27日 下午3:18:36
 */
@Repository
public interface LotteryFlowRepository extends BaseRepository<LotteryFlow> {

    @Query("select l from LotteryFlow l where l.reward = ?1 order by l.createTime desc ")
    List<LotteryFlow> findByReward(Reward reward);


    @Query("select l from LotteryFlow l where l.reward = ?1 and l.userId = ?2 order by l.createTime desc ")
    LotteryFlow findByReward(Reward reward,Long userId);


    @Query("select l from LotteryFlow l where l.userId = ?2 order by l.createTime desc ")
    List<LotteryFlow> findByReward(Long userId);

   /* default Specification<LotteryFlow> buildSpecification(LotteryFlowDTO lotteryFlowDTO){
        return  new Specification<LotteryFlow>() {
            @Override
            public Predicate toPredicate(Root<LotteryFlow> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(!StringUtils.isEmpty(rewardQueryDTO.getCode())){
                    predicates.add(cb.like(root.get("code").as(String.class),"%"+rewardQueryDTO.getCode()+"%"));
                }
                if(rewardQueryDTO.getStartTime()!=null){
                    predicates.add(cb.greaterThanOrEqualTo(root.get("startTime").as(Date.class),rewardQueryDTO.getStartTime()));
                }
                if(rewardQueryDTO.getEndTime()!=null){
                    predicates.add(cb.lessThan(root.get("endTime").as(Date.class), DateUtil.addDays(rewardQueryDTO.getEndTime(),1)));
                }
                Predicate[] pre = new Predicate[predicates.size()];
                return query.where(predicates.toArray(pre)).getRestriction();
            }
        }

    }*/

}
