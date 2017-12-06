package com.transfer.db.repo;


import com.h9.common.base.PageResult;
import com.transfer.db.BasicRepository;
import com.transfer.db.entity.GoodsInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: GoodsInfoRepository
 * @Description: GoodsInfo 的查询
 * @author: shadow.liu
 * @date: 2016年6月27日 下午3:18:36
 */
@Repository
public interface GoodsInfoRepository extends BasicRepository<GoodsInfo> {
//    @Query("SELECT G FROM GoodsInfo G WHERE G.price<>null")
//    Page<GoodsInfo> findByPrice(Pageable pageable);
//
//    default PageResult<GoodsInfo> findByPrice(int page,int limit){
//        Page<GoodsInfo> byPrice = findByPrice(pageRequest(page, limit));
//        return new PageResult<>(byPrice);
//    }

}
