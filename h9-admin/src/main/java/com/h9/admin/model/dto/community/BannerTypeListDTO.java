package com.h9.admin.model.dto.community;

import com.h9.common.modle.dto.PageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * BannerTypeListDTO:刘敏华 shadow.liu@hey900.com
 * Date: 2018/2/1
 * Time: 14:54
 */
@Data
public class BannerTypeListDTO extends PageDTO{
    @ApiModelProperty(value = "所属页面")
    private Integer localtion;

}
