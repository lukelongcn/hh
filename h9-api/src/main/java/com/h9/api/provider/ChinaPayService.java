package com.h9.api.provider;

import chinapay.Base64;
import chinapay.PrivateKey;
import chinapay.SecureLink;
import com.h9.common.base.Result;
import com.h9.common.db.entity.WithdrawalsRequest;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by itservice on 2017/11/3.
 */

@Service
public class ChinaPayService {
    @Value("${chinaPay.testUrl}")
    private String url;
    @Value("${chinaPay.merId}")
    private String merId;
    @Value("${chinaPay.queryUrl}")
    private String queryUrl;
    private Logger logger = Logger.getLogger(this.getClass());
    /**
     * description: 银行代付
     */
    public Result signPay(PayParam payParam,String merDate) {

        String s = merId + merDate + payParam.getMerSeqId() + payParam.getCardNo() + payParam.getUsrName() + payParam.getOpenBank()
                + payParam.getProv() + payParam.getCity() + payParam.getTransAmt() + payParam.getPurpose() + payParam.getVersion();

        PrivateKey key = new PrivateKey();
        String path = "D:\\MerPrK_808080211881410_20171102154758.key";
        boolean buildOK = key.buildKey(merId, 0, path);
        if (!buildOK) {
            System.out.println("没有找到私钥文件");
        }
        System.out.println(buildOK);
        SecureLink secureLink = new SecureLink(key);
        char[] encode = Base64.encode(s.getBytes());
        String sign = secureLink.Sign(new String(encode));

        System.out.println("------");
        System.out.println(sign);
        System.out.println("------");

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("merId", merId);
        params.add("merDate", merDate);
        System.out.println(merDate);
        params.add("merSeqId", payParam.getMerSeqId());
        params.add("cardNo", payParam.getCardNo());
        params.add("usrName", payParam.getUsrName());
        params.add("openBank", payParam.getOpenBank());
        params.add("prov", payParam.getProv());
        params.add("city", payParam.getCity());
        params.add("transAmt", payParam.getTransAmt());
        params.add("purpose", payParam.getPurpose());
        params.add("version", payParam.getVersion());
        params.add("signFlag", payParam.getSignFlag());
        params.add("chkValue", sign);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String body = res.getBody();
        return Result.success("success",body);
    }


    public Result query(WithdrawalsRequest withdrawalsRequest)  {
        String merDate = withdrawalsRequest.getMerDate();
        String merSeqId = withdrawalsRequest.getMerSeqId();

        String version = withdrawalsRequest.getVersion();
        String signFlag = withdrawalsRequest.getSignFlag();
        String chkValue = merId+merDate+merSeqId+version;

        PrivateKey key = new PrivateKey();
        String path = "D:\\MerPrK_808080211881410_20171102154758.key";
        boolean buildOK = key.buildKey(merId, 0, path);
        if(!buildOK){
            System.out.println("没有找到私钥文件");
        }
        System.out.println(buildOK);
        SecureLink secureLink = new SecureLink(key);

        chkValue = new String(Base64.encode(chkValue.getBytes()));
        chkValue = secureLink.Sign(new String(chkValue));

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("merId",merId);
        params.add("merDate",merDate);
        params.add("merSeqId",merSeqId);
        params.add("version",version);
        params.add("signFlag",signFlag);
        params.add("chkValue",chkValue);
        HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<>(params,headers);
        ResponseEntity<String> res = restTemplate.exchange(queryUrl, HttpMethod.POST, httpEntity, String.class);

        String gbk = null;
        try {
            gbk = new String(res.getBody().toString().getBytes("GBK"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.info(e.getMessage(),e);
        }

        return Result.success("",gbk);
    }

    public static class PayParam {
        private String merSeqId;
        private String cardNo;
        private String usrName;
        private String openBank;
        private String prov;
        private String city;
        private String transAmt;
        private String purpose;
        private String version = "20151207";
        private String signFlag = "1";
        private String termType = "7";

        public PayParam() {
        }

        public PayParam(String merSeqId, String cardNo, String usrName, String openBank, String prov, String city, String transAmt, String signFlag, String purpose) {
            this.merSeqId = merSeqId;
            this.cardNo = cardNo;
            this.usrName = usrName;
            this.openBank = openBank;
            this.prov = prov;
            this.city = city;
            this.transAmt = transAmt;
            this.signFlag = signFlag;
            this.purpose = purpose;
        }

        public String getMerSeqId() {
            return merSeqId;
        }

        public void setMerSeqId(String merSeqId) {
            this.merSeqId = merSeqId;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getUsrName() {
            return usrName;
        }

        public void setUsrName(String usrName) {
            this.usrName = usrName;
        }

        public String getOpenBank() {
            return openBank;
        }

        public void setOpenBank(String openBank) {
            this.openBank = openBank;
        }

        public String getProv() {
            return prov;
        }

        public void setProv(String prov) {
            this.prov = prov;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getTransAmt() {
            return transAmt;
        }

        public void setTransAmt(String transAmt) {
            this.transAmt = transAmt;
        }

        public String getPurpose() {
            return purpose;
        }

        public void setPurpose(String purpose) {
            this.purpose = purpose;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getSignFlag() {
            return signFlag;
        }

        public void setSignFlag(String signFlag) {
            this.signFlag = signFlag;
        }

        public String getTermType() {
            return termType;
        }

        public void setTermType(String termType) {
            this.termType = termType;
        }
    }
}
