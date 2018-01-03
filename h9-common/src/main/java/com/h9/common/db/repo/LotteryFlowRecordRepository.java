package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.lottery.LotteryFlowRecord;
import com.h9.common.modle.vo.admin.finance.LotteryFlowRecordVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Date;

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

   @Query("select new com.h9.common.modle.vo.admin.finance.LotteryFlowRecordVO(o,ua.balance) from LotteryFlowRecord o, UserAccount ua where o.outerId = ua.userId" +
           " and (?1 is null or o.phone = ?1)" +
           " and (?2 is null or o.code = ?2)" +
           " and (?3 is null or o.createTime >= ?3)" +
           " and (?4 is null or o.createTime < ?4)" +
           " and (?5 = 0 or o.status = ?5)" +
           " order by o.id desc")
   Page<LotteryFlowRecordVO> findByCondition(String phone, String code, Date startTime, Date endTime, Integer status, Pageable pageable);


}
