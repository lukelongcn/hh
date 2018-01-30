package com.h9.common.common;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.h9.common.base.Result;
import com.h9.common.db.entity.account.BalanceFlow;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserAccount;
import com.h9.common.db.entity.user.UserRecord;
import com.h9.common.db.repo.*;
import com.h9.common.utils.NetworkUtil;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * CommonService:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/6
 * Time: 14:50
 */
@Component
public class CommonService {

    Logger logger = Logger.getLogger(CommonService.class);

    @Resource
    private UserAccountRepository userAccountRepository;
    @Resource
    private BalanceFlowRepository balanceFlowRepository;
    @Resource
    private UserRecordRepository userRecordRepository;
    @Resource
    private UserRepository userRepository;


    @Transactional
    public Result setBalance(Long userId, BigDecimal money, Long typeId, Long orderId, String orderNo, String remarks) {
        this.logger.infov("userId:{0}",userId);
        UserAccount userAccount = userAccountRepository.findByUserIdLock(userId);
        this.logger.infov("userAccount:{0}",JSONObject.toJSON(userAccount));
        BigDecimal balance = userAccount.getBalance();
        BigDecimal newbalance = balance.add(money);

        if (money.compareTo(new BigDecimal(0)) < 0) {
            if (newbalance.compareTo(new BigDecimal(0)) < 0) {
                return Result.fail("余额不足");
            }
        }

        userAccount.setBalance(newbalance);
        BalanceFlow balanceFlow = new BalanceFlow();
        balanceFlow.setBalance(newbalance);
        balanceFlow.setMoney(money);
        balanceFlow.setFlowType(typeId);
        balanceFlow.setOrderId(orderId);
        balanceFlow.setOrderNo(orderNo);
        balanceFlow.setRemarks(remarks);
        balanceFlow.setUserId(userId);

        userAccountRepository.save(userAccount);
        balanceFlowRepository.save(balanceFlow);
        return Result.success();
    }



    /****
     * 通过经纬都获取地址信息
     * @param lat
     * @param lon
     * @return
     */
    public String getAdressUrl(double lat, double lon) {
        return MessageFormat.format("http://api.map.baidu.com/geocoder/v2/?location={0},{1}&output=json&ak=vnCSOl4W7bgaIQH3GtNVTdFXRcU8hcCD", lat, lon);
    }

    public Result<JSONObject> getAddress(double lat, double lon) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String forObject = restTemplate.getForObject(getAdressUrl(lat, lon), String.class);
            System.out.println("------" + forObject + "------");
            JSONObject jsonObject = JSONObject.parseObject(forObject);
            if (jsonObject.getInteger("status") == 0) {
                JSONObject result = jsonObject.getJSONObject("result");
//                return result.getString("formatted_address");

                return Result.success(result);
            } else {
                return Result.fail();
            }

        } catch (Exception ex) {
            logger.debugv(ex.getMessage(), ex);
        }
        return Result.fail();
    }

    @Transactional
    public UserRecord newUserRecord(Long userId, double latitude, double longitude, HttpServletRequest request) {
        UserRecord userRecord = new UserRecord();
        userRecord.setUserId(userId);

        userRecord.setLatitude(latitude);
        userRecord.setLongitude(longitude);


        try {
            String refer = request.getHeader("Referer");
            String userAgent = request.getHeader("User-Agent");
            userRecord.setUserAgent(userAgent);
            userRecord.setRefer(refer);
            String ip = NetworkUtil.getIpAddress(request);
            userRecord.setIp(ip);
            String client = request.getHeader("client");
            if (StringUtils.isNotEmpty(client)) {
                userRecord.setClient(Integer.parseInt(client));
            }
            String version = request.getHeader("version");
            userRecord.setVersion(version);
            if (latitude != 0 && longitude != 0) {

                Result<JSONObject> addressQueryResult = getAddress(latitude, longitude);
                if (addressQueryResult.getCode() == 0) {
                    String detailAddress = addressQueryResult.getData().getString("formatted_address");
                    JSONObject data = addressQueryResult.getData().getJSONObject("addressComponent");

                    userRecord.setProvince(data.getString("province"));
                    userRecord.setCity(data.getString("city"));
                    User user = userRepository.findOne(userId);

                    userRecord.setDistrict(data.getString("district"));
                    userRecord.setStreet(data.getString("street"));
                    userRecord.setStreetNumber(data.getString("street_number"));
                    userRecord.setAddress(detailAddress);

                    if(user != null){
                        user.setLongitude(longitude);
                        user.setLatitude(latitude);
                        user.setCity(data.getString("city"));
                        user.setProvince(data.getString("province"));
                        user.setStreet(data.getString("street"));
                        user.setStreetNumber(data.getString("street_number"));
                        userRepository.save(user);
                    }
                }

            }
            String imei = request.getHeader("imei");
            userRecord.setImei(imei);
        } catch (Exception ex) {
            logger.debugv(ex.getMessage(), ex);
        }


        return userRecordRepository.saveAndFlush(userRecord);
    }

    public AddressResult getAddressDetail(double lat, double lon) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String forObject = restTemplate.getForObject(getAdressUrl(lat, lon), String.class);
            logger.debugv(forObject);
            JSONObject jsonObject = JSONObject.parseObject(forObject);

            if (jsonObject.getInteger("status") == 0) {
                JSONObject result = jsonObject.getJSONObject("result");
                AddressResult addressResult = new AddressResult();
                JSONObject addressComponent = result.getJSONObject("addressComponent");
                AddressResult addressResultFromNet = JSONObject.parseObject(addressComponent.toJSONString(), AddressResult.class);
                BeanUtils.copyProperties(addressResultFromNet,addressResult);
                addressResult.setDetailAddress(result.getString("formatted_address"));
                return addressResult;
            } else {
                return null;
            }

        } catch (Exception ex) {
            logger.debugv(ex.getMessage(), ex);
        }
        return null;
    }


    public static class AddressResult{
        private String province;
        private String city;
        private String district;
        private String street;
        @JSONField(name = "street_number")
        private String streetNumber;
        private String detailAddress;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getStreetNumber() {
            return streetNumber;
        }

        public void setStreetNumber(String streetNumber) {
            this.streetNumber = streetNumber;
        }

        public String getDetailAddress() {
            return detailAddress;
        }

        public void setDetailAddress(String detailAddress) {
            this.detailAddress = detailAddress;
        }
    }


    /**
     * 根据内容匹配图片
     */
    public List<String> image(String content){
        List<String> imagesList = new ArrayList<>();
        String img = "";
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        Pattern p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        Matcher m_image = p_image.matcher(content);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                imagesList.add(m.group(1));
            }
        }
        return imagesList;
    }
}
