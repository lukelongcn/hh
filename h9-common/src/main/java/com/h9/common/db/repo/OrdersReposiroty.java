package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.Orders;
import com.h9.common.db.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by itservice on 2017/11/1.
 */
public interface OrdersReposiroty extends JpaRepository<Orders,Long> {
    @Query("SELECT o from Orders o where o.user.id=?1 order by o.id desc")
    Page<Orders> findByUser(Long userId, Pageable pageable);
}
