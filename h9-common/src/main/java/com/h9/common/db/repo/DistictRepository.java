package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.City;
import com.h9.common.db.entity.Distict;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by 李圆 on 2017/11/29
 */
public interface DistictRepository extends BaseRepository<Distict> {

    /**
     * 所有区信息
     * @return List<City>
     */
    @Query("select d from  Distict d  ")
    List<Distict> findAllDisticts();

    /**
     * 查询对应city的id
     * @param cid
     * @param name
     * @return id
     */
    @Query("select d.id from Distict d where d.city.id = ?1 and d.name = ?2")
    Long findDid(Long cid,String name);

    /**
     * 对应市内区信息
     * @return List<City>
     */
    @Query("select d from  Distict d where d.city.id = ?1 order by d.id")
    List<Distict> findDisticts(Long cid);


}
