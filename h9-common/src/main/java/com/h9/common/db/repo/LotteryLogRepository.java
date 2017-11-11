package com.h9.common.db.repo;


import com.h9.admin.model.vo.UserRecordVO;
import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.LotteryLog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: LotteryLogRepository
 * @Description: LotteryLog 的查询
 * @author: shadow.liu
 * @date: 2016年6月27日 下午3:18:36
 */
@Repository
public interface LotteryLogRepository extends BaseRepository<LotteryLog> {

    @Query("select count(distinct ll.code) from LotteryLog  ll  where ll.userId = ?1 and ll.createTime between ?2 and ?3 ")
    BigDecimal getLotteryCount(Long userId,Date startDate,Date endDate);

    @Query("select count(distinct ll.code) from LotteryLog  ll  where ll.userId = ?1 and ll.code = ?2")
    BigDecimal getLotteryCount(Long userId,String code);
    
    @Query("select distinct new com.h9.admin.model.vo.UserRecordVO(ll.userId,u.nickName,u.phone,u.openId," +
            "(select count(l1) from Lottery l1 where l1.userId=u.id and l1.roomUser=1)," +  //开瓶次数
            "(select count(l2) from LotteryLog l2 where l2.userId=u.id and l2.status=1)) " + //参与次数 只要扫了码  就算参与
            "from LotteryLog ll,User u  " +
            "where u.id=ll.userId " +
            "and ll.createTime between ?1 and ?2 " +
            "and (?3 is null or ll.userId like ?3 or u.phone like ?3)")
    List<UserRecordVO> getUserList(Date startTime, Date endTime, String key);

}
