package com.h9.admin.service;

import com.h9.admin.model.vo.LoginResultVO;
import com.h9.common.base.Result;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.User;
import com.h9.common.db.repo.UserRepository;
import com.h9.common.utils.HttpUtil;
import com.h9.common.utils.MD5Util;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: George
 * @date: 2017/10/31 14:08
 */
@Service
@Transactional
public class UserService {
    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisBean redisBean;


    public Result<LoginResultVO> login(String name, String password){
        String actualPassword = MD5Util.getMD5(password);
        this.logger.infov("name:{0},password:{1}",name,actualPassword);
        User user  = this.userRepository.findByPhoneAndPasswordAndIsAdmin(name,actualPassword,1);
        if(user == null){
            return Result.fail("用户不存在");
        }
        if(user.getStatus()!=1){
            return Result.fail("该用户已被禁用");
        }
        //生成token,并保存
        String token = UUID.randomUUID().toString();
        String tokenUserIdKey = RedisKey.getAdminTokenUserIdKey(token);
        redisBean.setStringValue(tokenUserIdKey, user.getId() + "", 2,TimeUnit.HOURS);
        HttpUtil.getHttpSession().setAttribute("user",this.userRepository.findByPhone(name));
        return new Result(0, "登录成功",new LoginResultVO(token,name));
    }

   /* public Result logout(){
        String tokenUserIdKey = RedisKey.getAdminTokenUserIdKey(token);
        redisBean.setStringValue(tokenUserIdKey, user.getId() + "", 2,TimeUnit.HOURS);
        return new Result(0, "登录成功",new LoginResultVO(token,name));
    }*/
}
