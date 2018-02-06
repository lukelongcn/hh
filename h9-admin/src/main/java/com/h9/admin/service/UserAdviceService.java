package com.h9.admin.service;

import com.h9.admin.model.vo.UserAdviceVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.ConfigService;
import com.h9.common.constant.ParamConstant;
import com.h9.common.db.entity.order.Address;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserAdvice;
import com.h9.common.db.repo.AdviceRespository;

import net.bytebuddy.asm.Advice;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 李圆 on 2018/1/4
 */
@Service
public class UserAdviceService {

    @Resource
    AdviceRespository adviceRespository;

    @Transactional
    public Result getUserAdvice(Integer page, Integer limit, Map<String,String> mapConfig) {
        PageResult<UserAdvice> pageResult = adviceRespository.findAdviceList(page, limit);
        if (pageResult == null){
            return Result.success("暂无意见反馈");
        }
        return Result.success(pageResult.result2Result(userAdvice ->new UserAdviceVO(userAdvice,mapConfig)));
    }


}
