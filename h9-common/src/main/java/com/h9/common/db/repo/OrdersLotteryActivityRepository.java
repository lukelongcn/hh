package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.base.PageResult;
import com.h9.common.db.entity.order.OrdersLotteryActivity;
import org.springframework.data.domain.Page;
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
    @Query("select r from OrdersLotteryActivity r where status = 1 order by end_time DESC ")
    Page<OrdersLotteryActivity> findAllDetail(Pageable pageRequest);
    default PageResult<OrdersLotteryActivity> findAllDetail(Integer page, Integer limit){
        Page<OrdersLotteryActivity> List =  findAllDetail(pageRequest(page,limit));
        return new PageResult(List);
    }
}
