package com.h9.admin.model.vo;

import com.h9.common.db.entity.order.GoodsTopic;
import com.h9.common.db.entity.order.GoodsTopicRelation;
import com.h9.common.db.entity.order.GoodsTopicType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ln on 2018/4/8.
 */
@Data
@NoArgsConstructor
public class GoodsTopicVO {

    private String img = "";
    private Integer sort = 0;
    private List<GoodsInfo> goodsList = new ArrayList<>();


    private static class GoodsInfo {
        private long id;
        private long relationId;
        private String name = "";
        private int sort;

        public GoodsInfo() {
        }

        public GoodsInfo(Long id, String name, int sort,Long relationId) {
            this.id = id;
            this.name = name;
            this.sort = sort;
            this.relationId = relationId;
        }

        public long getRelationId() {
            return relationId;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }

    public GoodsTopicVO(GoodsTopic goodsTopic, List<GoodsTopicRelation> goodsTopicRelations) {
        this.img = goodsTopic.getImg();
        this.sort = goodsTopic.getSort();

        List<GoodsInfo> list = goodsTopicRelations.stream().map(goodsTopicRelation -> {
            GoodsInfo goodsInfo = new GoodsInfo(goodsTopicRelation.getGoodsId(),
                    goodsTopicRelation.getName(),
                    goodsTopicRelation.getSort(),goodsTopicRelation.getId());

            return goodsInfo;
        }).collect(Collectors.toList());

        this.goodsList = list;
    }

}
