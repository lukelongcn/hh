package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.base.PageResult;
import com.h9.common.db.entity.community.StickReward;
import com.h9.common.db.entity.user.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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

   @Query("select distinct(s.user) from StickReward s where  s.stick.id = ?1")
    List<User> findByStickId(Long stickId);

    @Query("select s from StickReward s where s.user.id = ?1 order by s.createTime DESC ")
    Page<StickReward> findGiveList(long userId, Pageable pageable);
    default PageResult<StickReward> findGiveList(long userId, Integer page, Integer limit){
        Page<StickReward> rewardList = findGiveList(userId,pageRequest(page,limit));
        return new PageResult(rewardList);
    }

    @Query("select sum(u.reward) from StickReward u where u.stick.id = ?1")
    BigDecimal findRewardCount(Long stickId);
}
