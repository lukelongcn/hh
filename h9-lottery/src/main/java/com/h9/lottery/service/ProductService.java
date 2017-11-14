package com.h9.lottery.service;

import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.db.entity.Product;
import com.h9.common.db.entity.ProductFlow;
import com.h9.common.db.entity.ProductLog;
import com.h9.common.db.entity.UserRecord;
import com.h9.common.db.repo.ProductFlowRepository;
import com.h9.common.db.repo.ProductLogRepository;
import com.h9.common.db.repo.ProductRepository;
import com.h9.common.db.repo.UserRecordRepository;
import com.h9.common.utils.DateUtil;
import com.h9.common.utils.NetworkUtil;
import com.h9.lottery.model.vo.AuthenticityVO;
import com.h9.lottery.model.vo.LotteryDto;
import com.h9.lottery.provider.FactoryProvider;
import com.h9.lottery.provider.model.ProductModel;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.apache.bcel.classfile.Code;
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


    @Transactional
    public Result<AuthenticityVO> getAuthenticity(Long userId, LotteryDto lotteryVo, HttpServletRequest request) {
        UserRecord userRecord = commonService.newUserRecord(userId, lotteryVo.getLatitude(), lotteryVo.getLongitude(), request);
        ProductLog productLog = recordLog(userId, lotteryVo, userRecord);
        String code = lotteryVo.getCode();
        Result result = findByCode(code);
        if (result != null) return result;

//      TODO  黑名单 改成配置的
        String imei = request.getHeader("imei");
        if (lotteryService.onBlackUser(userId, imei)) {

            return Result.fail("系统繁忙，请稍后再试");
        }
        int date = -60 * 1;
        Date startDate = DateUtil.getDate(new Date(), date, Calendar.SECOND);
        long errCount = productLogRepository.findByUserId(userId, startDate);
        long userCode = productLogRepository.findByUserId(userId, code);
        if (errCount > 3 && userCode <= 0) {
            return Result.fail("您的错误次数已经到达上限，请稍后再试");
        }
        Product product4Update = productRepository.findByCode4Update(code);
        BigDecimal count = product4Update.getCount();

        if (count.compareTo(new BigDecimal(0)) == 0) {
            product4Update.setFisrtTime(new Date());
        }
        product4Update.setCount(count.add(new BigDecimal(1)));

        Date lastTime = product4Update.getLastTime();
        product4Update.setLastTime(new Date());
        productLog.setProduct(product4Update);
        String address = product4Update.getAddress();
        product4Update.setAddress(userRecord.getAddress());


        productLogRepository.save(productLog);
        ProductFlow productFlow = new ProductFlow();
        BeanUtils.copyProperties(productLog, productFlow, "id");
        productFlowRepository.save(productFlow);
        productRepository.save(product4Update);

        AuthenticityVO authenticityVO = new AuthenticityVO();
        authenticityVO.setProductName(product4Update.getName());
        authenticityVO.setSupplierName(product4Update.getSupplierName());
        authenticityVO.setSupplierDistrict(product4Update.getSupplierDistrict());
        authenticityVO.setLastQueryTime(DateUtil.formatDate(lastTime, DateUtil.FormatType.GBK_SECOND));
        authenticityVO.setLastQueryAddress(address);
        authenticityVO.setQueryCount(count);
        return Result.success(authenticityVO);
    }

    public ProductLog recordLog(Long userId, LotteryDto lotteryVo, UserRecord userRecord) {
        ProductLog productLog = new ProductLog();
        productLog.setCode(lotteryVo.getCode());
        productLog.setUserId(userId);
        productLog.setUserRecord(userRecord);
        return productLogRepository.saveAndFlush(productLog);
    }

    public Result findByCode(String code) {
        Product product = productRepository.findByCode(code);
        if (product != null) {
            return null;
        }
        ProductModel productInfo = factoryProvider.getProductInfo(code);
        if (productInfo == null) {
            return Result.fail("服务器繁忙，请稍后再试");
        }
        int state = productInfo.getState();
        if (state == 2) {
            return Result.fail("未能查询到该商品信息");
        } else if (state == 4) {
            return Result.fail("服务器繁忙，请稍后再试");
        } else {
            product = productInfo.covert();
            productRepository.saveAndFlush(product);
            return null;
        }

    }


}
