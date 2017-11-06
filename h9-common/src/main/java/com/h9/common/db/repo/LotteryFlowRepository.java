package com.h9.common.db.repo;


import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.Lottery;
import com.h9.common.db.entity.LotteryFlow;
import com.h9.common.db.entity.Reward;
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

    @Query("select l.createTime from LotteryFlow l where l.reward = ?1 order by l.createTime desc ")
    List<LotteryFlow> findByReward(Reward reward);

}
