package com.h9.common.db.repo;


import com.h9.common.base.BaseRepository;
import com.h9.common.base.PageResult;
import com.h9.common.db.entity.LotteryFlow;
import com.h9.common.db.entity.Reward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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


    @Query("select l from LotteryFlow l where l.user.id = ?1 order by l.createTime desc ")
    Page<LotteryFlow> findByReward(Long userId, Pageable pageable);

   default PageResult<LotteryFlow> findByReward(Long userId, int page , int limit){
       PageRequest pageRequest = pageRequest(page, limit);
       Page<LotteryFlow> lotteryFlows = findByReward(userId, pageRequest);
       return new PageResult(lotteryFlows);
   }

}
