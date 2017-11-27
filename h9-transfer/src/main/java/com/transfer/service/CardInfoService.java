package com.transfer.service;

import com.h9.common.db.entity.BankType;
import com.h9.common.db.repo.BankTypeRepository;
import com.h9.common.db.repo.UserBankRepository;
import com.transfer.db.entity.CardInfo;
import com.transfer.db.entity.City;
import com.transfer.db.entity.Province;
import com.transfer.db.repo.CardInfoRepository;
import com.transfer.db.repo.CityReposiroty;
import com.transfer.db.repo.ProvinceReposiroty;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itservice on 2017/11/24.
 */
@SuppressWarnings("Duplicates")
@Service
public class CardInfoService {

    public String basePath = "d://sql/";
    @Resource
    private CardInfoRepository cardInfoRepository;

    @Resource
    private CityReposiroty cityReposiroty;

    @Resource
    private ProvinceReposiroty provinceReposiroty;

    @Resource
    private BankTypeRepository bankTypeRepository;
    @Resource
    private UserBankRepository userBankRepository;

    StringBuilder sql = new StringBuilder();

    private List<String> bankList = new ArrayList<>();

    public void readBanTypePage() throws IOException {

        int size = 5000;
        int page = 0;

        File dirFile = new File(basePath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        File bankTypeSqlFile = new File(basePath + "/bankType.sql");

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(bankTypeSqlFile));

        Pageable pageable = new PageRequest(page, size);
        Page<CardInfo> cardInfoList = cardInfoRepository.findAll(pageable);
        System.out.println("page: " + page + " size: " + size + " totalPage: " + cardInfoList.getTotalPages());
        while (!CollectionUtils.isEmpty(cardInfoList.getContent())) {
            cardInfoList = cardInfoRepository.findAll(pageable);

            int forSize = cardInfoList.getContent().size();
            for (int i = 0; i < forSize; i++) {
                if (i % 1000 == 0) {
                    System.out.println("index :" + i);
                }
                CardInfo el = cardInfoList.getContent().get(i);
                if (org.apache.commons.lang3.StringUtils.isBlank(el.getBankName())) {
                    continue;
                }

                String findBankName = el.getBankName();
                String bankName = findBankName.substring(0, 2);

                boolean findResult = bankList.stream().anyMatch(b -> b.equals(bankName));
                if (findResult) {

                } else {
                    bankList.add(bankName);
                    writebankTypeTosql(el, bufferedWriter);
                }

//                List<BankType> bankTypeList = bankTypeRepository.findByBankNameContaining(bankName);

                BankType bankType = null;

//
//                Province province = provinceReposiroty.findByPid(el.getProvince());
//                City city = cityReposiroty.findByCid(el.getCity());
//
//                String provinceStr = province == null ? province.getName() : "";
//                String cityStr = city == null ? city.getCname() : "";
//
//                writeUserBankToSql(el, userBankBW, provinceStr, cityStr,bankType.getId());


            }

            page++;
            System.out.println("page: " + page + " size: " + size + " totalPage: " + cardInfoList.getTotalPages());
            pageable = new PageRequest(page, size);
        }
        System.out.println("end-------------------");
    }

