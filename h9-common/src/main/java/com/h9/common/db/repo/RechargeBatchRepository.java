package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.RechargeBatch;

import java.util.List;

/**
 * Created by itservice on 2018/1/11.
 */
public interface RechargeBatchRepository extends BaseRepository<RechargeBatch>{

    List<RechargeBatch> findByBatchNo(String batchNo);
}
