package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.custom.CustomModuleGoods;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Ln on 2018/4/23.
 */
public interface CustomModuleGoodsRep extends BaseRepository<CustomModuleGoods> {

    @Modifying
    @Query("update   CustomModuleGoods o set o.delFlag =1 where o.customModuleId = ?1 and o.delFlag <> 1")
    Integer deleteByCustomModuleId(Long customModuleId);

    @Query("select o from CustomModuleGoods o where o.delFlag = ?1 and o.customModuleId = ?2")
    List<CustomModuleGoods> findByCustomModuleId(Integer delFlag,Long customModuleId);
}
