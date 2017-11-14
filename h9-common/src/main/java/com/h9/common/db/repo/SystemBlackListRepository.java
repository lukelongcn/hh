package com.h9.common.db.repo;

import com.h9.admin.model.vo.BlackAccountVO;
import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.SystemBlackList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 
 * Created by Gonyb on 2017/11/13.
 */
@Repository
public interface SystemBlackListRepository extends BaseRepository<SystemBlackList> {
    @Query("select s from SystemBlackList s where s.userId=?1 and s.status=1 and ?2 > s.startTime and ?2 < s.endTime")
    SystemBlackList findByUserIdAndStatus(Long userId,Date now);
    @Query("select s from SystemBlackList s where s.imei=?1 and s.status=1 and ?2 > s.startTime and ?2 < s.endTime")
    SystemBlackList findByImeiAndStatus(String imei,Date now);
    
    @Query("select new com.h9.admin.model.vo.BlackAccountVO(s.id,s.userId,u.nickName,u.phone,u.openId,s.createTime,s.endTime,s.cause)" +
            " from SystemBlackList s,User u where s.userId is not null and s.status = 1 " +
            "and u.id=s.userId and ?1 > s.startTime and ?1 < s.endTime")
    Page<BlackAccountVO> findAllAccount(Date now,Pageable pageable);

    @Query("select new com.h9.admin.model.vo.BlackAccountVO(s.id,s.imei,s.createTime,s.endTime,s.cause)" +
            " from SystemBlackList s where s.imei is not null and s.status = 1 and ?1 > s.startTime and ?1 < s.endTime")
    Page<BlackAccountVO> findAllImei(Date now,Pageable pageable);

    @Query(value = "select o from SystemBlackList o where ?3 > o.startTime and ?3 < o.endTime and o.status = 1 and (o.userId = ?1 or o.imei = ?2)")
    SystemBlackList findByUserIdOrImei(Long userId, String imei, Date date);
}
