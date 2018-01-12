package com.h9.api.provider;

import com.alibaba.fastjson.JSONObject;
import com.h9.api.model.dto.OrderDTO;
import com.h9.api.model.dto.PayConfig;
import com.h9.api.model.vo.OrderVo;
import com.h9.common.base.Result;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * PayProvider:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/11
 * Time: 16:51
 */
@Service
public class PayProvider {

     Logger logger = Logger.getLogger(PayProvider.class);


    @Value("${pay.host}")
    private String payHost;
    
    @Resource
    private RestTemplate restTemplate;
    

    private String initUrl(){
        return payHost + "/h9/pay/config/regist";
    }

    private String initOrderURL(){
        return payHost + "/h9/pay/initOrder";
    }

    private String getPrepayURL(long orderId){
        return payHost + "/h9/pay/getPrepay?payOrderId="+orderId;
    }


    public String goPay(long payOrderId,long businessOrderId){
        return payHost + "/h9/pay/toPay?payOrderId="+payOrderId+"&businessAppId="+payConfig.getBusinessAppId()+"&businessOrderId="+businessOrderId;
    }


    @Resource
    private PayConfig payConfig;

    public void initConfig( ){
        logger.debugv("payConfig init:"+JSONObject.toJSONString(payConfig));
        logger.debugv(initUrl());
        Result result = restTemplate.postForObject(initUrl(), payConfig, Result.class);
        logger.debugv(JSONObject.toJSONString(result));
    }


    public Result<OrderVo> initOrder(OrderDTO orderDTO){
        try {
            logger.debugv(initOrderURL());
            orderDTO.setBusinessAppId(payConfig.getBusinessAppId());
            HttpEntity<OrderDTO> stringHttpEntity = getStringHttpEntity(orderDTO);
            ResponseEntity<Result> exchange = restTemplate.exchange(initOrderURL(), HttpMethod.POST, stringHttpEntity, Result.class);
            return exchange.getBody();
        } catch (RestClientException e) {
            logger.debug(e.getMessage(),e);
            e.printStackTrace();
        }
        return Result.fail("支付失败");
    }


    private static HttpEntity<OrderDTO> getStringHttpEntity(OrderDTO param) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        return new HttpEntity<OrderDTO>(param, headers);
    }










}
