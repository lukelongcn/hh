package com.h9.common.db.repo;


import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.Lottery;
import com.h9.common.db.entity.Reward;
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

    Lottery findByUserIdAndReward(Long userId, Reward reward);

    @Query("select l from Lottery l where l.reward = ?1 order by l.createTime desc ")
    List<Lottery> findByReward(Reward reward);

    @Query("select count(l) from Lottery l where l.reward = ?1")
    BigDecimal findByRewardCount(Reward reward);


}
