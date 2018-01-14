package com.h9.common.db.repo;


import com.h9.common.base.BaseRepository;
import com.h9.common.base.PageResult;
import com.h9.common.db.entity.community.StickComment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @ClassName: StickCommentRepository
 * @Description: StickComment 的查询
 * @author: shadow.liu
 * @date: 2016年6月27日 下午3:18:36
 */
@Repository
public interface StickCommentRepository extends BaseRepository<StickComment> {

    @Query("select s from StickComment s where s.id = ?1 and s.state = 1 and s.operationState = 1")
    StickComment findById(long id);

    @Query("select s from StickComment s where s.stick.id = ?1 and s.level = 1 and s.state = 1  and " +
            "s.operationState = 1 order by s.createTime DESC")
    Page<StickComment> findStickCommentList(long stickId, Pageable pageRequest);

    default PageResult<StickComment> findStickCommentList(long stickId, Integer page, Integer limit){
        Page<StickComment> stickComment =  findStickCommentList(stickId, pageRequest(page,limit));
        return new PageResult(stickComment);
    }


    @Query("select s from StickComment s where s.id = ?1 and  s.state = 1 order by s.createTime ASC ")
    List<StickComment> findByBackId(long stickCommentParentId);
}
