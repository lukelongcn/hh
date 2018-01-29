package com.h9.api.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by itservice on 2018/1/25.
 */
@Data
@Accessors(chain = true)
public class TransferDTO {
    @NotNull(message = "请输入要转入的账户")
    private String targetUserPhone;
    @NotNull(message = "请填写金额")
    private BigDecimal transferMoney;
    private String remarks;
}
