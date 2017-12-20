package com.h9.common.db.repo;


import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.BalanceFlow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: BalanceFlowRepository
 * @Description: BalanceFlow 的查询
 * @author: shadow.liu
 * @date: 2016年6月27日 下午3:18:36
 */
@Repository
public interface BalanceFlowRepository extends BaseRepository<BalanceFlow> {

    @Query("select bf from BalanceFlow  bf where bf.userId = ?1 order by bf.createTime desc")
    Page<BalanceFlow> findByBalance(Long userId, Pageable pageRequest);


}
