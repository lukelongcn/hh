package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.VB2Money;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * Created by itservice on 2017/12/5.
 */
@Repository
public interface VB2MoneyRepository extends BaseRepository<VB2Money>{

    @Query("select sum(o.vb) from VB2Money o")
    BigDecimal sumVB();

    @Query("select sum(o.money) from VB2Money o")
    BigDecimal sumMoney();

    @Query("select o from VB2Money o where ?1 is null or o.tel = ?1 order by o.id desc ")
    Page<VB2Money> findByTel(String tel, Pageable pageable);
}
