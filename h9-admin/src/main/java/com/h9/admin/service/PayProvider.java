package com.h9.admin.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.h9.admin.model.dto.hotel.RefundDTO;
import com.h9.admin.model.dto.order.PayOrderDTO;
import com.h9.common.base.Result;
import org.apache.commons.io.IOUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * <p>
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

    @Value("${h9.current.envir}")
    private String env;


    @Resource
    private RestTemplate restTemplate;
    @Resource
    private Environment environment;


    private static HttpEntity<Object> getStringHttpEntity(Object param) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        return new HttpEntity<Object>(param, headers);
    }

    public Result getPayOrderInfo(Long payInfId) {
//        payHost = "http://localhost:6311";
        Result mapResult = restTemplate.getForObject(payHost + "/h9/pay/order/payinfo?id="
                + payInfId + "&bid=" + bid, Result.class);
        return mapResult;

    }

    /**
     * @param id              payInfo Id
     * @param payMoney4Wechat
     * @return
     */
    public Result refundOrder(Long id, BigDecimal payMoney4Wechat, int payMethod) {

        //WX(2, "wx"), WXJS(3, "wxjs"),
        InputStream is = null;
        if (payMethod == 3) {
            mchId =environment.getProperty("wx.pay.mchid");
            appid = environment.getProperty("wx.pay.appid");
            payKey = environment.getProperty("wx.paykey");

            if (env.equals("product")) {
                is = this.getClass().getClassLoader().getResourceAsStream("apiclient_cert_wxjs.p12");
            }else{
                is = this.getClass().getClassLoader().getResourceAsStream("apiclient_cert_wd_wxjs.p12");
            }
        } else if (payMethod == 2) {

            mchId =environment.getProperty("client.pay.mchId");
            appid = environment.getProperty("client.pay.appId");
            payKey = environment.getProperty("client.pay.apiKey");

            is = this.getClass().getClassLoader().getResourceAsStream("apiclient_cert_wx.p12");
        } else {
            mchId =environment.getProperty("wx.pay.mchid");
            appid = environment.getProperty("wx.pay.appid");
            payKey = environment.getProperty("wx.paykey");
            is = this.getClass().getClassLoader().getResourceAsStream("apiclient_cert_wxjs.p12");
        }
        byte[] bytes = null;
        try {
            bytes = IOUtils.toByteArray(is);
            IOUtils.closeQuietly(is);
        } catch (NullPointerException ne) {
            return Result.fail("证书不存在");
        } catch (Exception e) {
            return Result.fail("读取证书失败");
        }
        RefundDTO refundDTO = new RefundDTO(payMoney4Wechat, mchId, appid, payKey, id + "", bid, bytes);

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
