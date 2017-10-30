package com.h9.admin.service;

import com.h9.admin.model.dto.GlobalPropertyDTO;
import com.h9.common.base.Result;
import com.h9.common.db.bean.RedisBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: George
 * @date: 2017/10/30 15:22
 */
@Service
@Transactional
public class ConfigService {
    @Autowired
    private RedisBean redisBean;

    public Result updateGlobalProperty(GlobalPropertyDTO globalPropertyDTO){
        this.redisBean.setStringValue(globalPropertyDTO.getName(), globalPropertyDTO.getVal());
        return Result.success();
    }

    public Result updateGlobalProperty(String name, String val){
        this.redisBean.setStringValue(name, val);
        return Result.success();
    }


}
