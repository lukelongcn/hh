package com.h9.admin.model.dto.stick;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Created by 李圆 on 2018/1/15
 */
@Data
public class StickDTO {
    @NotNull(message = "用户id不能为空")
    private long userId;
    @NotBlank(message = "请填写标题")
    @Length(min = 0,max = 64,message = "标题填写过长")
    private String title;
    @NotBlank(message = "请填写内容")
    @Length(min = 0,max = 1000,message = "内容过长")
    private String content;
    @NotNull(message = "请选择分类")
    private Long typeId;
}
