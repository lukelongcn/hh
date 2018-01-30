package com.h9.api.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by itservice on 2018/1/25.
 */
@Data
@Accessors(chain = true)
public class VerifyTokenDTO {
    private String signature;
    private String timestamp;
    private String nonce;
    private String echostr;
}
