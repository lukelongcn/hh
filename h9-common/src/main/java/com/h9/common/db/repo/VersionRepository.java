package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.config.Version;
import com.h9.common.modle.vo.admin.basis.VersionVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author: George
 * @date: 2017/11/28 16:35
 */
public interface VersionRepository extends BaseRepository<Version> {
    @Query("select new com.h9.common.modle.vo.admin.basis.VersionVO(o) from Version o order by o.id desc")
    Page<VersionVO> findAllByPage(Pageable pageable);

    @Query(value = "select o.* from version o where o.client_type = ?1 and o.version_number > ?2 and upgrade_type = 3",nativeQuery = true)
    List<Version> findForceUpdateVersion(Integer clientType, Integer version);

    @Query(value = "select o.* from version o where o.client_type = ?1 and o.version_number > ?2 order by o.version_number desc limit 0,1",nativeQuery = true)
    Version findLastVersion(Integer clientType,Integer version);

    Version findByClientType(Integer client_type);


    @Query(value = "select o.* from version o where o.client_type = ?1 order by o.create_time desc limit 0,1",nativeQuery = true)
    Version findNewVersion(Integer clientType);

}