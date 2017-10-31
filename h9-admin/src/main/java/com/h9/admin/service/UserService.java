package com.h9.admin.service;

import com.h9.admin.model.dto.SystemUserDTO;
import com.h9.admin.repository.UserRepository;
import com.h9.common.base.Result;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: George
 * @date: 2017/10/31 14:08
 */
@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisBean redisBean;

    public Result login(String name,String password){
        User user  = this.userRepository.findByNameAndPasswordAndIsAdmin(name,password,1);
        if(user == null){
            return Result.fail("用户不存在");
        }
        if(user.getStatus()!=1){
            return Result.fail("该用户已被禁用");
        }
        //生成token
        String token = UUID.randomUUID().toString();
        String tokenUserIdKey = RedisKey.getAdminTokenUserIdKey(token);
        redisBean.setStringValue(tokenUserIdKey, user.getId() + "", 2,TimeUnit.HOURS);

        return new Result(0, "登录成功");
    }
}
