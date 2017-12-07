package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.Goods;
import com.h9.common.db.entity.GoodsType;
import com.h9.common.modle.DiDiCardInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by itservice on 2017/10/31.
 */
public interface GoodsReposiroty extends BaseRepository<Goods>{

    @Query(value = "select o from Goods o where o.goodsType = ?1 and o.status = 1")
    List<Goods> findByGoodsType(GoodsType goodsType);

    @Query(value = "select o from Goods o where o.goodsType = ?1 and o.status = 1")
    Page<Goods> findByGoodsType(GoodsType goodsType,Pageable pageable);

    @Query(value = "select o from Goods o where o.code = ?1 and o.status = 1")
    Page<Goods> findByCode(String code,Pageable pageable);

//    @Query(value = "select new com.h9.common.modle.DiDiCardInfo(o.realPrice,count(o.id))  from Goods o where o.status =1 and o.goodsType = 2 group by o.realPrice")
//    List<DiDiCardInfo> findRealPriceAndStock();

    @Query(value = "select * from goods  where status = 1 and real_price = ?1 and goods_type_id = 2 limit 0,1",nativeQuery = true)
    Goods findByTop1(BigDecimal realPrice);

    @Query("select o from Goods o  order by o.status asc , o.id desc ")
    Page<Goods> findAllByPage(Pageable page);

    @Query("select o from Goods o where o.goodsType.code <> 'didi_card' and o.goodsType.code <> 'mobile_recharge' and o.status = 1")
    Page<Goods> findStoreGoods(Pageable pageable);

    @Query("select o from Goods o where o.goodsType.code =?1 and o.status=1")
    Page<Goods> findStoreGoods(String code,Pageable pageable);

    @Query("select o from Goods o where o.status = 1 and o.goodsType.code <> 'didi_card' and o.goodsType.code <> 'mobile_recharge' order by o.updateTime desc")
    Page<Goods> findLastUpdate(Pageable pageable);

    /**
     * description: 查询最后兑换的商品
     */
    @Query(value = "select g.* from goods g, goods_type gtype where gtype.`code` <> 'mobile_recharge'" +
            " and gtype.`code` <> 'didi_card' and g.goods_type_id =gtype.id AND g.status = 1 order by  g.update_time desc limit 0,6",nativeQuery = true)
    List<Goods> findLastConvertGoods();

    @Query("select o from Goods o where id = ?1")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Goods findByLockId(Long id);

}
