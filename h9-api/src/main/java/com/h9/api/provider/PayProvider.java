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
        HttpEntity<Object> stringHttpEntity = getStringHttpEntity(payConfig);
        ResponseEntity<Result> exchange = restTemplate.exchange(initUrl(), HttpMethod.POST, stringHttpEntity, Result.class);
        logger.debugv(JSONObject.toJSONString(exchange.getBody()));
    }


    public Result<OrderVo> initOrder(OrderDTO orderDTO){
        try {
            logger.debugv(initOrderURL());
            orderDTO.setBusinessAppId(payConfig.getBusinessAppId());
            HttpEntity<Object> stringHttpEntity = getStringHttpEntity(orderDTO);
            ResponseEntity<Result> exchange = restTemplate.exchange(initOrderURL(), HttpMethod.POST, stringHttpEntity, Result.class);
            Result result = exchange.getBody();
            logger.debugv(JSONObject.toJSONString(exchange.getBody()));
            if(!result.isSuccess()){
                return result;
            }
            Object data = result.getData();
            OrderVo orderVo = JSONObject.parseObject(JSONObject.toJSONString(data), OrderVo.class);
            return new Result<OrderVo>(result.getCode(),result.getMsg(),orderVo);
        } catch (RestClientException e) {
            logger.debug(e.getMessage(),e);
            e.printStackTrace();
        }
        return Result.fail("支付失败");
    }


    private static HttpEntity<Object> getStringHttpEntity(Object param) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        return new HttpEntity<Object>(param, headers);
    }










}
