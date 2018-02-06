package com.h9;

import com.alibaba.fastjson.JSONObject;
import com.h9.common.base.Result;
import com.h9.common.db.repo.GoodsReposiroty;
import com.h9.common.db.repo.OrderItemReposiroty;
import com.h9.store.service.GoodService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class H9StoreApplicationTests {

	@Value("${server.context-path}")
	private String serverUrl;
	@Value("${server.port}")
	private String port;
	private String host = "http://localhost:";
	@Resource
	private OrderItemReposiroty orderItemReposiroty;
	@Resource
	private GoodsReposiroty goodsReposiroty;
	@Test
	public void test() {
//		List<Goods> lastConvertGoods = goodsReposiroty.findLastConvertGoods();
//		System.out.println(lastConvertGoods);
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
