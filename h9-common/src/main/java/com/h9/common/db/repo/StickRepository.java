package com.h9.common.db.repo;


import com.h9.common.base.BaseRepository;
import com.h9.common.base.PageResult;
import com.h9.common.db.entity.community.Stick;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sun.util.calendar.BaseCalendar;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: StickRepository
 * @Description: Stick 的查询
 * @author: shadow.liu
 * @date: 2016年6月27日 下午3:18:36
 */
@Repository
public interface StickRepository extends BaseRepository<Stick> {

    @Query("select s from Stick s where s.state = 1 order by s.createTime desc")
    Page<Stick> find4Home(Pageable pageable);


    @Query("select s from Stick s where s.state = 1 order by s.readCount desc")
    Page<Stick> find4Hot( Pageable pageable);


    @Query("select s from Stick s where s.stickType.id=?1 and s.state = 1  order by s.updateTime desc")
    Page<Stick> findType(Long id,Pageable pageable);

    @Query("select s from Stick s where  s.stickType.name=?1 and s.state = 1 order by s.updateTime desc")
    Page<Stick> findType(String name, Pageable pageable);


    @Query("select s from Stick s where s.title like ?1 and s.state = 1 order by s.createTime DESC")
    Page<Stick> findStickList(String str, Pageable pageRequest);


    @Query("select s from Stick s where s.id = ?1 and s.state =1")
    Stick findById(long stickId);

    @Query("select s from Stick s where s.user.id = ?1 and s.state = 1 order by s.createTime DESC ")
    Page<Stick> findPersonalStickList(long userId,Pageable pageable);

    @Query("select s.id from Stick s where s.user.id = ?1 and s.state = 1 order by s.createTime DESC ")
    List<Long> findStickIdByUserId(long userId);
}
