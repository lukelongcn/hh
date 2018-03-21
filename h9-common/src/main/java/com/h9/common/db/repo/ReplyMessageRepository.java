package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.base.PageResult;
import com.h9.common.db.entity.wxEvent.ReplyMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Ln on 2018/3/5.
 */
public interface ReplyMessageRepository extends BaseRepository<ReplyMessage> {

//    List<ReplyMessage> findByKeyWordRegex(String regex);

    List<ReplyMessage> findByEventType(String eventType);

    @Query(value = "select * from reply_message  where order_name = ?1 and status = 1 order by sort DESC limit 1"
            ,nativeQuery = true)
    ReplyMessage fingByOrderName(String orderName);

    @Query("select r from ReplyMessage r where r.keyWord = ?1 and r.status = 1")
    ReplyMessage fingByAllKey(String key);

    @Query(value = "select * from reply_message where key_word like ?1  and status = 1 order by sort DESC limit 1"
            ,nativeQuery = true)
    ReplyMessage fingByHalfKey(String key);

    @Query(value = "SELECT * FROM `reply_message` WHERE content = ?1 REGEXP '[:alnum:]' and status = 1 " +
            "ORDER BY sort LIMIT 1 ",nativeQuery = true)
    ReplyMessage fingByRegexp(String key);

    @Query(value = "select * from reply_message  where order_name LIKE '%自动回复%' and status = 1 order by sort DESC limit 1"
            ,nativeQuery = true)
    ReplyMessage findOneKey();

    @Query("select r from ReplyMessage r order by r.id")
    Page<ReplyMessage> findAllDetail(Pageable pageRequest);
    default PageResult<ReplyMessage> findAllDetail(Integer page, Integer limit){
        Page<ReplyMessage> List =  findAllDetail(pageRequest(page,limit));
        return new PageResult(List);
    }


}
