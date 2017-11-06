package com.h9.common.db.repo;


import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.LotteryLog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName: LotteryLogRepository
 * @Description: LotteryLog 的查询
 * @author: shadow.liu
 * @date: 2016年6月27日 下午3:18:36
 */
@Repository
public interface LotteryLogRepository extends BaseRepository<LotteryLog> {

    @Query("select count(distinct ll.code) from LotteryLog  ll  where ll.userId = ?1 and ll.createTime between ?2 and ?3 ")
    BigDecimal getLotteryCount(Long userId,Date startDate,Date endDate);

}
