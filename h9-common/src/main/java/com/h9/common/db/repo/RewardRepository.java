package com.h9.common.db.repo;


import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.Reward;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;

/**
 * @ClassName: RewardRepository
 * @Description: Reward 的查询
 * @author: shadow.liu
 * @date: 2016年6月27日 下午3:18:36
 */
@Repository
public interface RewardRepository extends BaseRepository<Reward> {
    @Query("select r from Reward r where r.code =?1")
    Reward findByCode(String code);

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select r from Reward r where r.code =?1")
    Reward findByCode4Update(String code);




}
