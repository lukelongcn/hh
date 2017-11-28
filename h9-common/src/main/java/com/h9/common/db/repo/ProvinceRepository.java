package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.Province;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by 李圆 on 2017/11/27
 */
public interface ProvinceRepository  extends BaseRepository<Province> {

    /**
     * 所有省信息
     * @return
     */
    @Query("select p from Province p ")
    List<Province> findAllProvinces();

    @Query("select p from Province p where name = ?1")
    Long findByName(String name);
}
