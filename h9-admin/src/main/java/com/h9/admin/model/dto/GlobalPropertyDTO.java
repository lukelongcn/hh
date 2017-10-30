package com.h9.admin.model.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

/**
 * @author: George
 * @date: 2017/10/30 16:18
 */
public class GlobalPropertyDTO {
    @NotEmpty(message = "键不能为空")
    private String name;

    @NotNull(message = "值不能为空")
    private String val;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
