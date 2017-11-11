package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.LotteryFlowRecord;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

/**
 * @author: George
 * @date: 2017/11/10 14:53
 */
@Repository
public interface LotteryFlowRecordRepository  extends BaseRepository<LotteryFlowRecord> {
   LotteryFlowRecord findByLotteryFlow_Id(long lotteryFlowId);

   @Query("select o from LotteryFlowRecord o where o.id=?1")
   @Lock(LockModeType.PESSIMISTIC_WRITE)
   LotteryFlowRecord findByLockId(long id);

}
