package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.lottery.OrdersLotteryActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by Ln on 2018/3/28.
 */
public interface OrdersLotteryActivityRep extends BaseRepository<OrdersLotteryActivity> {

    @Query("select o from OrdersLotteryActivity o where ?1>= o.startTime and ?1 < o.endTime and o.status = 1")
    List<OrdersLotteryActivity> findByDate(Date date);

    @Query("select o from OrdersLotteryActivity o where o.status =?1")
    Page<OrdersLotteryActivity> findByStatus(int status, Pageable pageable);

    @Query("select o from OrdersLotteryActivity o where  o.status = 1 and o.startLotteryTime > ?1")
    List<OrdersLotteryActivity> findByLotteryDate(Date date);

}
