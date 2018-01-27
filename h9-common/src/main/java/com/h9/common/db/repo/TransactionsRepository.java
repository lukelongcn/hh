package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.Transactions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by itservice on 2018/1/25.
 */
public interface TransactionsRepository extends BaseRepository<Transactions> {

    Page<Transactions> findByUserIdOrTargetUserId(Long id,Long targetUserId, Pageable pageable);

    @Query(value = "select o from Transactions  o where o.userId = ?2 and o.balanceFlowType = ?1")
    Page<Transactions> findByBalanceFlowType(Long type,Long userId,Pageable pageable);

    Transactions findByTempId(String tempId);
}
