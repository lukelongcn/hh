package com.h9.admin.service;

import com.h9.admin.model.vo.UserSignVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserAccount;
import com.h9.common.db.entity.user.UserSign;
import com.h9.common.db.repo.UserAccountRepository;
import com.h9.common.db.repo.UserRepository;
import com.h9.common.db.repo.UserSignRepository;

import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.Date;

import javax.annotation.Resource;

/**
 * Created by 李圆 on 2018/1/15
 */
@Service
public class SignService {
    @Resource
    private UserSignRepository userSignRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private UserAccountRepository userAccountRepository;
    /**
     * 签到记录
     */
    public Result getSign(Integer page, Integer limit) {
        PageResult<Long> pageResult = userSignRepository.findSignList(page, limit);
        if (pageResult == null){
            return Result.success("暂无签到记录");
        }
        return Result.success(pageResult.result2Result(this::convert));
    }

    private UserSignVO convert(Long id) {
        User user = userRepository.findOne(id);
        UserAccount userAccount = userAccountRepository.findByUserId(id);
        Date createTime = userSignRepository.findBySingleId(id);
        return new UserSignVO(user,userAccount.getCashBackMoney(),createTime);
    }

}
