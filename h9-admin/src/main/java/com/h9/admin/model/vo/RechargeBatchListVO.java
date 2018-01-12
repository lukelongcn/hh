package com.h9.admin.model.vo;

import com.h9.common.db.entity.RechargeBatch;
import com.h9.common.db.entity.RechargeBatchRecord;
import com.h9.common.utils.DateUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;

/**
 * Created by itservice on 2018/1/12.
 */
@Data
@Accessors(chain = true)
public class RechargeBatchListVO {

    private Long id;

    private String batchNo;

    private String phone;

    private String nickName;

    private String money;

    private String remarks;

    private String rechargeTime;

    /**
     * description: 1 为充值 0为未充值 3 为 查无此人
     */
    private Integer status = 0;

    private String statusDesc = "";

    public RechargeBatchListVO(){

    }

    public RechargeBatchListVO(RechargeBatchRecord rechargeBatchRecord){

        BeanUtils.copyProperties(rechargeBatchRecord, this);

        this.setBatchNo(rechargeBatchRecord.getBatchNo())
                .setRechargeTime(DateUtil.formatDate(rechargeBatchRecord.getCreateTime(), DateUtil.FormatType.SECOND));

        RechargeBatchRecord.RechargeStatusEnum statusEnum = RechargeBatchRecord.RechargeStatusEnum.findByCode(rechargeBatchRecord.getStatus());
        if (statusEnum != null) {
            this.setStatusDesc(statusEnum.getDesc());
            this.setStatus(statusEnum.getCode());
        }
    }

}
