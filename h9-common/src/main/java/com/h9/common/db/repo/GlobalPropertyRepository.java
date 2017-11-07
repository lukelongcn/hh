package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.GlobalProperty;
import com.h9.common.modle.vo.GlobalPropertyVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

/**
 * @author: George
 * @date: 2017/11/3 10:57
 */
public interface GlobalPropertyRepository extends BaseRepository<GlobalProperty> {

    GlobalProperty findByCode(String code);

    GlobalProperty findByIdNotAndCode(long id,String code);

    @Query("select new com.h9.common.modle.vo.GlobalPropertyVO(o) from GlobalProperty o  order by o.id desc ")
    Page<GlobalPropertyVO> findAllByPage(Pageable page);

}
