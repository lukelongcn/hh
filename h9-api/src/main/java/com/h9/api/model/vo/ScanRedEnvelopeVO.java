package com.h9.api.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by itservice on 2018/1/31.
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ScanRedEnvelopeVO {
    private String money;
    private String img;
    private String nickName;
}
