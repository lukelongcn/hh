package com.h9.api.provider;

import com.h9.api.model.dto.MobileRechargeDTO;
import com.h9.common.base.Result;
import com.h9.common.utils.MD5Util;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringReader;
import java.text.SimpleDateFormat;

/**
 * description: 手机充值
 */
@Component
public class MobileRechargeService {

    private Logger logger = Logger.getLogger(this.getClass());
    private static final String url = "http://apitest.ofpay.com/onlineorder.do";
    @Value("${ofpay.userid}")
    private String userId;
    @Value("${ofpay.userpwd}")
    private String userpws;
    private String keyStr = "OFCARD";
    private RestTemplate restTemplate = new RestTemplate();

    //md5_str检验码的计算方法:

//    包体=userid+userpws+cardid+cardnum+sporder_id+sporder_time+ game_userid
    //2: KeyStr(秘钥) 必须由客户提供欧飞商务进行绑定

    public Result recharge(MobileRechargeDTO mobileRechargeDTO,Long id) {
        String userpwsmd5 = MD5Util.getMD5(userpws);
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
                return Result.success("充值失败",rechargeResult);
            }
        } catch (JAXBException e) {
            logger.info(e.getMessage(), e);
        }


        return Result.success("充值失败");
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
