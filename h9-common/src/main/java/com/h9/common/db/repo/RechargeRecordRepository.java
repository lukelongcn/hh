package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.RechargeRecord;

/**
 * Created by itservice on 2017/12/5.
 */
public interface RechargeRecordRepository extends BaseRepository<RechargeRecord>{

    RechargeRecord findByOrderId(long id);
}
