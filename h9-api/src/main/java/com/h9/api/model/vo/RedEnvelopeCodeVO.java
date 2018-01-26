package com.h9.api.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by itservice on 2018/1/26.
 */
@Data
@Accessors(chain = true)
public class RedEnvelopeCodeVO {

    private String codeUrl;
    private String money;

    private List<Map<Object, Object>> transferRecord= new ArrayList();

}
