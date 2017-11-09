package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.WithdrawalsRecord;
import org.springframework.data.jpa.repository.Query;

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

}
