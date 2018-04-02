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
    @Query("select r from OrdersLotteryActivity r where r.status = 1 order by r.endTime DESC ")
    Page<OrdersLotteryActivity> findAllDetail(Pageable pageRequest);
    default PageResult<OrdersLotteryActivity> findAllDetail(Integer page, Integer limit){
        Page<OrdersLotteryActivity> List =  findAllDetail(pageRequest(page,limit));
        return new PageResult(List);
    }


    @Query("select o from OrdersLotteryActivity o where o.id = ?1 and o.status <> 0")
    OrdersLotteryActivity findOneById(Long id);

    @Query("SELECT r From OrdersLotteryActivity r where r.startTime < ?1 and r.endTime>?1 and r.status = 1 ORDER BY r.id desc ")
    List<OrdersLotteryActivity> findAllTime(Date createTime);
}
