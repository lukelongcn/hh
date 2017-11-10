package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.LotteryFlow;
import com.h9.common.db.entity.LotteryFlowRecord;
import com.h9.common.db.entity.User;
import com.h9.common.modle.dto.LotteryFlowActivityDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: George
 * @date: 2017/11/10 14:53
 */
public interface LotteryFlowRecordRepository  extends BaseRepository<LotteryFlowRecord> {
    default Specification<LotteryFlowRecord> buildActivitySpecification(LotteryFlowActivityDTO lotteryFlowActivityDTO){
        return  new Specification<LotteryFlowRecord>() {
            @Override
            public Predicate toPredicate(Root<LotteryFlowRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(!StringUtils.isEmpty(lotteryFlowActivityDTO.getPhone())){
                    Join<LotteryFlow,User> join = root.join("user", JoinType.INNER);
                    predicates.add(cb.equal(join.get("phone").as(String.class), lotteryFlowActivityDTO.getPhone()));
                }
                if(lotteryFlowActivityDTO.getCode()!=null){
                    predicates.add(cb.equal(root.get("reward").get("code").as(String.class), lotteryFlowActivityDTO.getCode()));
                }
                if(lotteryFlowActivityDTO.getStatus()!=null&& lotteryFlowActivityDTO.getStatus()!=0){
                    if(lotteryFlowActivityDTO.getStatus()==1){
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
