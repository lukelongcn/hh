package com.h9.api;

import chinapay.Base64;
import chinapay.PrivateKey;
import chinapay.SecureLink;
import chinapay.util.SecureUtil;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CloseShieldInputStream;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by itservice on 2017/11/2.
 */
public class Test {

    public String accessKey = "jSjj4DZYDPO2l00Iejd7MnUNUCapVd_IDu1m7LNq";
    public String secretKey = "3wKtwQGZfA7xFIsAt3I1LDaOR_kF6UVvRojLdi9k";
    String bucket = "h9-joyful-img";


    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = "111111111111113";
        String substring = s.substring(0, 14);
        System.out.println(substring);
    }

    @org.junit.Test
    public void downLoad() throws UnsupportedEncodingException {
        String fileName = "FoHkjc0XuS0niM_RKKJkWiGZLC5q";
        String domainOfBucket = "http://devtools.qiniu.com";
        String encodedFileName = URLEncoder.encode(fileName, "utf-8");
        String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
        Auth auth = Auth.create(accessKey, secretKey);
        long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
        String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        System.out.println(finalUrl);
    }

    @org.junit.Test
    public void downLoad2() throws UnsupportedEncodingException {
        String fileName = "FoHkjc0XuS0niM_RKKJkWiGZLC5q";

        String domainOfBucket = "http://devtools.qiniu.com";
        String encodedFileName = URLEncoder.encode(fileName, "utf-8");
        String finalUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
        System.out.println(finalUrl);
    }

    @org.junit.Test
    public void upload() {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "jSjj4DZYDPO2l00Iejd7MnUNUCapVd_IDu1m7LNq";
        String secretKey = "3wKtwQGZfA7xFIsAt3I1LDaOR_kF6UVvRojLdi9k";
        String bucket = "h9-joyful-img";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "Config:\\Users\\itservice\\Pictures\\235113-1403260U22059.jpg";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    @org.junit.Test
    public void getInfo() {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
//...其他参数参考类注释
        String key = "FoHkjc0XuS0niM_RKKJkWiGZLC5q";
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            FileInfo fileInfo = bucketManager.stat(bucket, key);
            System.out.println(fileInfo.key);
            System.out.println(fileInfo.hash);
            System.out.println(fileInfo.fsize);
            System.out.println(fileInfo.mimeType);
            System.out.println(fileInfo.putTime);
        } catch (QiniuException ex) {
            System.err.println(ex.response.toString());
        }
    }

    @org.junit.Test
    public void chinaPayTest2() throws IOException {
        String version = "20090501";
        String signFlag = "1";
        String chkValue = "";
        String merId = "808080211881410";

        String money = "101";
        SimpleDateFormat format = new SimpleDateFormat("YYYYMMDD");
        String date = format.format(new Date());
        String bachNumber = "000001";
        StringBuilder fileName = new StringBuilder();
        fileName.append(merId);
        fileName.append("_");
        fileName.append(date);
        fileName.append("_");
        fileName.append(bachNumber);
        fileName.append(".txt");
        System.out.println("filename: " + fileName);

        StringBuilder fileHead = new StringBuilder();
        fileHead.append(merId);
        fileHead.append("|");
        fileHead.append(bachNumber);
        fileHead.append("|");
        fileHead.append("1");
        fileHead.append("|");
        fileHead.append(money);

        StringBuilder fileBody = new StringBuilder();
        fileBody.append(merId);
        fileBody.append("|");
        String flowNumber = "0000000000000001";
        fileBody.append(flowNumber);
        fileBody.append("|");
        fileBody.append("李圆");
        fileBody.append("|");
        fileBody.append("6210984280001561104");
        fileBody.append("|");
        fileBody.append("中国邮政储蓄银行");
        fileBody.append("|");
        fileBody.append("江西省");
        fileBody.append("|");
        fileBody.append("赣州");
        fileBody.append("|");
        fileBody.append("中国邮政储蓄银行");
        fileBody.append("|");
        fileBody.append(money);
        fileBody.append("|");
        fileBody.append("提现");

        String fileContent = fileHead + "\n" + fileBody;

        File file = new File("/" + fileName);
        System.out.println(file.getAbsolutePath());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(fileContent.toString().getBytes());
        System.out.println(fileContent);
        System.out.println("----------");

    }

    @org.junit.Test
    public void chinapayTest2() {
        String url = "http://sfj-test.chinapay.com/dac/SinPayServletGBK";
        String merId = "808080211881410";
        SimpleDateFormat format = new SimpleDateFormat("YYYYMMdd");
        String merDate = format.format(new Date());
        String merSeqId = "21";
        String cardNo = "6210984280001561104";
        String usrName = "李圆123";
        String openBank = "邮政";
        String prov = "";
        String city = "";
        String transAmt = "101";
        String purpose = "提现";
        String version = "20151207";
        String signFlag = "1";
        String termType = "7";
        String s = merId + merDate + merSeqId + cardNo + usrName + openBank + prov + city + transAmt + purpose + version;

        PrivateKey key = new PrivateKey();
        String path = "D:\\MerPrK_808080211881410_20171102154758.key";
//        String path = ApiApplication.chinaPayKeyPath;
        boolean buildOK = key.buildKey(merId, 0, path);
        if(!buildOK){
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
        params.add("merId",merId);
        params.add("merDate",merDate);
        System.out.println(merDate);
        params.add("merSeqId",merSeqId);
        params.add("cardNo",cardNo);
        params.add("usrName",usrName);
        params.add("openBank",openBank);
        params.add("prov",prov);
        params.add("city",city);
        params.add("transAmt",transAmt);
        params.add("purpose",purpose);
//        params.add("subBank",);
//        params.add("flag",);
        params.add("version",version);
        params.add("signFlag",signFlag);
        params.add("chkValue",sign);
        HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<>(params,headers);
        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String returnData = res.getBody();
        System.out.println(returnData);
        System.out.println(res);
    }


    @org.junit.Test
    public void chinapayTest3() {
        String url = "http://sfj-test.chinapay.com/dac/SinPayServletGBK";
        String merId = "808080211881410";
        SimpleDateFormat format = new SimpleDateFormat("YYYYMMdd");
        String merDate = format.format(new Date());
        String merSeqId = "103";
        String cardNo = "6212264000073350908";
        String usrName = "龙登辉";
        String openBank = "工商";
        String prov = "广东";
        String city = "深圳";
        String transAmt = "101";
        String purpose = "提现";
        String version = "20151207";
        String signFlag = "1";
        String termType = "7";
        String s = merId + merDate + merSeqId + cardNo + usrName + openBank + prov + city + transAmt + purpose + version;

        PrivateKey key = new PrivateKey();
        String path = "D:\\MerPrK_808080211881410_20171102154758.key";
//        String path = ApiApplication.chinaPayKeyPath;
        boolean buildOK = key.buildKey(merId, 0, path);
        if(!buildOK){
            System.out.println("没有找到私钥文件");
        }
        System.out.println(buildOK);
        SecureLink secureLink = new SecureLink(key);
        char[] encode = Base64.encode(s.getBytes());
        String sign = secureLink.Sign(new String(encode));

        boolean b = secureLink.verifyAuthToken(new String(encode), sign);
        System.out.println("sing result : "+b);
        System.out.println("------");
        System.out.println(sign);
        System.out.println("------");

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("merId",merId);
        params.add("merDate",merDate);
        System.out.println(merDate);
        params.add("merSeqId",merSeqId);
        params.add("cardNo",cardNo);
        params.add("usrName",usrName);
        params.add("openBank",openBank);
        params.add("prov",prov);
        params.add("city",city);
        params.add("transAmt",transAmt);
        params.add("purpose",purpose);
//        params.add("subBank",);
        params.add("flag","01");
        params.add("version",version);
        params.add("signFlag",signFlag);
        params.add("chkValue",sign);
        HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<>(params,headers);
        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String returnData = res.getBody();

        int start = returnData.indexOf("chkValue=")+9;
        String chkValue = returnData.substring(start, returnData.length());
        System.out.println("returnData : "+returnData);
        System.out.println("chkValue : "+chkValue);

        System.out.println(res);
    }


    @org.junit.Test
    public void cpTest3() throws UnsupportedEncodingException {
        String merId = "808080211881410";
        String merDate = "20171103";
        String merSeqId = "4";

        String version = "20090501";
        String signFlag = "1";
        String chkValue = merId+merDate+merSeqId+version;


        PrivateKey key = new PrivateKey();
//        String path = "D:\\MerPrK_808080211881410_20171102154758.key";
        String path = ApiApplication.chinaPayKeyPath;
        boolean buildOK = key.buildKey(merId, 0, path);
        if(!buildOK){
            System.out.println("没有找到私钥文件");
        }
        System.out.println(buildOK);
        SecureLink secureLink = new SecureLink(key);

        chkValue = new String(Base64.encode(chkValue.getBytes()));
        chkValue = secureLink.Sign(new String(chkValue));


        String url = "http://sfj-test.chinapay.com/dac/SinPayQueryServletGBK";


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
        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

        String gbk = new String(res.toString().getBytes("GBK"), "utf-8");
        System.out.println(gbk);

//        System.out.println(res);
    }

    @org.junit.Test
    public void test22(){
        Person person = new Person();
        person.name = "ldh";


    }

    class Person{
        String name;
    }

    @org.junit.Test
    public void getContent() {
        try {
            File file = new File("d:\\test.txt");
            FileInputStream fis = new FileInputStream(file);

            int len =0;
            byte[] bytes = new byte[1024];

            CloseShieldInputStream csContent = new CloseShieldInputStream(fis);
            StringBuilder sb = new StringBuilder();
            while ((len = fis.read(bytes)) != -1) {

                sb.append(new String(bytes));
            }
            System.out.println(sb.toString());


            byte[] bytes2 = new byte[1024];

            StringBuilder sb2 = new StringBuilder();
            len = 0;
            while ((len = csContent.read(bytes2)) != -1) {

                sb2.append(new String(bytes2));
            }
            System.out.println(sb2.toString());

        } catch (Exception e) {

        }
    }


    @org.junit.Test
    public void testStream() {

//        byte[] bytes = new byte[4];
//        ByteBuffer buffer = ByteBuffer.wrap(bytes);
//        buffer.putInt(0x1234);
//        System.out.println(Arrays.toString(bytes));
//        bytes[0] = 1;
//        buffer.flip();
//        System.out.println(buffer.get());

        ByteBuffer buffer = ByteBuffer.allocate(64);

        buffer.putInt(1);
        buffer.putInt(2);
        buffer.putInt(3);
        System.out.println("write mode");
        buffer.flip();
        System.out.println(buffer.limit());
        System.out.println(buffer.position());
        System.out.println(buffer.capacity());

        System.out.println("read mode");
        buffer.flip();
        System.out.println(buffer.limit());
        System.out.println(buffer.position());
        System.out.println(buffer.capacity());

        buffer.flip();
        System.out.println("----");
        int anInt = buffer.getInt();
        int anInt1 = buffer.getInt();
        System.out.println("read data");
        System.out.println(anInt);
        System.out.println(anInt1);
        System.out.println("read data");
    }


}
