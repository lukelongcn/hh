package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.base.PageResult;
import com.h9.common.db.entity.lottery.OrdersLotteryActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:</p>
 *
 * @author LiYuan
 * @Date 2018/3/28
 */
public interface OrdersLotteryActivityRepository  extends BaseRepository<OrdersLotteryActivity> {
    @Query("select r from OrdersLotteryActivity r where r.status = 1 order by r.endTime DESC ")
    Page<OrdersLotteryActivity> findAllDetail(Pageable pageRequest);
    default PageResult<OrdersLotteryActivity> findAllDetail(Integer page, Integer limit){
        Page<OrdersLotteryActivity> List =  findAllDetail(pageRequest(page,limit));
        return new PageResult(List);
    }

    @Query("select o from OrdersLotteryActivity o where o.winnerUserId = ?1 and o.status <> 0 order by o.endTime ")
    Page<OrdersLotteryActivity> findByUserId(long userId, Pageable pageable);
    default PageResult<OrdersLotteryActivity> findByUserId(long userId, Integer page, Integer limit){
        Page<OrdersLotteryActivity> list = findByUserId(userId, pageRequest(page,limit));
        return new PageResult<>(list);
    }
}
