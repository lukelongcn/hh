package com.h9.api.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * Created by itservice on 2018/2/5.
 */
@Data
@Accessors(chain = true)
public class ScanDTO {

    @NotNull(message = "请传入tempId")
    private String tempId;
}
