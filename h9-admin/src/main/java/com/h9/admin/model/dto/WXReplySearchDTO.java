package com.h9.admin.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.apiguardian.api.API;
import org.springframework.data.domain.Page;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:</p>
 *
 * @author LiYuan
 * @Date 2018/3/15
 */
@Data
@ApiModel(description = "微信自动回复搜索条件")
public class WXReplySearchDTO {
    private Integer page = 1;
    private Integer limit = 10;
    private String orderName;
    private String contentType;
    private Integer status = 0;
}
