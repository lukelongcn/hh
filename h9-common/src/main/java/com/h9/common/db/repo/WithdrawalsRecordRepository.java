package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.WithdrawalsRecord;
import com.h9.common.modle.vo.WithdrawRecordVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * Created by itservice on 2017/11/5.
 */
public interface WithdrawalsRecordRepository extends BaseRepository<WithdrawalsRecord> {


    List<WithdrawalsRecord> findByStatusIn(Collection<Integer> statusList);

    List<WithdrawalsRecord> findByStatus(Integer status);

    @Query("select sum(w.money) from WithdrawalsRecord w where w.status=?1")
    BigDecimal getWithdrawalsCount(int status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select o from WithdrawalsRecord o where o.id = ?1")
    WithdrawalsRecord findByLockId(long id);

    @Query("select new com.h9.common.modle.vo.WithdrawRecordVO(w) from WithdrawalsRecord  w,User u where w.userId=u.id and u.id=?1 order by w.id")
    Page<WithdrawRecordVO> findByUserId(long userId, Pageable pageable);


}
