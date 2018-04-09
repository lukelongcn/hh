package com.h9.admin.model.vo;

import com.h9.common.db.entity.order.GoodsTopicModule;
import com.h9.common.db.entity.order.GoodsTopicRelation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel
public class GoodsTopicVO {

    @ApiModelProperty("图片")
    private String img = "";
    @ApiModelProperty("排序")
    private Integer sort = 0;
    @ApiModelProperty("商品信息")
    private List<GoodsInfo> goodsList = new ArrayList<>();


    @ApiModel
    public static class GoodsInfo {
        private long id;
        @ApiModelProperty("")
        private long relationId;
        @ApiModelProperty("关联Id")
        private String name = "";
        @ApiModelProperty("排序")
        private int sort;

        public GoodsInfo() {
        }

        public GoodsInfo(Long id, String name, int sort, Long relationId) {
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

    public GoodsTopicVO(GoodsTopicModule goodsTopicModule, List<GoodsTopicRelation> goodsTopicRelations) {
        this.img = goodsTopicModule.getImg();
        this.sort = goodsTopicModule.getSort();

        List<GoodsInfo> list = goodsTopicRelations.stream().map(goodsTopicRelation -> {
            GoodsInfo goodsInfo = new GoodsInfo(goodsTopicRelation.getGoodsId(),
                    goodsTopicRelation.getName(),
                    goodsTopicRelation.getSort(), goodsTopicRelation.getId());

            return goodsInfo;
        }).collect(Collectors.toList());

        this.goodsList = list;
    }

}
