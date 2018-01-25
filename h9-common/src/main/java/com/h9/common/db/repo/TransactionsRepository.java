package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.Transactions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by itservice on 2018/1/25.
 */
public interface TransactionsRepository extends BaseRepository<Transactions> {

    Page<Transactions> findByUserIdOrTargetUserId(Long id,Long targetUserId, Pageable pageable);
}
