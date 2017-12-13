package com.transfer;

import com.h9.common.db.bean.RedisBean;
import com.transfer.db.entity.BounsDetails;
import com.transfer.db.entity.Oratrans;
import com.transfer.db.model.OratransWrap;
import com.transfer.db.repo.BounsDetailsRepository;
import com.transfer.db.repo.OratransRepository;
import com.transfer.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SqlTests {

    private Logger logger = Logger.getLogger(SqlTests.class);
    @Resource
    private UserService userService;
    @Resource
    private AddressService addressService;
    @Resource
    private IntegralRecordService integralRecordService;
    @Resource
    private BounsDetailService bounsDetailService;
    @Resource
    private OratransSerivce oratransSerivce;

    @Test
    public void user() {
        logger.debugv(" ----*************###############+++++++++++++++++++");
        userService.user();
        logger.debugv(" ----*************###############+++++++++++++++++++");
    }

    @Test
    public void transferAddress() {
        addressService.transfernAddress();
    }

    @Test
    public void transferVcoins() {
        integralRecordService.trants();
    }

    @Test
    public void transferOratrans() {
        oratransSerivce.trants();
    }

    @Test
    public void transferBouns(){
        bounsDetailService.trants();
    }



    @Resource
    private OratransRepository oratransRepository;

    @Test
    public void test() {
        Page<OratransWrap> pageQuery = oratransRepository.findPageQuery(0, 1);
        List<OratransWrap> oratransWrapList = pageQuery.getContent();
        OratransWrap oratransWrap = oratransWrapList.get(0);
        Oratrans oratrans = oratransWrap.getOratrans();
        BounsDetails bounsDetails = oratransWrap.getBounsDetails();
        System.out.println(oratrans.getOratransOId());
        System.out.println(bounsDetails.getOratransOId());
    }

    @Resource
    private BounsDetailsRepository bounsDetailsRepository;

    @Resource
    private RedisBean redisBean;
    @Test
    public void test2() {
//        OratransWrap byOId = oratransRepository.findByOId("8214B5FA-84F6-473A-B007-00191AECC79E");
//        System.out.println(byOId);
        int page = 0;
        int size = 10000;
        Page<BounsDetails> findPage = bounsDetailsRepository.findAll(new PageRequest(page, size));
        while (CollectionUtils.isNotEmpty(findPage.getContent())) {
            page++;
            findPage = bounsDetailsRepository.findAll(new PageRequest(page, size));
            List<BounsDetails> bounsDetailsList = findPage.getContent();
            int listSize = bounsDetailsList.size();
            for(int i = 0;i < listSize ;i++) {
                BounsDetails bounsDetails = bounsDetailsList.get(i);
                Long userid = bounsDetails.getUserid();
                String oratransOId = bounsDetails.getOratransOId();
                String key  = "h9:transfer:data:"+oratransOId;
                redisBean.setStringValue(key, userid + "");
            }
            logger.info("page : "+page + " total : "+findPage.getTotalPages());
        }

    }

    @Test
    public void test4(){
        long l1 = System.currentTimeMillis();
        BounsDetails bounsDetails = bounsDetailsRepository.findByOAndBounsOID("5AF1FD83-2042-4587-A8E6-00001808C6F2");
        long l2 = System.currentTimeMillis();
        logger.info("time : "+ (l2 - l1));
    }
}
