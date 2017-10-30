package com.h9.api.provider;

import com.h9.common.base.Result;
import com.h9.common.utils.MD5Util;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import sun.plugin2.main.client.MozillaServiceDelegate;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * description: 手机充值
 */
@Component
public class MobileRechargeService {

    private static final String url = "http://apitest.ofpay.com/onlineorder.do";
    private static final String userId = "A08566";
    private static final String userpws = MD5Util.getMD5("of111111");
    private static final String keyStr = "OFCARD";
    private RestTemplate restTemplate = new RestTemplate();

    //md5_str检验码的计算方法:

//    包体=userid+userpws+cardid+cardnum+sporder_id+sporder_time+ game_userid
    //2: KeyStr(秘钥) 必须由客户提供欧飞商务进行绑定

    public Result recharge() {
        String cardid = "140101";
        String cardnum = "50";
        String sporderId = "test001234567";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//        String sporder_time = formatter.format(new Date());
        String sporder_time = "20160817140214";
        String game_userid = "15996271050";
//        String mg5_str = "B662CA03452882C761BC75585BA2483F";
        String version = "6.0";
        MultiValueMap<String, String> map = new LinkedMultiValueMap();
        map.add("userid", userId);
        map.add("userpws", userpws);
        map.add("cardid", cardid);
        map.add("cardnum", cardnum);
        map.add("sporder_id", sporderId);
        map.add("sporder_time", sporder_time);
        map.add("game_userid", game_userid);
        map.add("version", version);
        String s = userId + userpws + cardid + cardnum + sporderId + sporder_time + game_userid;
        s += keyStr;
        System.out.println("--:"+s);
        System.out.println("--:A085664c625b7861a92c7971cd2029c2fd3c4a14010150test0012345672016081714021415996271050OFCARD");
        System.out.println(s.equals("A085664c625b7861a92c7971cd2029c2fd3c4a14010150test0012345672016081714021415996271050OFCARD"));
        String md5 = MD5Util.getMD5(s);
        System.out.println(md5);
        System.out.println("B662CA03452882C761BC75585BA2483F".equals(md5.toUpperCase()));
        map.add("md5_str", md5.toUpperCase());
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        String body = restTemplate.postForEntity(url, request, String.class).getBody();
        System.out.println(body);
        return null;
    }

    public static void main(String[] args) {
        MobileRechargeService service = new MobileRechargeService();
        service.recharge();
    }

}
