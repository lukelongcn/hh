package com.h9.admin.model.dto.finance;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import java.util.Set;

/**
 * @author: George
 * @date: 2017/11/10 20:03
 */
public class TransferLotteryFLowDTO {
    @ApiModelProperty(value = "ids",required = true)
    @NotEmpty(message = "ids不能为空")
    private Set<Long> ids;

    public Set<Long> getIds() {
        return ids;
    }

    public void setIds(Set<Long> ids) {
        this.ids = ids;
    }
}
