package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.base.PageResult;
import com.h9.common.db.entity.community.StickReward;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 李圆 on 2018/1/14
 */
@Repository
public interface StickRewardResitory extends BaseRepository<StickReward> {

    @Query("select s from StickReward s where s.stick.id = ?1 order by s.createTime DESC ")
    Page<StickReward> findRewardList(long stickId, Pageable pageable);
    default PageResult<StickReward> findRewardList(long stickId, Integer page, Integer limit){
       Page<StickReward> rewardList = findRewardList(stickId,pageRequest(page,limit));
       return new PageResult(rewardList);
   }

   @Query("select s from StickReward s where s.stick.id = ?1")
    List<StickReward> findByStickId(Long stickId);
}
