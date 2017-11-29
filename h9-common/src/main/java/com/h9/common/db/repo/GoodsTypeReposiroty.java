package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.GoodsType;
import com.h9.common.modle.vo.admin.transaction.GoodsTypeVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by itservice on 2017/11/2.
 */
public interface GoodsTypeReposiroty extends BaseRepository<GoodsType>{

    GoodsType findByCode(Integer code);

    List<GoodsType> findByStatus(Integer status);

    @Query("select new com.h9.common.modle.vo.admin.transaction.GoodsTypeVO(o) from GoodsType o order by o.status asc ,o.id desc ")
    Page<GoodsTypeVO> findAllByPage(Pageable pageable);

}
