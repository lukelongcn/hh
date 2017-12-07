package com.transfer.service;

import com.h9.common.base.PageResult;
import com.h9.common.db.entity.Goods;
import com.h9.common.db.entity.User;
import com.h9.common.db.repo.GoodsReposiroty;
import com.h9.common.db.repo.UserRepository;
import com.h9.common.utils.DateUtil;
import com.transfer.db.entity.GoodsOrder;
import com.transfer.db.repo.GoodsOrderRepository;
import com.transfer.service.base.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by itservice on 2017/12/6.
 */
@Service
public class OrderService extends BaseService<GoodsOrder> {

    @Resource
    private GoodsOrderRepository goodsOrderRepository;
    @Resource
    private GoodsReposiroty goodsReposiroty;
    @Resource
    private UserRepository userRepository;

    private List<Goods> mobileMoneyGoods = new ArrayList<>();

    @Override
    public String getPath() {
        return "./goodsOrder.sql";
    }

    @Override
    public PageResult get(int page, int limit) {
        return goodsOrderRepository.findAll(page, limit);
    }

    @Override
    public void getSql(GoodsOrder goodsOrder, BufferedWriter writer) throws IOException {

        if(mobileMoneyGoods.size() < 6){
            mobileMoneyGoods.add(goodsReposiroty.findOne(57L));
            mobileMoneyGoods.add(goodsReposiroty.findOne(59L));
            mobileMoneyGoods.add(goodsReposiroty.findOne(60L));
            mobileMoneyGoods.add(goodsReposiroty.findOne(61L));
            mobileMoneyGoods.add(goodsReposiroty.findOne(62L));
            mobileMoneyGoods.add(goodsReposiroty.findOne(63L));
        }

        StringBuilder sql = new StringBuilder("INSERT into orders(id,user_id,create_time,address_id" +
                ",express_name,user_phone,user_name,express_num,province,city,district,money,pay_money,pay_status,status) values(");
        //TODO 订单状态没有转过来，不确定是什么是意思
        sql.append("'");
        sql.append(goodsOrder.getId());
        sql.append("',");

        sql.append("'");
        sql.append(goodsOrder.getUserId());
        sql.append("',");

        sql.append("'");
        sql.append(goodsOrder.getOrderDate());
        sql.append("',");

        sql.append("'");
        Long addressId = goodsOrder.getAddressId();
        if (addressId != null) {
            sql.append(addressId);
        }
        sql.append("',");

        sql.append("'");
        String expressName = goodsOrder.getExpressName();
        if (expressName != null) {
            sql.append(expressName);
        }
        sql.append("',");

        sql.append("'");
        String orderPhone = goodsOrder.getOrderPhone();
        if (orderPhone != null) {
            sql.append(orderPhone);
        }
        sql.append("',");

        sql.append("'");
        User user = userRepository.findOne(goodsOrder.getUserId());
        if (user != null) {
            sql.append(user.getNickName());
        }
        sql.append("',");

        sql.append("'");
        String exNum = goodsOrder.getExNum();
        if (exNum != null) {
            sql.append(exNum);
        }
        sql.append("',");

        sql.append("'");
        String province = goodsOrder.getProvince();
        if (province != null) {
            sql.append(province);
        }
        sql.append("',");

        sql.append("'");
        String city = goodsOrder.getCity();
        if (city != null) {
            sql.append(city);
        }
        sql.append("',");

        sql.append("'");
        String distic = goodsOrder.getDistrict();
        if (distic != null) {
            sql.append(distic);
        }
        sql.append("',");

        sql.append("'");
        Long goodsId = goodsOrder.getGoodsId();
        //goodsId 为空是充话费的订单
        Goods goods = null;
        if (goodsId != null) {
            goods = goodsReposiroty.findOne(goodsId);
            if (goods != null) {
                BigDecimal price = goods.getPrice();
                if (price != null) {
                    sql.append(price);
                }
            }
        }else{

            for(Goods tempGoods : mobileMoneyGoods){
                if (tempGoods.getName().equals(goodsOrder.getGoodsName())) {
                    goods = tempGoods;
                    break;
                }
            }
        }
        sql.append("',");

        sql.append("'");
        if (goods != null) {
            BigDecimal realPrice = goods.getRealPrice();
            if (realPrice != null) {
                sql.append(realPrice);
            }
        }
        sql.append("',");

        sql.append("'");
        sql.append("1");
        sql.append("',");

        sql.append(goodsOrder.getOrderState());

        sql.append(");");
        sql.append("\n");

        writer.write(sql.toString());

        //处理订单项表
        StrBuilder itemSql = new StrBuilder("insert into order_items(create_time,count,didi_card_number,image,money,name,price,goods_id,orders_id) values(");

    //create_time
        itemSql.append("'");
        Date orderDate = goodsOrder.getOrderDate();
        if (orderDate != null) {
            itemSql.append(orderDate);
        }
        itemSql.append("',");

//count
        Integer count = goodsOrder.getExpressNum();
        if (count == null) count = 1;
        itemSql.append(count);
        itemSql.append(",");

        //didi_card_number
        itemSql.append("'");
        itemSql.append("',");

        //image
        itemSql.append("'");
        if(goods != null){
            String img = goods.getImg();
            itemSql.append(img);
        }
        itemSql.append("',");
        //money
        itemSql.append("'");
        if(goods != null){
            itemSql.append(goods.getRealPrice());
        }
        itemSql.append("',");
        //name
        itemSql.append("'");
        if(goods != null){
            itemSql.append(goods.getName());
        }
        itemSql.append("',");
        //price
        itemSql.append("'");
        if(goods != null){
            itemSql.append(goods.getRealPrice());
        }
        itemSql.append("',");

        itemSql.append("'");
        if(goods != null){
            itemSql.append(goods.getId());
        }
        itemSql.append("',");

        itemSql.append("'");
        itemSql.append(goodsOrder.getId());
        itemSql.append("'");

        itemSql.append(");\n");
        writer.write(itemSql.toString());

    }

    @Override
    public String getTitle() {
        return "--------------------->>>>>>>>>>>>>>>>>>>>>>>>";
    }
}
