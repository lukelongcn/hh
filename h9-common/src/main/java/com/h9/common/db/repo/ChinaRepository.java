package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.China;
import com.h9.common.db.entity.City;
import com.h9.common.db.entity.Province;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by 李圆 on 2017/11/30
 */

public interface ChinaRepository extends BaseRepository<China> {

    /**
     * 查询对应省的id
     * @param name
     * @return
     */
    @Query("select p.parentCode.code from  China p where p.name = ?1")
    String  findPid(String name);

    /**
     * 查询对应子区域的id
     * @param parentCode
     * @param name
     * @return id
     */
    @Query("select c.id from China c where c.parentCode.code = ?1 and c.name = ?2 and status = 1")
    String findCid(String parentCode,String name);

    /**
     * 所有子区域信息
     * @return List<China>
     */
    @Query("select c from China c where c.parentCode.code = ?1 and status = 1 order by c.id")
    List<China> findChlid(String parentCode);

    /**
     * 所有省
     * @return
     */
    @Query("select c from  China c where c.level = 1")
    List<China> findAllProvinces();

    /**
     * 省对应市
     * @param parentCode
     * @return
     */
    @Query("select c from  China c where c.parentCode.code = ?1 and c.level = 2")
    List<China> findCities(String parentCode);
    /**
     * 市对应区
     * @param parentCode
     * @return
     */
    @Query("select c from  China c where c.parentCode.code = ?1 and c.level = 3")
    List<China> findAreas(String parentCode);
}
