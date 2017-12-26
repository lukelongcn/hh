package com.h9.common.db.repo;


import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.Product;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;

/**
 * @ClassName: ProductRepository
 * @Description: Product 的查询
 * @author: shadow.liu
 * @date: 2016年6月27日 下午3:18:36
 */
@Repository
public interface ProductRepository extends BaseRepository<Product> {

    Product findByCode(String code);
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.code = ?1")
    Product findByCode4Update(String code);




}
