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

    private String merchantNo = "70057039";
    private String publicKeyIndex = "0001";
    private String productCode = "01070000042";
    private String goodType = "220029";
    @Value("${shuning.withdraw.callback}")
    private String notifyUrl;
    private String myPrivateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIJc6uye1r6ZUtRGy+R0oLaUsAZd" +
            "r3ra2w2f+ID1cFhPZh6bQU7mFzC2z6MflUL3GhMjDPQQwP8Rt9sn366ECuGC3bVrl9dr1g+Q/52q" +
            "PpZy4C2tXTPOQbyTbTzfpXCnTCXai1en3i9kjilDS0aLRa4w1JkHf+AvHA/kUllAHSRhAgMBAAEC" +
            "gYAydXK9KfInDkPARLLw96+pXD4SCLs+i23UhUHz8IyOshTt7dxGhMsfIPOXyUbGB81A8QU3hzCc" +
            "yifnHTT4YMWw3K/2e8BJ37acMncd0tEdtxPfMNCqoqmJRB61uUFRhBjorfS5CJviYVrbPenzAuHq" +
            "rVvaviiO2v22529fyShtUQJBALx8HKcH3lZ6S2/tE6fi/KONtCrpevBD+0Ffz5pe8QBD+1LSmKTm" +
            "BZQyNH2A7rWR4M1CKJFe2HejnyknL2P5p30CQQCxDxWEHj3ZawaiPB/soXOE+DXd92Vk5boopVS6" +
            "QnwBTy9LfUuOtks+7FL7fLo40K31zC8Q7MY+y+QUWPVGp+21AkA108vq95hLYgmBIVdnrq8vlhxJ" +
            "1PvC+ecbOF11XH++76sqb/IfxYD6XYwX+2YwfESS2b30Jf3zzMp7WjFyf62RAkB80zCNCzD5Zb2w" +
            "hIjRL2WcmcyIJxEBl/+tBhn8kkCQP74ND1FEVHop6zv5do5m3Z+2yPNpkDOXM/Eg8zJAVMV9AkBP" +
            "xWC/hpaQg5oBbbS+qLYW6M/PWyLVn5q2bgI+twnTFUn1BlGdn/ySPqcx/qKt61IwxHkQH0GsD/W9" +
            "u5ez9LQ4";


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
            JSONObject http_result = JSONObject.parseObject(post);
            if(http_result!=null&&http_result.containsKey("responseCode")){
                String responseCode = http_result.getString("responseCode");
                if("0000".equals(responseCode)){
                    Result.success();
                }
            }
            logger.debugv(post);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result.fail("提现失败");
    }


    public String sign(String param) throws InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        Map<String, String> signMap = new HashMap<String, String>();
        signMap.put("body", param);
        signMap.put("merchantNo", merchantNo);
        signMap.put("publicKeyIndex",publicKeyIndex);
        signMap.put("inputCharset", "UTF-8");
        String digest = Digest.digest(Digest.mapToString(Digest.treeMap(signMap)));
        logger.debugv("digest"+digest);
        PrivateKey privateKey = RSAUtil.getPrivateKey(myPrivateKey);
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
        contentObject.put("merchantNo", merchantNo);// 70057241;70056575
        contentObject.put("productCode", productCode);
        contentObject.put("totalNum", 1);
        contentObject.put("totalAmount", withdrawDTO.getMoneyPercent());// 40*detailNum
        contentObject.put("currency", "CNY");
        contentObject.put("payDate", withdrawDTO.getDate());
//        contentObject.put("tunnelData", "{\"businessOrderId\":\""+withdrawDTO.getOrderId()+"\"}");
        contentObject.put("detailData", body);
        contentObject.put("notifyUrl", notifyUrl);
        contentObject.put("batchOrderName", "提现");
        contentObject.put("goodsType", goodType);
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
