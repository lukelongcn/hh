package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.order.GoodsTopicRelation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Ln on 2018/4/8.
 */
public interface GoodsTopicRelationRep extends BaseRepository<GoodsTopicRelation> {


    List<GoodsTopicRelation> findByGoodsTopicId(Long topicId);

    List<GoodsTopicRelation> findByGoodsId(Long goodsId);

    @Query("select o from GoodsTopicRelation o where o.del_flag =?2 and o.goodsId = ?1 and o.goodsTopicId = ?3")
    List<GoodsTopicRelation> findByGoodsIdAndTopicId(Long goodsId, Integer delFlag, Long goodsTopicId);

    @Modifying
    @Query("update GoodsTopicRelation o set o.del_flag = 1 where o.goodsTopicId = ?1")
    Integer updateStatus(Long goodsTopicId);
}
