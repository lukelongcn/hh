package com.h9.api.service;

import com.h9.api.model.dto.AdviceDTO;
import com.h9.common.base.Result;
import com.h9.common.common.ConfigService;
import com.h9.common.constant.ParamConstant;
import com.h9.common.db.entity.user.UserAdvice;
import com.h9.common.db.repo.AdviceRespository;

import com.h9.common.modle.vo.Config;
import com.h9.common.utils.NetworkUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
        List<Config> mapListConfig = configService.getMapListConfig(ParamConstant.ADVICE_TYPE);
        if(CollectionUtils.isEmpty(mapListConfig)){
            mapListConfig = new ArrayList<>();
        }
        return Result.success(mapListConfig);
    }

    /**
     * 提交意见反馈
     * @param userId
     * @param adviceDTO
     * @param request
     * @return
     */
    public Result sendAdvice(long userId, AdviceDTO adviceDTO, HttpServletRequest request) {
        if (adviceDTO == null){
            return Result.fail("对象不存在");
        }
        UserAdvice userAdvice = new UserAdvice();
        userAdvice.setAdvice(adviceDTO.getAdvice());
        userAdvice.setAnonymous(adviceDTO.getAnonymous());
        userAdvice.setConnect(adviceDTO.getConnect());
        userAdvice.setUserId(userId);
        userAdvice.setAdviceImg(adviceDTO.getAdviceImgList());
        userAdvice.setIp(NetworkUtil.getIpAddress(request));
        userAdvice.setAdviceType(adviceDTO.getAdviceType());

        adviceRespository.save(userAdvice);
        return Result.success("意见反馈成功");
    }


}
