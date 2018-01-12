package com.h9.admin.model.vo;

import com.h9.common.db.entity.RechargeBatch;
import com.h9.common.utils.DateUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;

/**
 * Created by itservice on 2018/1/11.
 */
@Data
@Accessors(chain = true)
public class RechargeBatchList {

    private Long id;

    private String batchNo;

    private String rechargeCountRate;

    private String rechargeMoneyRate;

    private String remark;

    private String createTime;

    private String fileName;

    private String filePath;

    public RechargeBatchList(){
    }

    public RechargeBatchList(RechargeBatch rechargeBatch,String rechargeCount,String rechargeMoney) {
        BeanUtils.copyProperties(rechargeBatch, this);

        this.setRechargeMoneyRate(rechargeCount);
        this.setRechargeCountRate(rechargeMoney);
        this.setCreateTime(DateUtil.formatDate(rechargeBatch.getCreateTime(), DateUtil.FormatType.SECOND));
    }
}
