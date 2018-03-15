package com.h9.admin.model.vo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Setter;
import org.apache.commons.collections.IterableMap;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:</p>
 *
 * @author LiYuan
 * @Date 2018/3/14
 */
@Setter
@ApiModel("素材列表信息")
public class WXmatterVO {

    @ApiModelProperty("该类型的素材的总数")
    private Integer total_count;

    @ApiModelProperty("本次调用获取的素材的数量")
    private Integer item_count;

    private List<item> item;


    public List<com.h9.admin.model.vo.item> getItem() {
        return item;
    }

    public Integer getTotal_count() {
        return total_count;
    }

    public Integer getItem_count() {
        return item_count;
    }
}
