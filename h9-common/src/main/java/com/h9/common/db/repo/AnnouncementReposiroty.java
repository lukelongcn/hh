package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.config.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by itservice on 2017/11/14.
 */
public interface AnnouncementReposiroty extends BaseRepository<Announcement>{
    @Query(value = "select o from Announcement o where  o.enable = 1 order by o.sort")
    List<Announcement> findActived();

    @Query("select o from Announcement o  order by o.enable desc ,o.sort desc ,o.id desc ")
    Page<Announcement> findAllByPage(Pageable page);
}
