package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.WithdrawalsRecord;

import java.util.Collection;
import java.util.List;

/**
 * Created by itservice on 2017/11/5.
 */
public interface WithdrawalsRecordReposiroty extends BaseRepository<WithdrawalsRecord> {


    List<WithdrawalsRecord> findByStatusIn(Collection<Integer> statusList);

    List<WithdrawalsRecord> findByStatus(Integer status);


}
