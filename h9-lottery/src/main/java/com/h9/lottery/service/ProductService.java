package com.h9.lottery.service;

import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.db.entity.UserRecord;
import com.h9.common.db.repo.UserRecordRepository;
import com.h9.common.utils.NetworkUtil;
import com.h9.lottery.model.vo.AuthenticityVO;
import com.h9.lottery.model.vo.LotteryDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * ProductService:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/7
 * Time: 19:21
 */
@Component
public class ProductService {

    @Resource
    private UserRecordRepository userRecordRepository;
    @Resource
    private CommonService commonService;


    public Result<AuthenticityVO> getAuthenticity(Long userId, LotteryDto lotteryVo, HttpServletRequest request){
        UserRecord userRecord = commonService.newUserRecord(userId, lotteryVo.getLatitude(), lotteryVo.getLongitude(), request);
        AuthenticityVO authenticityVO = new AuthenticityVO();
        return Result.success(authenticityVO);
    }


}
