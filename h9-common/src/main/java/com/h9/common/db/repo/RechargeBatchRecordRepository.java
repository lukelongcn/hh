package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.RechargeBatchRecord;

import java.util.List;

/**
 * Created by itservice on 2018/1/11.
 */
public interface RechargeBatchRecordRepository extends BaseRepository<RechargeBatchRecord>{

    List<RechargeBatchRecord> findByRechargeBatchId(Long batchId);

}
