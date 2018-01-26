package com.h9.api.provider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.h9.api.provider.handler.SuNingHandler;
import com.h9.api.provider.model.WithdrawDTO;
import com.h9.common.base.Result;
import com.h9.common.utils.MD5Util;
import com.suning.epps.codec.Digest;
import com.suning.epps.codec.RSAUtil;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * SuNingProvider:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/25
 * Time: 14:08
 */
@Service
public class SuNingProvider {
    
     Logger logger = Logger.getLogger(SuNingProvider.class);

     @Resource
     private SuNingHandler suNingHandler;


    @Value("${shuning.withdraw.host}")
    private String POST_Host;

    private String merchantNo = "suning";
    private String publicKeyIndex = "xpy1";
    private String payMerchantNo ="70055995";
    private String productCode = "01060000029";
    private String notifyUrl = "https://console-dev-h9.thy360.com/wechat/index";


    public String  getWithdraw(){
        return POST_Host + "epps-wag/withdraw.htm";
    }

    public String  getWithdrawQueryUrl(){
        return POST_Host + "epps-wag/withdrawQuery.htm";
    }


    public Result withdraw(WithdrawDTO withdrawDTO) {
        JSONArray batchData = createBatchData(withdrawDTO);
        String body = batchData.toJSONString();
        try {
            String sign = sign(body);
            MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
            params.add("merchantNo", merchantNo);
            params.add("publicKeyIndex", publicKeyIndex);
            params.add("signature", sign);
            params.add("signAlgorithm", "RSA");
            params.add("inputCharset", "UTF-8");
            params.add("body", body);
            logger.debugv(JSONObject.toJSONString(params));
            String post = suNingHandler.post(getWithdraw(), params);
            logger.debugv(post);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result.success();
    }






    public String sign(String param) throws InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        Map<String, String> signMap = new HashMap<String, String>();
        signMap.put("body", param);
        signMap.put("merchantNo", merchantNo);
        signMap.put("publicKeyIndex",publicKeyIndex);
        signMap.put("inputCharset", "UTF-8");
        String wagKeyString = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIWvIOflNkPsEvGWCvvTM4tlWcodoPbC52Q6EXsv9UJyOqbzfX9u9xGRLiUv3fOs9J02QQPK6ZPQiR0g8RvPv2858bh5finE13iwIuYTpgSRZVi2Kn7goer4qqwXD_TjM1B6PIpOylJksbF9RESZP0A8cG2twJprdZ54xYj4HIGvAgMBAAECgYAPG-b9LpO-g3z0nv-ozIsD0zWduVGK8iZS1plJMfdnRh_I5LYnY_Q6oQz1GP7d3otbBVm9wv45PZVxnFqDySwajI4uAP9ZZQ8RHrPTNttFgLm3OQ0shbIUhBi2vXorxicR-EnS4qWpCOP10o5JrlpieZ295S2p7Dn_xmIoRgPRKQJBAN4ilfdxuEU3E-eiPo98gUXFPpCCCXKla4JMvN2R6em8d0MVYT8g0rXoXS44UnEg0vOoJ7ulPh5Col6ilqR2op0CQQCaEIGvc2PDa8jHXSmDuwpl4ogqafNyY7FCjPqWvlG-_auU0qaKBuVhIEMuy-3ZUMFFCsmGkMOKr_7ACTW3bM27AkEAwCihHIYmhtGniWhjwBJPbgC8J5wl-iQ5RWWGuBGCjSz46nIzRr3pKW2SNeqI_s4LTrY3cO74NoskFMOHl0v9TQJARmWofH0jZtZHZiGBqLm8pJWAVrEXFnvLMXetwVexjq3myxf-FS_VfC37xNRWGGi4B05Ii352e1az9xe-PdQvpQJATWPfQc1IR-cefoAvEcUyQlTsthVQkJ3wUpRMEosw2V5a1f9euwhJXJDf6ca8zOhtyfXuTIag1YturYfKyXgY3w";
        String digest = MD5Util.getMD5(Digest.mapToString(Digest.treeMap(signMap)));
        PrivateKey privateKey = RSAUtil.getPrivateKey(wagKeyString);
        String signature = RSAUtil.sign(digest, privateKey);
        logger.debugv("sign:"+signature);
        return signature;
    }




    /**
     * 生成请求wag多个批次的json数据方法
     * 生成提现批次
     * @return JSONArray
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
        public JSONArray createBatchData(WithdrawDTO withdrawDTO) {
            JSONArray jsonArray = new JSONArray();
            JSONArray body = bulidDetailContentJosn(withdrawDTO);
            jsonArray.add(bulidBatchContentJosn(withdrawDTO,body.toJSONString()));
            return jsonArray;
        }


        /**
         * 生成body批次数据
         *
         * @param
         * @return JSONObject
         * @see [相关类/方法]（可选）
         * @since [产品/模块版本] （可选）
         */
    public JSONObject bulidBatchContentJosn(WithdrawDTO withdrawDTO,String body ) {
        JSONObject contentObject = new JSONObject();
        contentObject.put("batchNo", withdrawDTO.getOrderId());
        contentObject.put("merchantNo", payMerchantNo);// 70057241;70056575
        contentObject.put("productCode", productCode);
        contentObject.put("totalNum", 1);
        contentObject.put("totalAmount", withdrawDTO.getMoneyPercent());// 40*detailNum
        contentObject.put("currency", "CNY");
        contentObject.put("payDate", withdrawDTO.getDate());
//        contentObject.put("tunnelData", "{\"businessOrderId\":\""+withdrawDTO.getOrderId()+"\"}");
        contentObject.put("detailData", body);
        contentObject.put("notifyUrl", notifyUrl);
        contentObject.put("batchOrderName", "提现");
        contentObject.put("goodsType", "220026");
        return contentObject;
    }


    /**
     * 生成body明细数据
     *
     * @param
     * @return JSONObject
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    public JSONArray bulidDetailContentJosn(WithdrawDTO withdrawDTO) {
        JSONArray detailArray = new JSONArray();
        JSONObject detailObject = new JSONObject();
        detailObject.put("serialNo", withdrawDTO.getOrderNo());
        detailObject.put("receiverCardNo", withdrawDTO.getNo());
        detailObject.put("receiverName", withdrawDTO.getName());
        detailObject.put("receiverType", "PERSON");
        detailObject.put("receiverCurrency", "CNY");
        detailObject.put("bankName", withdrawDTO.getBankName());
        detailObject.put("bankCode", withdrawDTO.getBankTypeCode());
        detailObject.put("bankProvince", withdrawDTO.getProvince());
        detailObject.put("bankCity", withdrawDTO.getCity());
        detailObject.put("amount", withdrawDTO.getMoneyPercent());
        detailObject.put("orderName", "提现");
//        detailObject.put("payeeBankLinesNo", "");
        detailArray.add(detailObject);

        return detailArray;

    }


}
