package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.Goods;
import com.h9.common.db.entity.GoodsType;
import com.h9.common.modle.DiDiCardInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by itservice on 2017/10/31.
 */
public interface GoodsReposiroty extends BaseRepository<Goods>{

    List<Goods> findByGoodsType(GoodsType goodsType);


//    @Query(value = "select new com.h9.common.modle.DiDiCardInfo(o.realPrice,count(o.id))  from Goods o where o.status =1 and o.goodsType = 2 group by o.realPrice")
//    List<DiDiCardInfo> findRealPriceAndStock();

    @Query(value = "select * from goods  where status = 1 and real_price = ?1 and goods_type_id = 2 limit 0,1",nativeQuery = true)
    Goods findByTop1(BigDecimal realPrice);

    @Query("select o from Goods o  order by o.status asc , o.id desc ")
    Page<Goods> findAllByPage(Pageable page);

}
