package com.h9.admin.service;

import com.h9.admin.model.vo.LoginResultVO;
import com.h9.common.base.Result;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.Address;
import com.h9.common.db.entity.User;
import com.h9.common.db.entity.UserBank;
import com.h9.common.db.entity.UserExtends;
import com.h9.common.db.repo.AddressRepository;
import com.h9.common.db.repo.UserBankRepository;
import com.h9.common.db.repo.UserExtendsRepository;
import com.h9.common.db.repo.UserRepository;
import com.h9.common.modle.vo.admin.finance.*;
import com.h9.common.utils.HttpUtil;
import com.h9.common.utils.MD5Util;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
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

    @Resource
    private UserRepository userRepository;
    @Resource
    private UserExtendsRepository userExtendsRepository;
    @Resource
    private UserBankRepository userBankRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private RedisBean redisBean;


    public Result<LoginResultVO> login(String name, String password){
        String actualPassword = MD5Util.getMD5(password);
        this.logger.infov("name:{0},password:{1}",name,actualPassword);
        User user  = this.userRepository.findByPhoneAndIsAdmin(name, User.IsAdminEnum.ADMIN.getId());
        if(user == null){
            return Result.fail("用户不存在");
        }
        if(!actualPassword.equals(user.getPassword())){
            return Result.fail("密码错误");
        }
        if(user.getStatus()!= User.StatusEnum.ENABLED.getId()){
            return Result.fail("该用户已被禁用");
        }
        //生成token,并保存
        String token = UUID.randomUUID().toString();
        String tokenUserIdKey = RedisKey.getAdminTokenUserIdKey(token);
        redisBean.setStringValue(tokenUserIdKey, user.getId() + "", 30,TimeUnit.MINUTES);
        //HttpUtil.setHttpSessionAttr("curUserId",user.getId());
        user.setLastLoginTime(new Date());
        this.userRepository.save(user);
        return new Result(0, "登录成功",new LoginResultVO(token,name));
    }

    public Result logout(String token){
        redisBean.expire(RedisKey.getAdminTokenUserIdKey(token),100,TimeUnit.MICROSECONDS);
        return new Result(0, "成功退出登录");
    }

    public Result<UserVO> getUserInfo(long userId) {
        UserVO userVO = new UserVO();
        User user = this.userRepository.findOne(userId);
        userVO.setUserInfoVO(user==null?null:new UserInfoVO(user));
        UserExtends userExtends = this.userExtendsRepository.findByUserId(userId);
        userVO.setUserExtendsInfoVO(userExtends==null?null:new UserExtendsInfoVO(userExtends));
        List<UserBank> userBankList = this.userBankRepository.findByUserId(userId);
        userVO.setUserBankInfoVOList(UserBankInfoVO.toUserBankVO(userBankList));
        List<Address> addressList = this.addressRepository.findByUserId(userId);
        userVO.setUserAddressInfoVOList(UserAddressInfoVO.toUserAddressInfoVO(addressList));
        return Result.success(userVO);
    }
}
