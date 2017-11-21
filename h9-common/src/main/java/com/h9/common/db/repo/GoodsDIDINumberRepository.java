package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.GoodsDIDINumber;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by itservice on 2017/11/6.
 */
public interface GoodsDIDINumberRepository extends BaseRepository<GoodsDIDINumber>{

    @Query(value = "select count(o.id) from GoodsDIDINumber o where o.status = 1 and o.goodsId = ?1 ")
    Object getCount(Long goodId);

    @Query(value = "select * from goods_didi_number where goods_id = ?1 and status = 1 limit 0,1",nativeQuery = true)
    GoodsDIDINumber findByGoodsId(Long goodsId);

    @Query(value = "select gdn from GoodsDIDINumber gdn where  gdn.didiNumber = ?1")
    GoodsDIDINumber findByGoodsAndDidiNumber(String number);
}
