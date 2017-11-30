package com.h9;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.h9.common.base.Result;
import com.h9.store.service.GoodService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class H9StoreApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Resource
	GoodService goodService;
	@Test
	public void testtodayNewGoods(){
		Result result = goodService.todayNewGoods();
		String s = JSONObject.toJSONString(result);
		System.out.println(s);
	}
}
