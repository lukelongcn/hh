package com.h9.lottery.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.h9.lottery.provider.model.LotteryModel;
import com.h9.lottery.provider.model.ProductModel;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * FactoryProvider:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/8
 * Time: 18:13
 */
@Component
public class FactoryProvider {

    Logger logger = Logger.getLogger(FactoryProvider.class);

    @Value("${h9.current.envir}")
    private String env;

    @Resource
    private RestTemplate restTemplate;

    public LotteryModel findByLotteryModel(String code) {

        try {
            LotteryModel lotteryModel = restTemplate.getForObject("http://61.191.56.33:63753/GetCodeBouns.aspx?Code=" + code, LotteryModel.class);
            logger.debugv(code+":find "  +JSONObject.toJSONString(lotteryModel));
            return lotteryModel;
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return null;
    }


    public LotteryModel updateLotteryStatus(String code) {
        // 非线上环境不改变工厂状态
        if(env.equals("product")){
            return null;
        }
        try {
            LotteryModel lotteryModel = restTemplate.getForObject("http://61.191.56.33:63753/UpdateCodeState.aspx?Code=" + code, LotteryModel.class);
            logger.debugv(code+":update "+JSONObject.toJSONString(lotteryModel));
            return lotteryModel;
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return null;
    }


    public ProductModel getProductInfo(String code) {
        try {
            ProductModel productModel = restTemplate.getForObject("http://61.191.56.33:63753/QueryIsTrue.aspx?Code=" + code, ProductModel.class);
            logger.debugv(code+":product "+JSONObject.toJSONString(productModel));
            return productModel;
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return null;

    }







}
