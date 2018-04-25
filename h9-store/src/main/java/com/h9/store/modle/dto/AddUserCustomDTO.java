package com.h9.store.modle.dto;

import com.h9.common.common.ServiceException;
import lombok.Data;
import org.springframework.web.bind.MissingServletRequestParameterException;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Ln on 2018/4/25.
 */

@Data
public class AddUserCustomDTO {

    private List<String> images;

    private List<String> texts;


    private Long id;


    private Integer type;


    public void setId(Long id) throws MissingServletRequestParameterException {
        if (id == null) {
            throw new ServiceException("id不能为空");
        }
        this.id = id;
    }

    public void setType(Integer type) {
        if (type >= 1 && type <= 2) {
            this.type = type;
        }else{
            throw new ServiceException("type 在 1 和 2 之间");

        }
    }
}
