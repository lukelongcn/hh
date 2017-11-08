package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.WithdrawalsRequest;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by itservice on 2017/11/7.
 */
public interface WithdrawalsRequestReposiroty extends BaseRepository<WithdrawalsRequest> {


    @Query(nativeQuery = true,value = "select * from withdrawals_request where withdraw_id=?1 order by id desc limit 0,1")
    WithdrawalsRequest findByLastTry(Long withdrawCashId);
}
