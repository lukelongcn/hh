package com.h9.admin;

import com.h9.admin.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
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
}
