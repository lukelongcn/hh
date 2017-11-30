package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.base.PageResult;
import com.h9.common.db.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by itservice on 2017/11/1.
 */
public interface OrdersRepository extends BaseRepository<Orders> {
    @Query("SELECT o from Orders o where o.user.id=?1 order by o.id desc")
    Page<Orders> findByUser(Long userId, Pageable pageable);


    @Query("SELECT o from Orders o where o.user.id=?1 and o.orderType = ?2 order by o.id desc")
    Page<Orders> findDiDiCardByUser(Long userId,String orderType, Pageable pageable);

    default PageResult<Orders> findByUser(Long userId, int page, int limit){
        Page<Orders> byUser = findByUser(userId, pageRequest(page, limit));
        return new PageResult(byUser);
    }

    @Async
    @Query("select o from Orders o")
    Future<List<Orders>> findAllAsy();
}
