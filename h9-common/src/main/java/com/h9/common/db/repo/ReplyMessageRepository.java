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

    @Query(value = "select * from reply_message  where order_name = '自动回复' and status = 1 order by sort DESC limit 1"
            ,nativeQuery = true)
    ReplyMessage findOneKey();

    @Query("select r from ReplyMessage r where  r.status = 1 order by r.id")
    List<ReplyMessage> findAllDetail();


    @Query("select r from ReplyMessage r where r.orderName =?1")
    Page<ReplyMessage> findOrderName(String orderName, Pageable pageRequest);

    default PageResult<ReplyMessage> findOrderName(String orderName, Integer page, Integer limit){
        Page<ReplyMessage> orderNameList =  findOrderName(orderName, pageRequest(page,limit));
        return new PageResult(orderNameList);
    }

    @Query("select r from ReplyMessage r")
    Page<ReplyMessage> findAllList(Pageable pageRequest);

    default PageResult<ReplyMessage> findAllList(Integer page, Integer limit){
        Page<ReplyMessage> allList =  findAllList(pageRequest(page,limit));
        return new PageResult(allList);
    }

    @Query("select r from ReplyMessage r where r.orderName =?1 and r.contentType =?2")
    Page<ReplyMessage> findOrderNameAndContentType(String orderName,String contentType, Pageable pageRequest);

    default PageResult<ReplyMessage> findOrderNameAndContentType(String orderName,String contentType, Integer page
            , Integer limit){
        Page<ReplyMessage> messageList =  findOrderNameAndContentType(orderName, contentType,pageRequest(page,limit));
        return new PageResult(messageList);
    }

    @Query("select r from ReplyMessage r where r.orderName =?1 and r.contentType =?2")
    Page<ReplyMessage> findOrderNameAndContentTypeAndStatus(String orderName,String contentType,Integer status
            , Pageable pageRequest);

    default PageResult<ReplyMessage> findOrderNameAndContentTypeAndStatus(String orderName,String contentType
            , Integer status, Integer page, Integer limit){
        Page<ReplyMessage> messageList =  findOrderNameAndContentTypeAndStatus(orderName, contentType, status
                , pageRequest(page,limit));
        return new PageResult(messageList);
    }

    @Query("select r from ReplyMessage r where r.orderName =?1 and r.status =?2")
    Page<ReplyMessage> findOrderNameAndStatus(String orderName,Integer status, Pageable pageRequest);

    default PageResult<ReplyMessage> findOrderNameAndStatus(String orderName,Integer status, Integer page
            , Integer limit){
        Page<ReplyMessage> messageList =  findOrderNameAndStatus(orderName,status,pageRequest(page,limit));
        return new PageResult(messageList);
    }
}
