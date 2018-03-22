package com.h9.api.provider;

import com.alibaba.fastjson.JSONObject;
import com.h9.api.model.dto.MobileRechargeDTO;
import com.h9.common.base.Result;
import com.h9.common.common.ConfigService;
import com.h9.common.common.MailService;
import com.h9.common.utils.MD5Util;
import com.h9.common.utils.MoneyUtils;
import org.jboss.logging.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * description: 手机充值
 */
@Component
public class MobileRechargeService {

    @Resource
    private ConfigService configService;
    @Resource
    private MailService mailService;
    private Logger logger = Logger.getLogger(this.getClass());
    private static final String url = "http://apitest.ofpay.com/onlineorder.do";
    private static final String onlineUrl = "http://api2.ofpay.com/onlineorder.do";
//    ofpay.userid=A1403689
//    ofpay.userpwd=w5AURF

//    @Value("${ofpay.userid}")
    private String userId = "A1403689";
//    @Value("${ofpay.userpwd}")
    private String userpws = "w5AURF";
    private String keyStr = "H9@hf016";
    private RestTemplate restTemplate = new RestTemplate();

    //md5_str检验码的计算方法:

//    包体=userid+userpws+cardid+cardnum+sporder_id+sporder_time+ game_userid
    //2: KeyStr(秘钥) 必须由客户提供欧飞商务进行绑定

