package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.config.Announcement;

/**
 * Created by 李圆
 * on 2017/11/20
 */
public interface AnnouncementRepository extends BaseRepository<Announcement> {

    
    Announcement findOne(Long id);

}
