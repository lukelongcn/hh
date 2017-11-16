package com.h9.common.db.repo;


import com.h9.admin.model.vo.ImeiUserRecordVO;
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
    
    @Query(value = "select DISTINCT new com.h9.admin.model.vo.ImeiUserRecordVO(ur.imei," +
            "(select count(l2) from LotteryLog l2 where l2.userId in (select distinct u1.userId from UserRecord u1 where u1.imei = ur.imei))" +
            ") from UserRecord ur" +
            " where ur.createTime between ?1 and ?2 AND ur.imei IS NOT null")
    List<ImeiUserRecordVO> getUserRecordByTime(Date startTime, Date endTime);

    /**
     * 根据imei查找关联账号数
     * @param imei
     * @return
     */
    @Query(value = "SELECT COUNT(*) FROM (select distinct u1.user_id from user_record u1 where u1.imei = ?1) s",nativeQuery = true)
    Long findRelevanceCount(String imei);
}
