package com.h9.api.service.handler;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * PayHandlerFactory:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/12
 * Time: 11:15
 */
@Component
public class PayHandlerFactory {
    
    @Resource
    private RechargePayHandler rechargePayHandler;
    @Resource
    private HotelPayHandler hotelPayHandler;
    @Resource
    private StorePayHandler storePayHandler;

    private Logger logger = Logger.getLogger(this.getClass());
    public AbPayHandler getPayHandler(int type){
        logger.info("type : "+type);
        AbPayHandler abPayHandler;
        switch (type){
            case 0:{
                abPayHandler = rechargePayHandler;
                break;
            }
            case 1:{
                abPayHandler = hotelPayHandler;
                break;
            }
            case 2:
                abPayHandler = storePayHandler;
                break;
            default:{
                abPayHandler = rechargePayHandler;
            }
        }
        return abPayHandler;
    }


}

