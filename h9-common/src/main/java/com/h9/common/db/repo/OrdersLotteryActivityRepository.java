package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.base.PageResult;
import com.h9.common.db.entity.lottery.OrdersLotteryActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:</p>
 *
 * @author LiYuan
 * @Date 2018/3/28
 */
public interface OrdersLotteryActivityRepository  extends BaseRepository<OrdersLotteryActivity> {
    @Query("select r from OrdersLotteryActivity r where r.status = 1 and r.winnerUserId is not null  order by r.startLotteryTime DESC ")
    Page<OrdersLotteryActivity> findAllDetail(Pageable pageRequest);
    default PageResult<OrdersLotteryActivity> findAllDetail(Integer page, Integer limit){
        Page<OrdersLotteryActivity> List =  findAllDetail(pageRequest(page,limit));
        return new PageResult(List);
    }


    @Query("select o from OrdersLotteryActivity o where o.id = ?1 and o.status <> 0 and o.startLotteryTime < ?2")
    OrdersLotteryActivity findOneById(Long id, Date date);

    @Query(value = "SELECT * From orders_lottery  where start_time < ?1 and end_time > ?1 and status = 1 ORDER BY start_time desc limit 1",nativeQuery = true)
    OrdersLotteryActivity findAllTime(Date createTime);
}
