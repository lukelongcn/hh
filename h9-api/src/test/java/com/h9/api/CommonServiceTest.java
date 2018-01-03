package com.h9.api;

import com.alibaba.fastjson.JSONObject;
import com.h9.api.service.StickService;
import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.db.entity.order.Address;
import org.jboss.logging.Logger;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:地址测试
 * CommonServiceTest:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/2
 * Time: 15:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonServiceTest {
    @Resource
    private CommonService commonService;

    Logger logger = Logger.getLogger(CommonServiceTest.class);

//    @Test
    public void test2222() {
        CommonService.AddressResult addressDetail = commonService.getAddressDetail(22.5428600000, 114.0595600000);
        logger.debugv(JSONObject.toJSONString(addressDetail));

    }


    @Resource
    private StickService stickService;
    
    
    @Test
    public void testHome() {
        Result home = stickService.home();

    }
}
