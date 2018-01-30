package com.h9.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * Created by itservice on 2018/1/26.
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class RedEnvelopeDTO {

    private String codeUrl;
    private BigDecimal money;
    /**
     * description: 码的所有者（展示码的用户）
     */
    private Long userId;
    /**
     * description: 1 没有人使用，可用状态, 0 ，有人扫过了，不可用状态
     */
    private int status;

    private String tempId;

    /**
     * description: 发起方的OpenId
     */
    private String openId;

}
