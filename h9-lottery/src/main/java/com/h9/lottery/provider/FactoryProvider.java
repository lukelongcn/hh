package com.h9.lottery.provider;

import com.alibaba.fastjson.JSONObject;
import com.h9.lottery.provider.model.LotteryModel;
import com.h9.lottery.provider.model.ProductModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * FactoryProvider:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/8
 * Time: 18:13
 */
@Component
public class FactoryProvider {
    @Resource
    private RestTemplate restTemplate;

    public LotteryModel findByLotteryModel(String code) {
        String result = restTemplate.getForObject("http://61.191.56.33:63753/GetCodeBouns.aspx?Code=" + code, String.class);
        if(StringUtils.isEmpty(result)){
            return null;
        }
        return JSONObject.parseObject(result, LotteryModel.class);
    }


    public LotteryModel updateLotteryStatus(String code) {
        String result =  restTemplate.getForObject("http://61.191.56.33:63753/UpdateCodeState.aspx?Code=" + code, String.class);
        if(StringUtils.isEmpty(result)){
            return null;
        }
        return JSONObject.parseObject(result, LotteryModel.class);
    }


    public ProductModel getProductInfo(String code) {
        String result =  restTemplate.getForObject("http://61.191.56.33:63753/QueryIsTrue.aspx?Code=" + code, String.class);
        if(StringUtils.isEmpty(result)){
            return null;
        }
        return JSONObject.parseObject(result, ProductModel.class);
    }







}