    /**
     * description: 测试环境调用
     */
    public Result rechargeTest(MobileRechargeDTO mobileRechargeDTO, String rechargeId, BigDecimal realPrice) {
        String userpwsmd5 = "4c625b7861a92c7971cd2029c2fd3c4a";
        String cardid = "140101";
        String cardnum = "50";
        //商户订单号
        String sporderId = "test001234567";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//        String sporder_time = formatter.format(new Date());
        String sporder_time = "20160817140214";
        String game_userid = "15996271050";
//        String mg5_str = "B662CA03452882C761BC75585BA2483F";
        String version = "6.0";
        MultiValueMap<String, String> map = new LinkedMultiValueMap();
        map.add("userid", "A08566");
        map.add("userpws", userpwsmd5);
        map.add("cardid", cardid);
        map.add("cardnum", cardnum);
        map.add("sporder_id", sporderId);
        map.add("sporder_time", sporder_time);
        map.add("game_userid", game_userid);
        map.add("version", version);
        String s = userId + userpwsmd5 + cardid + cardnum + sporderId + sporder_time + game_userid;
        s += keyStr;
        String md5 = MD5Util.getMD5(s);
        map.add("md5_str", "B662CA03452882C761BC75585BA2483F");
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        String body = restTemplate.postForEntity(url, request, String.class).getBody();
        try {

            JAXBContext jc = JAXBContext.newInstance(Orderinfo.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            Orderinfo rechargeResult = (Orderinfo) unmarshaller.unmarshal(new StringReader(body));

            if (rechargeResult.retcode.equals("1")) {
                return Result.success("充值成功",rechargeResult);
            } else {
                return Result.fail("充值失败",rechargeResult);
            }
        } catch (JAXBException e) {
            logger.info(e.getMessage(), e);
        }


        return Result.success("充值失败");
    }


    public static void main(String[] args) {

//        String s = "A085664c625b7861a92c7971cd2029c2fd3c4a14010150test0012345672016081714021415996271050OFCARD";
//        String md5 = MD5Util.getMD5(s);
//        System.out.println(md5);

    }
    /**
     * description: 正式环境调用
     */
    public Result recharge(MobileRechargeDTO mobileRechargeDTO, String rechargeId, BigDecimal realPrice) {
        logger.info("短信充值 " + JSONObject.toJSONString(mobileRechargeDTO));
        String userpwsmd5 = MD5Util.getMD5(userpws).toLowerCase();
        String cardid = "140101";
        String cardnum = "";

        if (realPrice.compareTo(new BigDecimal(1)) < 0) {
            cardnum = MoneyUtils.formatMoney(realPrice, "0.00");
        }else {
            cardnum = MoneyUtils.formatMoney(realPrice, "0");
        }

        //商户订单号
        String sporderId = rechargeId;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String sporder_time = formatter.format(new Date());
        String game_userid = mobileRechargeDTO.getTel();
//        String mg5_str = "B662CA03452882C761BC75585BA2483F";
        String version = "6.0";
        MultiValueMap<String, String> map = new LinkedMultiValueMap();
        map.add("userid", userId);
        map.add("userpws", userpwsmd5);
        map.add("cardid", cardid);
        map.add("cardnum", cardnum);
        map.add("sporder_id", sporderId);
        map.add("sporder_time", sporder_time);
        map.add("game_userid", game_userid);
        map.add("version", version);

        String s = userId + userpwsmd5 + cardid + cardnum + sporderId + sporder_time + game_userid;
        s += keyStr;
        String md5 = MD5Util.getMD5(s);
        map.add("md5_str", md5.toUpperCase());
        logger.info("话费充值： params : "+JSONObject.toJSONString(map));
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        String body = restTemplate.postForEntity(onlineUrl, request, String.class).getBody();
        logger.info("充值结果："+body);
        try {
            JAXBContext jc = JAXBContext.newInstance(Orderinfo.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            Orderinfo rechargeResult = (Orderinfo) unmarshaller.unmarshal(new StringReader(body));

            if (rechargeResult.retcode.equals("1")) {
                logger.info("充值成功");
                return Result.success("充值成功",rechargeResult);
            } else {
                logger.info("充值失败");
                sendFailEmail(rechargeResult);
                return Result.fail("充值失败",rechargeResult);
            }
        } catch (JAXBException e) {
            logger.info(e.getMessage(), e);
        }

        return Result.fail("充值失败");
    }

    /**
     * 处理充值话费失败,发送错误邮件
     * @param rechargeResult 欧飞充值结果
     */
    public void sendFailEmail(Orderinfo rechargeResult) {

        mailService.sendtMail("手机话费充值失败",
                "充值结果: "+JSONObject.toJSONString(rechargeResult));
        if(rechargeResult.retcode.equals("1007")){

            List<String> emailGroup = configService.getStringListConfig("MobileRechargeFailEmailGroup");
            mailService.sendEmail("手机话费充值失败"
                    , "原因: "+rechargeResult.getErr_msg()
                    ,emailGroup);

        }
    }


    @XmlRootElement
    public static class Orderinfo {
        private String err_msg;
        private String retcode;
        private String orderid;
        private String cardid;
        private String cardnum;
        private String ordercash;
        private String cardname;
        private String sporder_id;
        private String game_userid;
        private String game_state;


        public String getErr_msg() {
            return err_msg;
        }

        public void setErr_msg(String err_msg) {
            this.err_msg = err_msg;
        }

        public String getRetcode() {
            return retcode;
        }

        public void setRetcode(String retcode) {
            this.retcode = retcode;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getCardid() {
            return cardid;
        }

        public void setCardid(String cardid) {
            this.cardid = cardid;
        }

        public String getCardnum() {
            return cardnum;
        }

        public void setCardnum(String cardnum) {
            this.cardnum = cardnum;
        }

        public String getOrdercash() {
            return ordercash;
        }

        public void setOrdercash(String ordercash) {
            this.ordercash = ordercash;
        }

        public String getCardname() {
            return cardname;
        }

        public void setCardname(String cardname) {
            this.cardname = cardname;
        }

        public String getSporder_id() {
            return sporder_id;
        }

        public void setSporder_id(String sporder_id) {
            this.sporder_id = sporder_id;
        }

        public String getGame_userid() {
            return game_userid;
        }

        public void setGame_userid(String game_userid) {
            this.game_userid = game_userid;
        }

        public String getGame_state() {
            return game_state;
        }

        public void setGame_state(String game_state) {
            this.game_state = game_state;
        }

        @Override
        public String toString() {
            return "RechargeResult{" +
                    "err_msg='" + err_msg + '\'' +
                    ", retcode='" + retcode + '\'' +
                    ", orderid='" + orderid + '\'' +
                    ", cardid='" + cardid + '\'' +
                    ", cardnum='" + cardnum + '\'' +
                    ", ordercash='" + ordercash + '\'' +
                    ", cardname='" + cardname + '\'' +
                    ", sporder_id='" + sporder_id + '\'' +
                    ", game_userid='" + game_userid + '\'' +
                    ", game_state='" + game_state + '\'' +
                    '}';
        }
    }
}
