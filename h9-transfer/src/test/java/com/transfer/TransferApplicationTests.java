package com.transfer;

import com.alibaba.fastjson.JSONObject;
import com.h9.common.base.PageResult;
import com.transfer.db.entity.UserInfo;
import com.transfer.db.repo.UserInfoRepository;

import com.transfer.service.UserService;
import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransferApplicationTests {

	private Logger logger = Logger.getLogger(TransferApplicationTests.class);
	@Resource
	private UserService userService;

	@Test
	public void contextLoads() {
		logger.debugv(" ----*************###############+++++++++++++++++++");
		userService.user();

		logger.debugv(" ----*************###############+++++++++++++++++++");


	}

}
