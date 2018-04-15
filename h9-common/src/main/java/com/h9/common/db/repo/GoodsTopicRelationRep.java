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


    @Query("select o from GoodsTopicRelation o where o.goodsTopicModuleId = ?1 and o.del_flag = 0 order by o.sort")
    List<GoodsTopicRelation> findByGoodsTopicModuleId(Long topicModuleId);

    List<GoodsTopicRelation> findByGoodsId(Long goodsId);

    @Query("select o from GoodsTopicRelation o where o.del_flag =?2 and o.goodsId = ?1 and o.goodsTopicModuleId = ?3")
    List<GoodsTopicRelation> findByGoodsIdAndTopicId(Long goodsId, Integer delFlag, Long goodsTopicId);

    @Modifying
    @Query("update GoodsTopicRelation o set o.del_flag = 1 where o.goodsTopicModuleId = ?1")
    Integer updateStatus(Long goodsTopicModuleId);

    @Query("select o from GoodsTopicRelation  o where o.del_flag  = ?1 and o.goodsTopicTypeId=?2")
    List<GoodsTopicRelation> findByTypeId(Integer delFlag, Long typeId);
}
