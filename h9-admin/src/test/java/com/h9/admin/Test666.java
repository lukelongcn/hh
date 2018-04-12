package com.h9.admin;

import com.h9.admin.job.HotelOrderJob;
import com.h9.admin.model.dto.AddBigRichDTO;
import com.h9.admin.service.AccountService;
import com.h9.common.db.entity.lottery.OrdersLotteryActivity;
import com.h9.common.db.repo.OrdersLotteryActivityRepository;
import com.h9.common.utils.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Gonyb on 2017/11/11.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test666 {
    @Resource
    private AccountService accountService;

    @Test
    public void contextLoads() {
        accountService.deviceIdInfo(new Date(0), new Date());
    }

    /*public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        for (String item : list) {
            if ("2".equals(item)) {
                list.remove(item);
            }
        }
    }*/
    @Test
    public void testRefund() throws IOException {
//        InputStream is = this.getClass().getClassLoader().getResourceAsStream("apiclient_cert_wxjs.p12");
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("test.txt");

//        File file = new File("d://test/test.p12");
        File file = new File("d://test/test.txt");

        if (!file.exists()) {
            file.createNewFile();
        }
        System.out.println("path :"+file.getAbsolutePath());
        FileOutputStream fos = new FileOutputStream(file);
        int len = 0;
        byte[] bytes = new byte[1024];

        while(( len = is.read(bytes)) != -1){
            fos.write(bytes, 0, len);
        }
        System.out.println("read finish!");
    }

    @Resource
    private HotelOrderJob job;

    @Test
    public void testTask(){
        job.scan();
    }



    @Resource
    OrdersLotteryActivityRepository ordersLotteryActivityRepository;

    @Test
    public void addBigRichActivity() {
        for (int i= 0;i<48;i++){
            AddBigRichDTO addBigRichDTO1 = new AddBigRichDTO();
            addBigRichDTO1.setEndTime(20180102L).setStartLotteryTime(20180102L).setStartTime(20180102L).setStatus(1);
            OrdersLotteryActivity ordersLotteryActivity = getOrdersLotteryActivity(addBigRichDTO1);
            ordersLotteryActivityRepository.save(ordersLotteryActivity);
        }
    }

    public OrdersLotteryActivity getOrdersLotteryActivity(AddBigRichDTO addBigRichDTO) {

        OrdersLotteryActivity ordersLotteryActivity = new OrdersLotteryActivity();
        ordersLotteryActivity.setStartTime(new Date(addBigRichDTO.getStartTime()));
        ordersLotteryActivity.setEndTime(new Date(addBigRichDTO.getEndTime()));
        Long startLotteryTime = addBigRichDTO.getStartLotteryTime();
        String number = DateUtil.formatDate(new Date(startLotteryTime), DateUtil.FormatType.NON_SEPARATOR_DAY);
        ordersLotteryActivity.setNumber(number);
        ordersLotteryActivity.setStatus(addBigRichDTO.getStatus());
        ordersLotteryActivity.setStartLotteryTime(new Date(startLotteryTime));

        return ordersLotteryActivity;
    }

}
