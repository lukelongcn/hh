package com.h9.api.service;

import com.h9.api.model.dto.AdviceDTO;
import com.h9.common.base.Result;
import com.h9.common.common.ConfigService;
import com.h9.common.constant.ParamConstant;
import com.h9.common.db.entity.user.UserAdvice;
import com.h9.common.db.repo.AdviceRespository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

/**
 * Created by 李圆 on 2018/1/3
 */
@Service
public class AdviceService {
    @Resource
    AdviceRespository adviceRespository;
    @Resource
    ConfigService configService;

    /**
     * 获取意见类别
     */
    public Result getAdviceType(){
        String adviceType = configService.getStringConfig(ParamConstant.ADVICE_TYPE);
        if (adviceType == null && adviceType == ""){
            return Result.fail("获取意见反馈类别失败，请检查全局配置");
        }
        return Result.success(adviceType);
    }

    /**
     * 提交意见反馈
     * @param userId
     * @param adviceDTO
     * @return
     */
    public Result sendAdvice(long userId, AdviceDTO adviceDTO) {
        if (adviceDTO == null){
            return Result.fail("对象不存在");
        }
        UserAdvice userAdvice = new UserAdvice();
        userAdvice.setAdvice(adviceDTO.getAdvice());
        userAdvice.setAnonymous(adviceDTO.getAnonymous());
        userAdvice.setConnect(adviceDTO.getConnect());
        userAdvice.setUserId(userId);

        List list = new ArrayList();
        list.add(adviceDTO.getAdviceImg1());
        list.add(adviceDTO.getAdviceImg2());
        list.add(adviceDTO.getAdviceImg3());
        userAdvice.setAdviceImg(list);

        adviceRespository.save(userAdvice);
        return Result.success("意见反馈成功");
    }
}
