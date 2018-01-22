package com.h9.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.h9.admin.model.dto.hotel.RefundDTO;
import com.h9.common.base.Result;
import org.apache.commons.io.IOUtils;
import org.hibernate.transform.ResultTransformer;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

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
    @Value("${wx.pay.mchid}")
    private String mchId;

    @Value("${wx.pay.appid}")
    private String appid;

    @Value("${wx.paykey}")
    private String payKey;

    @Value("${wx.pay.bid}")
    private String bid;

    @Resource
    private RestTemplate restTemplate;


    private static HttpEntity<Object> getStringHttpEntity(Object param) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        return new HttpEntity<Object>(param, headers);
    }


    public Result refundOrder(Long id, BigDecimal payMoney4Wechat) {

        InputStream is = this.getClass().getClassLoader().getResourceAsStream("apiclient_cert.p12");
        byte[] bytes = null;
        try {
//            InputStream is = new FileInputStream(new File("D:\\资料\\红包\\cert\\apiclient_cert.p12"));
            bytes = IOUtils.toByteArray(is);
        }catch (NullPointerException ne){
            return Result.fail("证书不存在");
        }catch (Exception e) {
            return Result.fail("读取证书失败");
        }
        RefundDTO refundDTO = new RefundDTO(payMoney4Wechat,mchId,appid,payKey,id+"",bid,bytes);

        try {
            String url = payHost + "/h9/pay/order/refund";
            HttpEntity<Object> stringHttpEntity = getStringHttpEntity(refundDTO);
            Result result = restTemplate.exchange(url, HttpMethod.POST, stringHttpEntity, Result.class).getBody();

            logger.info("result : " + JSONObject.toJSONString(result));
            return result;
        } catch (RestClientException e) {
            logger.info(e.getMessage(), e);
            return Result.fail("退款失败,请稍后再试");
        }
    }


}
