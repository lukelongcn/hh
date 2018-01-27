package com.h9.api.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.cache.annotation.CacheEvict;

/**
 * Created by itservice on 2018/1/26.
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class TransferResultVO {
    private String tips;
    /**
     * description: 1 成功 0 失败
     */
    private Integer transferStatus;

}
