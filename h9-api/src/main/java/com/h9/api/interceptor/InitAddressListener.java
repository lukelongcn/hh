package com.h9.api.interceptor;

import com.h9.api.model.dto.Areas;
import com.h9.api.service.AddressService;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.Address;
import com.h9.common.db.entity.GlobalProperty;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.Resource;

/**
 * Created by 李圆 on 2017/12/4
 */
@Component
public class InitAddressListener implements ApplicationListener<ApplicationReadyEvent> {

    @Resource
    AddressService addressService;
    @Resource
    RedisBean redisBean;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        this.initAddressCache();
    }

    private void initAddressCache() {
      List<Areas> areasList = addressService.findFromDb();
        redisBean.setObject(RedisKey.addressKey,areasList);
    }
}
