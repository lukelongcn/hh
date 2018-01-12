package com.h9.api.service.handler;

import com.h9.api.model.dto.PayNotifyVO;
import com.h9.common.db.entity.PayInfo;
import com.h9.common.db.entity.RechargeOrder;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * RechargePayHandler:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/11
 * Time: 20:41
 */
@Component
public class RechargePayHandler extends AbPayHandler {


    @Override
    public boolean callback(PayNotifyVO payNotifyVO, PayInfo payInfo) {
        return false;
    }
}
