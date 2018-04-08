package com.h9.admin.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:增加优惠券</p>
 *
 * @author LiYuan
 * @Date 2018/4/8
 */
@Data
public class CouponsDTO {

    @NotBlank(message = "标题必填")
    @Size(max = 20)
    private String title;

    @NotBlank(message = "优惠券类型必填")
    private String couponType;

    @NotNull(message = "生效时间必填")
    private Date startTime;

    @NotNull(message = "失效时间必填")
    private Date endTime;

    @NotEmpty(message = "选择商品列表不能为空")
    private List<Long> goodIdList;

    @NotNull(message = "制券张数必填")
    private Integer askCount;

    @NotBlank(message = "发放方式必填")
    private String sentType;

}
