package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.China;
import com.h9.common.db.entity.City;
import com.h9.common.db.entity.Province;

import org.springframework.data.jpa.repository.Modifying;
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
    @Query("select p.parentCode from  China p where p.name = ?1")
    String  findPid(String name);

    /**
     * 查询对应子区域的id
     * @param parentCode
     * @param name
     * @return id
     */
    @Query("select c.id from China c where c.parentCode = ?1 and c.name = ?2 and status = 1")
    String findCid(String parentCode,String name);

    /**
     * 所有省
     * @return
     */
    @Query("select c from  China c where c.level = 1")
    List<China> findAllProvinces();

    @Query("select c from  China c where c.level = ?1")
    List<China> findByLevel(Integer level);

    @Modifying
    @Query("update China c set c.pid = ?1 where c.code=?2 ")
    void updateALL(Long pid,String code);

    China findByCode(String parentcode);

}
