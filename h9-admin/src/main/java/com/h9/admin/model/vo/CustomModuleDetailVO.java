package com.h9.admin.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import sun.rmi.transport.ObjectTable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

/**
 * Created by Ln on 2018/4/23.
 */
@Data
@ApiModel
public class CustomModuleDetailVO {
    @ApiModelProperty("类型")
    private Long type;

    @ApiModelProperty("名字")
    private String name;

    @ApiModelProperty("组件信息")
    private List<Info> infos;

    @ApiModel("组件信息,第一个无素为瓶身定制信息,第二个为瓶箱定制信息")
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Info {
        @ApiModelProperty("主图")
        private List<String> mainImages;

        @ApiModelProperty("定制化图片组件数量")
        private Integer customImagesCount;

        @ApiModelProperty("定制化文本组件数量")
        private Integer textCount;
    }

    @ApiModelProperty("商品信息")
    private List<Map<String, String>> goodsList;

    @ApiModelProperty("模块Id")
    private Long customModuleId;
}
