package com.h9.admin.model.dto.topic;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;
import java.util.Map;

/**
 * Created by Ln on 2018/4/8.
 */
@Data
public class EditGoodsTopicDTO {

    private Long topicId;
    private String img;
    private Map<Long, Integer> ids;
    private Integer sort = 1;
}
