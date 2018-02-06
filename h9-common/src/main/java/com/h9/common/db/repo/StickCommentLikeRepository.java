package com.h9.common.db.repo;


import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.community.StickCommentLike;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: StickCommentLikeRepository
 * @Description: StickCommentLike 的查询
 * @author: shadow.liu
 * @date: 2016年6月27日 下午3:18:36
 */
@Repository
public interface StickCommentLikeRepository extends BaseRepository<StickCommentLike> {


    @Query("select s from StickCommentLike  s where s.userId = ?1 and s.stickCommentId = ?2")
    StickCommentLike findByUserIdAndStickCommentId(long id, long userId);

}
