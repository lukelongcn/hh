package com.transfer.service;

import com.h9.common.base.PageResult;
import com.h9.common.db.entity.Goods;
import com.h9.common.db.entity.GoodsType;
import com.h9.common.db.repo.GoodsReposiroty;
import com.h9.common.db.repo.GoodsTypeReposiroty;
import com.transfer.db.entity.GoodsInfo;
import com.transfer.db.repo.GoodsInfoRepository;
import com.transfer.service.base.BaseService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

import static com.h9.common.db.entity.GoodsType.GoodsTypeEnum.EVERYDAY_GOODS;

/**
 * Created with IntelliJ IDEA.
 * Description:商品信息
 * GoodsInfoService:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/5
 * Time: 13:59
 */
@Component
public class GoodsInfoService extends BaseService<GoodsInfo>{


    @Resource
    private GoodsInfoRepository goodsInfoRepository;

    @Resource
    private GoodsReposiroty goodsReposiroty;
    @Resource
    private GoodsTypeReposiroty goodsTypeReposiroty;

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public PageResult get(int page, int limit) {
        //TODO
        return goodsInfoRepository.findAll(page,limit);
    }

    @Override
    public void getSql(GoodsInfo goodsInfo, BufferedWriter userWtriter) throws IOException {
        Goods goods = new Goods();
        goods.setName(goodsInfo.getGoodsname());
        goods.setImg(goodsInfo.getPicture_URL());
        String idCode = goodsInfo.getIdCode();
       if(StringUtils.isEmpty(idCode)){
            goods.setCode(UUID.randomUUID().toString());
       }else{
           goods.setCode(idCode);
       }
        BigDecimal price = goodsInfo.getPrice();
        if(price!=null){
            goods.setPrice(price);
            goods.setRealPrice(price);
        }
        GoodsType goodsType = goodsTypeReposiroty.findByCode(EVERYDAY_GOODS.getCode());
        goods.setGoodsType(goodsType);
        goodsReposiroty.saveAndFlush(goods);
    }

    @Override
    public String getTitle() {
        return "商品信息迁移进度";
    }
}
