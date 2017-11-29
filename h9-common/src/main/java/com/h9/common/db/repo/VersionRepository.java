package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.Version;
import com.h9.common.modle.vo.admin.basis.VersionVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

/**
 * @author: George
 * @date: 2017/11/28 16:35
 */
public interface VersionRepository extends BaseRepository<Version> {
    @Query("select new com.h9.common.modle.vo.admin.basis.VersionVO(o) from Version o order by o.id desc")
    Page<VersionVO> findAllByPage(Pageable pageable);
}