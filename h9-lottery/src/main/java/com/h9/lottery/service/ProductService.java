package com.h9.lottery.service;

import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.lottery.Product;
import com.h9.common.db.entity.lottery.ProductFlow;
import com.h9.common.db.entity.lottery.ProductLog;
import com.h9.common.db.entity.lottery.ProductType;
import com.h9.common.db.entity.user.UserRecord;
import com.h9.common.db.repo.*;
import com.h9.common.utils.DateUtil;
import com.h9.lottery.config.LotteryConfig;
import com.h9.lottery.model.vo.AuthenticityVO;
import com.h9.lottery.model.vo.LotteryDto;
import com.h9.lottery.provider.FactoryProvider;
import com.h9.lottery.provider.model.ProductModel;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * ProductService:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/7
 * Time: 19:21
 */
@Component
public class ProductService {

    private Logger logger = Logger.getLogger(ProductService.class);

    @Resource
    private CommonService commonService;
    @Resource
    private ProductRepository productRepository;
    @Resource
    private ProductFlowRepository productFlowRepository;
    @Resource
    private ProductLogRepository productLogRepository;
    @Resource
    private FactoryProvider factoryProvider;
    @Resource
    private LotteryService lotteryService;
    @Resource
    private LotteryConfig lotteryConfig;

    @Transactional
    public Result<AuthenticityVO> getAuthenticity(String token, LotteryDto lotteryVo, HttpServletRequest request) {
        Long userId = token2userId(token);

        UserRecord userRecord = commonService.newUserRecord(userId, lotteryVo.getLatitude(), lotteryVo.getLongitude(), request);
        String code = lotteryVo.getCode();

        //       黑名单 改成配置的
        String imei = request.getHeader("imei");

        if(StringUtils.isEmpty(imei)){
            return Result.fail("服务器繁忙，请稍后刷新使用");
        }
//        if (lotteryService.onBlackUser(userId, imei)) {
//            return Result.fail("异常操作，限制访问！如有疑问，请联系客服。");
//        }

//        if (!lotteryService.onWhiteUser(userId)) {
//
//            if (lotteryService.onBlackUser(userId, imei)) {
//                return Result.fail("异常操作，限制访问！如有疑问，请联系客服。");
//            }
//        }


        int date = lotteryConfig.getIntervalTime();
        Date startDate = DateUtil.getDate(new Date(), date, Calendar.SECOND);
        long errCount = productLogRepository.findByUserId(imei, startDate);
        logger.debugv("-------errCount:"+errCount);
//        当前的条码是否是未扫过码
        long userCode = productLogRepository.findByUserId(imei, code);
        if (errCount > lotteryConfig.getErrorTimes() && userCode <= 0) {
            return Result.fail("您的错误次数已经到达上限，请稍后再试");
        }

        //记录扫描记录
        ProductLog productLog = recordLog(userId,imei, lotteryVo, userRecord);

        Result result = findByCode(code);
        if (result != null) return result;

        Product product4Update = productRepository.findByCode4Update(code);
        BigDecimal count = product4Update.getCount();

        if (count.compareTo(new BigDecimal(0)) == 0) {
            product4Update.setFisrtTime(new Date());
            product4Update.setFisrtAddress(userRecord.getAddress());
        }
        product4Update.setCount(count.add(new BigDecimal(1)));

        Date fisrtTime = product4Update.getFisrtTime();
        product4Update.setLastTime(new Date());
        productLog.setProduct(product4Update);
        product4Update.setAddress(userRecord.getAddress());

        productLogRepository.save(productLog);
        ProductFlow productFlow = new ProductFlow();
        BeanUtils.copyProperties(productLog, productFlow, "id");
        productFlowRepository.save(productFlow);
        productRepository.save(product4Update);

        AuthenticityVO authenticityVO = new AuthenticityVO();
        ProductType productType = product4Update.getProductType();
        if (productType != null) {
            authenticityVO.setProductName(productType.getName());
        }
        authenticityVO.setSupplierName(product4Update.getSupplierName());
        authenticityVO.setSupplierDistrict(product4Update.getSupplierDistrict());
        authenticityVO.setLastQueryTime(DateUtil.formatDate(fisrtTime, DateUtil.FormatType.GBK_SECOND));
        authenticityVO.setLastQueryAddress(product4Update.getFisrtAddress());
        authenticityVO.setQueryCount(product4Update.getCount());
        return Result.success(authenticityVO);
    }

    public ProductLog recordLog(Long userId, String imei,LotteryDto lotteryVo, UserRecord userRecord) {
        ProductLog productLog = new ProductLog();
        productLog.setCode(lotteryVo.getCode());
        productLog.setUserId(userId);
        productLog.setImei(imei);
        productLog.setUserRecord(userRecord);
        return productLogRepository.saveAndFlush(productLog);
    }

    public Result findByCode(String code) {
        Product product = productRepository.findByCode(code);
        if (product != null) {
            return null;
        }
        long start = System.currentTimeMillis();
        ProductModel productInfo = factoryProvider.getProductInfo(code);
        long end = System.currentTimeMillis();
        logger.debugv(""+((end-start)/1000l));
        if (productInfo == null) {
            return Result.fail("服务器繁忙，请稍后再试");
        }
        int state = productInfo.getState();
        if (state == 2) {
            return Result.fail("您所查询的防伪码不存在，谨防假冒！或与公司客服人员联系！");
        } else if (state == 4) {
            return Result.fail("服务器繁忙，请稍后再试");
        } else {
            product = productInfo.covert();
            ProductType productType = productTypeRepository.findOrNew(productInfo.getName());
            product.setProductType(productType);
            productRepository.saveAndFlush(product);
            return null;
        }

    }
    @Resource
    private ProductTypeRepository productTypeRepository;
    @Resource
    private RedisBean redisBean;

    public Long token2userId(String token){
        String userIdByPhone = redisBean.getStringValue(RedisKey.getWeChatUserId(token));
        if(!StringUtils.isEmpty(userIdByPhone)){
            return Long.parseLong(userIdByPhone);
        }

        String userIdByWechat = redisBean.getStringValue(RedisKey.getWeChatUserId(token));
        if(!StringUtils.isEmpty(userIdByWechat)){
            return Long.parseLong(userIdByWechat);
        }

        return null;
    }


}
