package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.City;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by 李圆 on 2017/11/27
 */
public interface CityRepository extends BaseRepository<City> {
    /**
     * 所有市信息
     * @return List<City>
     */
    @Query("select c from City c  ")
    List<City> findAllCities();

    /**
     * 查询对应city的id
     * @param pid
     * @param name
     * @return id
     */
    @Query("select c.id from City c where c.province.id = ?1 and c.name = ?2")
    Long findCid(Long pid,String name);
}
