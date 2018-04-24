package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.custom.CustomModule;
import com.h9.common.db.entity.custom.CustomModuleItems;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Ln on 2018/4/23.
 */
public interface CustomModuleItmesRep extends BaseRepository<CustomModuleItems> {

    @Query("select o from CustomModuleItems  o where o.customModule = ?1 ")
    List<CustomModuleItems> findByCustomModule(CustomModule customModule);

    @Modifying
    @Query("update CustomModuleItems o set o.delFlag = 1 where o.customModule = ?1 and o.delFlag <> 1")
    Integer deleteByCustomModule(CustomModule customModule);

    @Query("select o from CustomModuleItems o where o.delFlag = ?1")
    Page<CustomModuleItems> findByDelFlag(Integer delFlag, Pageable pageable);
}
