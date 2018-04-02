package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.lottery.OrdersLotteryActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.Date;
import java.util.List;

/**
 * Created by Ln on 2018/3/28.
 */
public interface OrdersLotteryActivityRep extends BaseRepository<OrdersLotteryActivity> {

    @Query("select o from OrdersLotteryActivity o where ?1>= o.startTime and ?1 < o.endTime and o.status = 1 and o.id <> ?2")
    List<OrdersLotteryActivity> findByDateId(Date date, Long id);

    @Query("select o from OrdersLotteryActivity o where o.status =?1")
    Page<OrdersLotteryActivity> findByStatus(int status, Pageable pageable);

    @Query("select o from OrdersLotteryActivity o where  o.status = 1 and o.startLotteryTime < ?1 " +
            "and o.startLotteryTime >?2")
    List<OrdersLotteryActivity> findByLotteryDate(Date date, Date now);

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "false")})
    @Query("select o from OrdersLotteryActivity o where id = ?1")
    OrdersLotteryActivity findByIdFromDB(Long id);

}
