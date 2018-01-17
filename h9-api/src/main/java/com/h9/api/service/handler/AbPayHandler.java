package com.h9.api.service.handler;

import com.h9.api.model.dto.PayNotifyVO;
import com.h9.common.db.entity.PayInfo;
import com.h9.common.db.entity.RechargeOrder;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * AbPayHandler:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/11
 * Time: 20:40
 */
public abstract class AbPayHandler {


    public abstract boolean callback(PayNotifyVO payNotifyVO, PayInfo payInfo);

}
