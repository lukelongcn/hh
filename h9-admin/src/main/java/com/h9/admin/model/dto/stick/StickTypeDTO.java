package com.h9.admin.model.dto.stick;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * StickTypeDTO:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/29
 * Time: 17:29
 */
@Data
public class StickTypeDTO {

    private Long id;
    private Long stickTypeId;
    @NotBlank(message = "请填写分类名称")
    @Length(min = 2,max = 64,message = "分类名称填写过长或过短")
    private String name;
    @NotBlank(message = "请填写分类描述")
    private String content;
    @NotBlank(message = "请选择图标")
    private String image;

    // 限制发帖 1不限制 2限制'
    @NotNull(message = "请选择发帖限制")
    private Integer limitState = 1;

    // 发帖后台审核 1是 2否
    @NotNull(message = "请选择发帖审核")
    private Integer examineState = 1;

    // 评论审核 1是 2否'
    @NotNull(message = "请选择评论审核")
    private Integer commentState = 1;

    // 是否允许评论 1是 2否'
    @NotNull(message = "请选择是否允许评论")
    private Integer admitsState = 1;

    // 顺序
    private String sort;

    // 默认排序 1回复数 2浏览数 3最新发表 4最后回复'
    @NotNull(message = "请选择默认排序")
    private Integer defaultSort = 1;

    private Integer stickCount;
}
