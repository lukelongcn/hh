package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.Announcement;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by itservice on 2017/11/14.
 */
public interface AnnouncementReposiroty extends BaseRepository<Announcement>{
    @Query(value = "select o from Announcement o where o.startTime < ?1 and (o.endTime is null or o.endTime > ?1 ) and o.enable = 1 order by o.sort")
    List<Announcement> findActived(Date date);
}
