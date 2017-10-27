package com.h9.api.service;

import com.h9.api.model.dto.UserLoginDTO;
import com.h9.api.provider.SMService;
import com.h9.common.base.Result;
import com.h9.common.db.bean.RedisBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * Created by itservice on 2017/10/26.
 */
@Service
@Transactional
public class UserService {
    @Resource
    private RedisBean redisBean;

    public Result loginFromPhone(UserLoginDTO userLoginDTO) {
        String phone = userLoginDTO.getPhone();
        String code = userLoginDTO.getCode();

        String redisCode = redisBean.getStringValue(String.format(SMService.smsCodeKey, phone));
        if (!code.equals(redisCode)) return Result.fail("验证码不在确");

        return null;
    }
}
