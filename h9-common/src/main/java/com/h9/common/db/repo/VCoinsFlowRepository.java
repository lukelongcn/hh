package com.h9.common.db.repo;


import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.VCoinsFlow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * @ClassName: VCoinsFlowRepository
 * @Description: VCoinsFlow 的查询
 * @author: shadow.liu
 * @date: 2016年6月27日 下午3:18:36
 */
@Repository
public interface VCoinsFlowRepository extends BaseRepository<VCoinsFlow> {

    @Query("select vcf from VCoinsFlow  vcf where vcf.userId = ?1 order by id desc")
    Page<VCoinsFlow> findByBalance(Long userId, Pageable pageRequest);

    @Query("select sum(v.money) from VCoinsFlow v where (v.vCoinsflowType=2 or v.vCoinsflowType=4) ")
    BigDecimal getGrantVCoins();
}
