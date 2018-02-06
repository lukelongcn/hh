package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.base.PageResult;
import com.h9.common.db.entity.community.StickReport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by 李圆 on 2018/1/12
 */
@Repository
public interface StickReportRepository extends BaseRepository<StickReport> {

    @Query("select a from StickReport a order by a.createTime DESC")
    Page<StickReport> findReportList(Pageable pageRequest);

    default PageResult<StickReport> findReportList(int page, int limit) {
        Page<StickReport> reportList = findReportList(pageRequest(page, limit));
        return new PageResult(reportList);
    }

}
