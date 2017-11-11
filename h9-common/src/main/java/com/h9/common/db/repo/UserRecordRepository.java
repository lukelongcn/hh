package com.h9.common.db.repo;


import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.UserRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: UserRecordRepository
 * @Description: UserRecord 的查询
 * @author: shadow.liu
 * @date: 2016年6月27日 下午3:18:36
 */
@Repository
public interface UserRecordRepository extends BaseRepository<UserRecord> {
    
    @Query(value = "select u.id,ur.imei," +
            "SELECT COUNT(s.*) FROM (select distinct u1.user_id from user_record u1 where u1.imei = ur.imei) s," +
            "(select count(l2) from lottery_log l2 where l2.user_id in (select distinct u1.user_id from user_record u1 where u1.imei = ur.imei)))" +
            " from user_record ur,User u " +
            "where u.id=ur.user_id " +
            "and ur.create_time between ?1 and ?2 ",nativeQuery = true)
    List<Object[]> getUserRecordByTime(Date startTime, Date endTime);
    

}
