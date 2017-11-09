package com.h9.common.db.repo;


import com.h9.common.base.BaseRepository;
import com.h9.common.base.PageResult;
import com.h9.common.db.entity.LotteryFlow;
import com.h9.common.db.entity.Reward;
import com.h9.common.db.entity.User;
import com.h9.common.modle.dto.LotteryFlowDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
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


    @Query("select l from LotteryFlow l where l.reward = ?1 and l.user.id = ?2 order by l.createTime desc ")
    LotteryFlow findByReward(Reward reward,Long userId);


    LotteryFlow findTop1ByRewardOrderByMoneyDesc(Reward reward);


    @Query("select l from LotteryFlow l where l.user.id = ?1 order by l.createTime desc ")
    Page<LotteryFlow> findByReward(Long userId, Pageable pageable);

    default Specification<LotteryFlow> buildSpecification(LotteryFlowDTO lotteryFlowDTO){
        return  new Specification<LotteryFlow>() {
            @Override
            public Predicate toPredicate(Root<LotteryFlow> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(!StringUtils.isEmpty(lotteryFlowDTO.getPhone())){
                   Join<LotteryFlow,User> join = root.join("user", JoinType.INNER);
                   predicates.add(cb.equal(join.get("phone").as(String.class),lotteryFlowDTO.getPhone()));
                }
                if(lotteryFlowDTO.getCode()!=null){
                    predicates.add(cb.equal(root.get("reward").get("code").as(String.class),lotteryFlowDTO.getCode()));
                }
                if(lotteryFlowDTO.getStatus()!=null&&lotteryFlowDTO.getStatus()!=0){
                    if(lotteryFlowDTO.getStatus()==1){
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

   default PageResult<LotteryFlow> findByReward(Long userId, int page , int limit){
       PageRequest pageRequest = pageRequest(page, limit);
       Page<LotteryFlow> lotteryFlows = findByReward(userId, pageRequest);
       return new PageResult(lotteryFlows);
   }

   @Query("select sum(l.money) from LotteryFlow l")
   BigDecimal getLotteryCount();
}
