package com.h9.admin.model.dto.topic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

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

    @ApiModelProperty("{goodsId:sort} 此类型的Json对象")
    private Map<Long, Integer> ids;



    @Min(value = 1, message = "最小值为1")
    @ApiModelProperty("排序")
    private Integer sort = 1;


    @ApiModelProperty("专题Id")
    private Long topicTypeId;


}
