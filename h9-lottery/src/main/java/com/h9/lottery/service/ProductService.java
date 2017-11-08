package com.h9.lottery.service;

import com.h9.common.base.Result;
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


    public Result<AuthenticityVO> getAuthenticity(Long userId, LotteryDto lotteryVo, HttpServletRequest request){
        UserRecord userRecord = newUserRecord(userId, lotteryVo, request);

        return Result.success();
    }


    @SuppressWarnings("Duplicates")
    public UserRecord newUserRecord(Long userId, LotteryDto lotteryVo, HttpServletRequest request) {
        UserRecord userRecord = new UserRecord();
        String refer = request.getHeader("Referer");
        String userAgent = request.getHeader("User-Agent");
        userRecord.setUserId(userId);
        userRecord.setUserAgent(userAgent);
        userRecord.setRefer(refer);
        String ip = NetworkUtil.getIpAddress(request);
        userRecord.setIp(ip);
        String client = request.getHeader("client");
        if (StringUtils.isNotEmpty(client)) {
            userRecord.setClient(Integer.parseInt(client));
        }
        String version = request.getHeader("version");
        userRecord.setVersion(version);
        userRecord.setLatitude(lotteryVo.getLatitude());
        userRecord.setLongitude(lotteryVo.getLongitude());
        String imei = request.getHeader("imei");
        userRecord.setImei(imei);
        return userRecordRepository.saveAndFlush(userRecord);
    }

}
