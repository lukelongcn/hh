package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.Orders;
import com.h9.common.db.entity.User;

import java.util.List;

/**
 * Created by itservice on 2017/11/1.
 */
public interface OrdersReposiroty extends BaseRepository<Orders>{
    List<Orders> findByUser(User user);
}