    public void readBankINfo() throws IOException {

        int size = 5000;
        int page = 0;

        File dirFile = new File(basePath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        File userBankSqlFile = new File(basePath, "/userBank.sql");

        BufferedWriter userBankBW = new BufferedWriter(new FileWriter(userBankSqlFile));

        Pageable pageable = new PageRequest(page, size);
        Page<CardInfo> cardInfoList = cardInfoRepository.findAll(pageable);
        System.out.println("page: " + page + " size: " + size + " totalPage: " + cardInfoList.getTotalPages());
        while (!CollectionUtils.isEmpty(cardInfoList.getContent())) {
            long queryStart = System.currentTimeMillis();
            cardInfoList = cardInfoRepository.findAll(pageable);
            System.out.println("db query1 time : " + (System.currentTimeMillis() - queryStart));

            int forSize = cardInfoList.getContent().size();
            for (int i = 0; i < forSize; i++) {
                long start = System.currentTimeMillis();
                if (i % 1000 == 0) {
                    System.out.println("index :" + i);
                }
                CardInfo el = cardInfoList.getContent().get(i);
                if (org.apache.commons.lang3.StringUtils.isBlank(el.getBankName())) {
                    continue;
                }

                String findBankName = el.getBankName();
                String bankName = findBankName.substring(0, 2);

                queryStart = System.currentTimeMillis();
                List<BankType> bankTypeList = bankTypeRepository.findByBankNameContaining(bankName);
                System.out.println("db query2 time : " + (System.currentTimeMillis() - queryStart));

                BankType bankType = bankTypeList.get(0);

                Province province = provinceReposiroty.findByPid(el.getProvince());
                City city = cityReposiroty.findByCid(el.getCity());

                String provinceStr = province == null ? province.getName() : "";
                String cityStr = city == null ? city.getCname() : "";
                boolean writeAble = false;
                if (i == forSize) {
                    writeAble = true;
                }
                if (i % 1000 == 0) {
                    writeAble = true;
                }
                System.out.println("前半：" + (System.currentTimeMillis() - start));
                start = System.currentTimeMillis();
                writeUserBankToSql(el, userBankBW, provinceStr, cityStr, bankType.getId(), writeAble);
                System.out.println("后半：" + (System.currentTimeMillis() - start));


            }

            page++;
            System.out.println("page: " + page + " size: " + size + " totalPage: " + cardInfoList.getTotalPages());
            pageable = new PageRequest(page, size);
        }
        System.out.println("end-------------------");
    }


    private BankType addNewBankType(CardInfo el) {
        BankType bankType = new BankType();
        bankType.setStatus(1);
        bankType.setColor("#C65055");
        bankType.setBankImg(el.getBankImg());
        bankType.setBankName(el.getBankName());
        return bankTypeRepository.saveAndFlush(bankType);
    }

    public void writeUserBankToSql(CardInfo el, BufferedWriter bufferedWriter, String province, String city, Long bankTypeId, boolean writeAble) {
        //INSERT INTO `user_bank`(city,default_select,name,no,provice,status,user_id,bank_type_id,card_id) VALUES ( '赣州','1', '李圆', '621321321312', '江西',  '1', '3', '43', '1');

        sql.append("INSERT INTO `user_bank`(city,default_select,name,no,provice,status,user_id,bank_type_id,card_id) VALUES ( '");
        sql.append("'" + city + "','0',");
        sql.append("'" + el.getCardName() + "','");
        sql.append("'");
        sql.append(el.getAccount());
        sql.append("',");

        sql.append("'");
        sql.append(province);
        sql.append("',");

        sql.append("'");
        sql.append("1");
        sql.append("',");

        sql.append("'");
        sql.append(el.getUserid());
        sql.append("',");


        sql.append("'");
        sql.append(bankTypeId);
        sql.append("',");

        sql.append("'");
        sql.append(el.getCardId());
        sql.append("');");
        sql.append("\n\r");

        if (writeAble) {
            try {
                bufferedWriter.write(sql.toString());
                bufferedWriter.flush();
                sql = new StringBuilder();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void writebankTypeTosql(CardInfo el, BufferedWriter bufferedWriter) {

        StringBuilder sql = new StringBuilder("INSERT INTO `bank_type`(color,bank_name,back_img,status) VALUES ('#C65055', '");
        sql.append(el.getBankName());
        sql.append("',");
        sql.append("'");
        sql.append(el.getBankImg());
        sql.append("'");

        sql.append(",'1');");
        sql.append("\n\r");
        try {
            bufferedWriter.write(sql.toString());
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public <T> void readPage(CallBackService callBack) {

        int size = 1000;
        int page = 0;

        Pageable pageable = new PageRequest(page, size);
//        String simpleName = content.getClass().getSimpleName();
//        String first = simpleName.substring(0, 1);
//        String convertfirst = first.toUpperCase();
//        String reposirotyClass = simpleName.replace("first", convertfirst);

        Page<T> cardInfoList = (Page<T>) cardInfoRepository.findAll(pageable);
        System.out.println("page: " + page + " size: " + size + " totalPage: " + cardInfoList.getTotalPages());

        while (!CollectionUtils.isEmpty(cardInfoList.getContent())) {
            cardInfoList = (Page<T>) cardInfoRepository.findAll(pageable);
            System.out.println("page: " + page + " size: " + size + " totalPage: " + cardInfoList.getTotalPages());
            callBack.doThing();
            page++;
            pageable = new PageRequest(page, size);
        }
        System.out.println("end-------------------");
    }


}
