package com.h9.common.db.repo;

import com.h9.admin.model.vo.BlackAccountVO;
import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.SystemBlackList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 
 * Created by Gonyb on 2017/11/13.
 */
@Repository
public interface SystemBlackListRepository extends BaseRepository<SystemBlackList> {
    SystemBlackList findByUserIdAndStatus(Long userId,Integer status);
    SystemBlackList findByImeiAndStatus(String imei,Integer status);
    
    @Query("select new com.h9.admin.model.vo.BlackAccountVO(s.id,s.userId,u.nickName,u.phone,u.openId,s.createTime,s.endTime,s.cause)" +
            " from SystemBlackList s,User u where s.userId is not null and s.status = 1 and u.id=s.userId")
    Page<BlackAccountVO> findAllAccount(Pageable pageable);

    @Query("select new com.h9.admin.model.vo.BlackAccountVO(s.id,s.imei,s.createTime,s.endTime,s.cause)" +
            " from SystemBlackList s where s.imei is not null and s.status = 1")
    Page<BlackAccountVO> findAllImei(Pageable pageable);
}
