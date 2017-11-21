package com.transfer.service;

import com.h9.common.base.PageResult;
import com.h9.common.db.entity.Goods;
import com.h9.common.db.entity.GoodsDIDINumber;
import com.h9.common.db.entity.GoodsType;
import com.h9.common.db.entity.Orders;
import com.h9.common.db.repo.*;
import com.transfer.SqlUtils;
import com.transfer.db.entity.C_Cards;
import com.transfer.db.entity.T_CardCodes;
import com.transfer.db.repo.C_CardsRepository;
import com.transfer.db.repo.FDidiCardRepository;
import org.jboss.logging.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:地点卡卷处理
 * DiDiCard:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/21
 * Time: 10:01
 */
@Service
public class DiDiCardService {
    @Resource
    private FDidiCardRepository fDidiCardRepository;
    Logger logger = Logger.getLogger(DiDiCardService.class);
    @Resource
    private GoodsDIDINumberRepository goodsDIDINumberRepository;

    @Resource
    private GoodsReposiroty goodsReposiroty;
    @Resource
    private GoodsTypeReposiroty goodsTypeReposiroty;

    @Transactional
    public void transferDidiCard() {
        int page = 0;
        int limit = 1000;
        int totalPage = 0;
        PageResult<T_CardCodes> userInfoPageResult;
        Sort sort = new Sort(Sort.Direction.ASC, "ID");
        do {
            page = page + 1;
            userInfoPageResult = fDidiCardRepository.findAll(page, limit, sort);
            totalPage = (int) userInfoPageResult.getTotalPage();
            List<T_CardCodes> userInfos = userInfoPageResult.getData();
            for (T_CardCodes userInfo : userInfos) {
//                  String sql = toMyAddressAndSave(userInfo);
                toDidi(userInfo);
            }
            logger.info("page: "+page+" totalPage: "+totalPage);
            float rate = (float) page * 100 / (float) totalPage;
            if (page <= totalPage && userInfoPageResult.getCount() != 0)
                logger.debugv("滴滴卡券迁移进度 " + rate + "% " + page + "/" + totalPage);
        } while (page <= totalPage &&userInfoPageResult.getCount() != 0);
    }

    public void toDidi(T_CardCodes userInfo ){
//
        GoodsDIDINumber didiNumber = goodsDIDINumberRepository.findByGoodsAndDidiNumber(userInfo.getCardCode());
        if (didiNumber != null) {
            return;
        }
        BigDecimal money = userInfo.getMoney();
        Goods goods = goodsReposiroty.findByTop1(money);
        if (goods == null) {
            goods = new Goods();
            goods.setName("滴滴"+money+"元代驾券");
            goods.setPrice(money);
            goods.setDescription("滴滴兑换券");
            goods.setRealPrice(money);
            goods.setStatus(1);
            GoodsType goodsType = goodsTypeReposiroty.findByCode(GoodsType.GoodsTypeEnum.DIDI_CARD.getCode());
            goods.setGoodsType(goodsType);
            goods.setImg("https://cdn-h9-img.thy360.com/FuQuxK79VYsa9cut_Uy6mxIXkE9e");
            goods.setCode("DIDI90001");
            goods = goodsReposiroty.saveAndFlush(goods);
        }
        didiNumber = new GoodsDIDINumber();
        didiNumber.setDidiNumber(userInfo.getCardCode());
        didiNumber.setGoodsId(goods.getId());
        didiNumber.setMoney(money);
        didiNumber.setOldId(userInfo.getID());
        didiNumber.setStatus(userInfo.getType() == 0?2:1);
        goodsDIDINumberRepository.save(didiNumber);
    }


    @Resource
    private C_CardsRepository c_cardsRepository;

    public void userCard(){
        int page = 0;
        int limit = 1000;
        int totalPage = 0;
        PageResult<C_Cards> userInfoPageResult;
        Sort sort = new Sort(Sort.Direction.ASC, "ID");
        do {
            page = page + 1;
            userInfoPageResult = c_cardsRepository.findAll(page, limit, sort);
            totalPage = (int) userInfoPageResult.getTotalPage();
            List<C_Cards> userInfos = userInfoPageResult.getData();
            for (C_Cards userInfo : userInfos) {
                toUserOrder(userInfo);
            }
            logger.info("page: "+page+" totalPage: "+totalPage);
            float rate = (float) page * 100 / (float) totalPage;
            if (page <= totalPage && userInfoPageResult.getCount() != 0)
                logger.debugv("滴滴卡券迁移进度 " + rate + "% " + page + "/" + totalPage);
        } while (page <= totalPage &&userInfoPageResult.getCount() != 0);
    }


    @Resource
    private OrdersRepository ordersRepository;
    @Resource
    private OrderItemReposiroty orderItemReposiroty;



    public void toUserOrder(C_Cards c_cards){
        Orders orders = new Orders();
        orders.setMoney(c_cards.getCardPrice());
    }






}
