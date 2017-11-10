package com.h9.common.common;

import com.alibaba.fastjson.JSONObject;
import com.h9.common.base.Result;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.BalanceFlow;
import com.h9.common.db.entity.GlobalProperty;
import com.h9.common.db.entity.UserAccount;
import com.h9.common.db.entity.UserRecord;
import com.h9.common.db.repo.*;
import com.h9.common.utils.NetworkUtil;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * CommonService:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/6
 * Time: 14:50
 */
@Component
public class CommonService {
    
    Logger logger = Logger.getLogger(CommonService.class);

    @Resource
    private UserAccountRepository userAccountRepository;
    @Resource
    private BalanceFlowRepository balanceFlowRepository;
    @Resource
    private UserRecordRepository userRecordRepository;


    @Transactional
    public Result setBalance(Long userId, BigDecimal money, Long typeId,Long orderId,String orderNo,String remarks){
        UserAccount userAccount = userAccountRepository.findByUserIdLock(userId);
        BigDecimal balance = userAccount.getBalance();
        BigDecimal newbalance = balance.add(money);
        if(newbalance.compareTo(new BigDecimal(0))<0){
            return Result.fail("余额不足");
        }
        userAccount.setBalance(newbalance);
        BalanceFlow balanceFlow = new BalanceFlow();
        balanceFlow.setBalance(newbalance);
        balanceFlow.setMoney(money);
        balanceFlow.setFlowType(typeId);
        balanceFlow.setOrderId(orderId);
        balanceFlow.setOrderNo(orderNo);
        balanceFlow.setRemarks(remarks);
        balanceFlow.setUserId(userId);

        userAccountRepository.save(userAccount);
        balanceFlowRepository.save(balanceFlow);
        return Result.success();
    }

    /****
     * 通过经纬都获取地址信息
     * @param lat
     * @param lon
     * @return
     */
    public String getAdressUrl(double lat, double lon) {
        return MessageFormat.format("http://api.map.baidu.com/geocoder/v2/?location={0},{1}&output=json&ak=vnCSOl4W7bgaIQH3GtNVTdFXRcU8hcCD", lat, lon);
    }

    public String getAddress(double lat, double lon) {
        try{
            RestTemplate restTemplate = new RestTemplate();
            String forObject = restTemplate.getForObject(getAdressUrl(lat, lon), String.class);
            System.out.println("------" + forObject + "------");
            JSONObject jsonObject = JSONObject.parseObject(forObject);
            if (jsonObject.getInteger("status") == 0) {
                JSONObject result = jsonObject.getJSONObject("result");
                return result.getString("formatted_address");
            } else {
                return org.apache.commons.lang3.StringUtils.EMPTY;
            }

        }catch (Exception ex){
            logger.debugv(ex.getMessage(),ex);
        }
        return org.apache.commons.lang3.StringUtils.EMPTY;
    }

    @Transactional
    public UserRecord newUserRecord(Long userId, double latitude, double longitude,HttpServletRequest request) {
        UserRecord userRecord = new UserRecord();
        userRecord.setUserId(userId);
        userRecord.setLatitude(latitude);
        userRecord.setLongitude(longitude);
        try{
            String refer = request.getHeader("Referer");
            String userAgent = request.getHeader("User-Agent");
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
            if(latitude!=0&&longitude!=0){
                String address = getAddress(latitude, longitude);
                userRecord.setAddress(address);
            }
            String imei = request.getHeader("imei");
            userRecord.setImei(imei);
        }catch (Exception ex){
            logger.debugv(ex.getMessage(),ex);
        }

        return userRecordRepository.saveAndFlush(userRecord);
    }



}
