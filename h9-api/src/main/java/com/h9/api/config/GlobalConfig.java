package com.h9.api.config;

import com.h9.common.common.ConfigService;
import com.h9.common.constant.ParamConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * GloabeConfig:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/26
 * Time: 10:59
 */
@Component
public class GlobalConfig {

    @Resource
    private ConfigService configService;

    public boolean getCanWithdrawConfig(){

        String stringConfig = configService.getStringConfig(ParamConstant.CAN_Withdraw);
        if(StringUtils.isEmpty(stringConfig)){
            return false;
        }else{
            if("0".equals(stringConfig)||"false".equals(stringConfig)){
                return false;
            }else{
                return true;
            }


        }

    }


}
