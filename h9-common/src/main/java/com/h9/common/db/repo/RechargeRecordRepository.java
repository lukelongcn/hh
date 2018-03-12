package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.account.RechargeRecord;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by itservice on 2017/12/5.
 */
public interface RechargeRecordRepository extends BaseRepository<RechargeRecord> {

    RechargeRecord findByOrderId(long id);

    @Query("select sum(o.money) from RechargeRecord  o  ")
    BigDecimal findRecharMoneySum();

    @Query("select sum(o.money) from RechargeRecord  o where o.createTime > ?1 and o.createTime < ?2")
    BigDecimal findRecharMoneySumAndDate(Date startTime, Date endTime);
}
