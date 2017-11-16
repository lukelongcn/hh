package com.h9.common.db.repo;


import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.ProductLog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName: ProductLogRepository
 * @Description: ProductLog 的查询
 * @author: shadow.liu
 * @date: 2016年6月27日 下午3:18:36
 */
@Repository
public interface ProductLogRepository extends BaseRepository<ProductLog> {

    @Query("select count(pl.code) from  ProductLog pl where pl.userId = ?1 and pl.createTime>?2  and pl.productId is null" )
    long findByUserId(Long userId, Date startDate);

    @Query("select count(pl.code) from  ProductLog pl where pl.userId = ?1  and pl.code = ?2" )
    long findByUserId(Long userId, String code );

}

