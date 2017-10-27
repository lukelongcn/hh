package com.h9.api.service;

import com.h9.api.model.dto.UserLoginDTO;
import com.h9.api.provider.SMService;
import com.h9.common.base.Result;
import com.h9.common.db.bean.RedisBean;
import com.wedo.server.api.sdk.rest.model.AuthUser;
import com.wedo.server.api.sdk.rest.model.PostResult;
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

        PostResult<AuthUser> postResult = com.wedo.server.api.sdk.Service.getInstance().loginFromNoPassword(phone);
        if (postResult == null || postResult.getStatusCode() == 0) {
            return Result.fail("登录异常,请稍后再试");
        }
        return Result.success(postResult);
    }
}
