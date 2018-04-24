package com.h9.common.db.repo;

import com.h9.common.base.BaseEntity;
import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.custom.CustomModule;
import com.h9.common.db.entity.custom.CustomModuleItems;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Ln on 2018/4/23.
 */
public interface CustomModuleRep extends BaseRepository<CustomModule> {
    @Query("select o from CustomModule o where o.delFlag = ?1")
    Page<CustomModule> findByDelFlag(Integer delFlag, Pageable pageable);

    @Query("select o from CustomModule o where o.delFlag = ?2 and o.moduleTypeId = ?1")
    Page<CustomModule> findByModuleTypeId(Long typeId,Integer delFlag,Pageable pageable);
}
