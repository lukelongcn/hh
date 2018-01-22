package com.h9.api.model.dto;

import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Created by 李圆 on 2018/1/22
 */
@Data
public class LikeDTO {
    @NotNull(message = "id不能为空")
    long id;
    @NotNull(message = "点赞类型不能为空")
    Integer type;
}
