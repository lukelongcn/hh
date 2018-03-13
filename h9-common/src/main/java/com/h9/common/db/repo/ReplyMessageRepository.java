package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.wxEvent.ReplyMessage;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Ln on 2018/3/5.
 */
public interface ReplyMessageRepository extends BaseRepository<ReplyMessage> {

//    List<ReplyMessage> findByKeyWordRegex(String regex);

    List<ReplyMessage> findByEventType(String eventType);

    @Query("select r from ReplyMessage r where r.orderName = ?1 and r.status = 1")
    ReplyMessage fingByOrderName(String orderName);

    @Query("select r from ReplyMessage r where r.keyWord = ?1 and r.status = 1")
    ReplyMessage fingByAllKey(String key);

    @Query("select r from ReplyMessage r where r.keyWord like ?1 and r.status = 1")
    ReplyMessage fingByHalfKey(String key);

    @Query(value = "SELECT * FROM `reply_message` WHERE content = ?1 REGEXP '[:alnum:]' and status = 1 " +
            "ORDER BY sort LIMIT 1 ",nativeQuery = true)
    ReplyMessage fingByRegexp(String key);

    @Query(value = "select * from reply_message  where order_name = '自动回复' and status = 1 order by id limit 1",nativeQuery = true)
    ReplyMessage findOneKey();

    @Query("select r from ReplyMessage r where  r.status = 1 order by r.id")
    List<ReplyMessage> findAllDetail();
}
