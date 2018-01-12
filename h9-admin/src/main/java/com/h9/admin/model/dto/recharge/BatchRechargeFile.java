package com.h9.admin.model.dto.recharge;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by itservice on 2018/1/11.
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class BatchRechargeFile {

    private String fileName;

    private String filePath;

}
