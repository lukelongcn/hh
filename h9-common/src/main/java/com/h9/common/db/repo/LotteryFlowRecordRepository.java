package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.LotteryFlow;
import com.h9.common.db.entity.LotteryFlowRecord;
import com.h9.common.db.entity.User;
import com.h9.common.modle.dto.LotteryFLowRecordDTO;
import com.h9.common.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: George
 * @date: 2017/11/10 14:53
 */
public interface LotteryFlowRecordRepository  extends BaseRepository<LotteryFlowRecord> {
   LotteryFlowRecord findByLotteryFlow_Id(long lotteryFlowId);

  /* default Specification<LotteryFlowRecord> buildSpecification(LotteryFLowRecordDTO lotteryFLowRecordDTO){
      return  new Specification<LotteryFlowRecord>() {
         @Override
         public Predicate toPredicate(Root<LotteryFlowRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            List<Predicate> predicates = new ArrayList<>();
            if(!StringUtils.isEmpty(lotteryFLowRecordDTO.getPhone())){
               Join<LotteryFlowRecord,LotteryFlow> join = root.join("lotteryFlow",JoinType.INNER);
               predicates.add(cb.equal(join.get("user").get("phone").as(String.class), lotteryFLowRecordDTO.getPhone()));
            }
            if(lotteryFLowRecordDTO.getCode()!=null){
               predicates.add(cb.equal(root.get("reward").get("code").as(String.class), lotteryFlowFinanceDTO.getCode()));
            }
            if(lotteryFlowFinanceDTO.getStartTime()!=null){
               predicates.add(cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class), lotteryFlowFinanceDTO.getStartTime()));
            }
            if(lotteryFlowFinanceDTO.getEndTime()!=null){
               predicates.add(cb.lessThan(root.get("createTime").as(Date.class), DateUtil.addDays(lotteryFlowFinanceDTO.getEndTime(),1)));
            }
            Predicate[] pre = new Predicate[predicates.size()];
            return query.where(predicates.toArray(pre)).getRestriction();
         }
      };

   }*/
}
