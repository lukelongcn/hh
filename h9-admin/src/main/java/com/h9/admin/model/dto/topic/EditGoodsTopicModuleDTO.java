package com.h9.admin.model.dto.topic;

import com.h9.common.base.Result;
import com.h9.common.common.ServiceException;
import com.h9.common.utils.CheckoutUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

/**
 * Created by Ln on 2018/4/8.
 */
@Data
@ApiModel
public class EditGoodsTopicModuleDTO {

    @ApiModelProperty("专题模块Id")
    private Long topicModuleId;

    @ApiModelProperty("图片")
    private String img;

    public void setIds(Map<Long, Integer> ids) {

        this.ids = ids;
        ids.forEach((k, v) -> {
            if (v < 1) {
                throw new ServiceException(1, "商品的排序不能为小于1");
            }
        });
    }

    @ApiModelProperty("{goodsId:sort} 此类型的Json对象")
    private Map<Long, Integer> ids;


    @ApiModelProperty("排序")
    private Integer sort = 1;

    public void setSort(String sort) {
        if (!CheckoutUtil.isNumeric(sort)) {
            throw new ServiceException(1, "排序值请输入数字");
        }
        Integer ints = Integer.valueOf(sort);
        if (ints < 1) {
            throw new ServiceException(1, "排序最小值为1");
        }

        this.sort = ints;
    }

    @ApiModelProperty("专题Id")
    private Long topicTypeId;


}
