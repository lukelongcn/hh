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

//    List<ReplyMessage> findByEventType(String eventType);

    @Query(value = "select * from reply_message  where status = 1 and match_strategy=5 order by sort DESC,update_time DESC limit 1"
            ,nativeQuery = true)
    ReplyMessage fingByOrderName();

    @Query(value ="select * from reply_message  where key_word = ?1 and match_strategy=1 and status = 1 order by sort DESC ,update_time DESC limit 1",nativeQuery = true)
    ReplyMessage fingByAllKey(String key);

    @Query(value = "select * from reply_message where key_word like ?1 and match_strategy=2 and status = 1 order by sort DESC,update_time DESC limit 1"
            ,nativeQuery = true)
    ReplyMessage fingByHalfKey(String key);

    @Query(value = "SELECT * FROM `reply_message` WHERE ?1 REGEXP key_word and match_strategy=3 AND status = 1 " +
            "order by sort DESC ,update_time DESC LIMIT 1 ",nativeQuery = true)
    ReplyMessage fingByRegexp(String key);

    @Query(value = "select * from reply_message  where match_strategy=4 and status = 1 order by sort DESC,update_time DESC limit 1"
            ,nativeQuery = true)
    ReplyMessage findOneKey();

    @Query("select r from ReplyMessage r order by sort DESC,update_time DESC")
    Page<ReplyMessage> findAllDetail(Pageable pageRequest);
    default PageResult<ReplyMessage> findAllDetail(Integer page, Integer limit){
        Page<ReplyMessage> List =  findAllDetail(pageRequest(page,limit));
        return new PageResult(List);
    }


}
