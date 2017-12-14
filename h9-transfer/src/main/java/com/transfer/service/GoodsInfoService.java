package com.transfer.service;

import com.h9.common.base.PageResult;
import com.h9.common.db.repo.GoodsReposiroty;
import com.h9.common.db.repo.GoodsTypeReposiroty;
import com.transfer.db.entity.GoodsInfo;
import com.transfer.db.repo.GoodsInfoRepository;
import com.transfer.service.base.BaseService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:商品信息
 * GoodsInfoService:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/5
 * Time: 13:59
 */
@Component
public class GoodsInfoService extends BaseService<GoodsInfo>{

    private boolean writeGoodsType = false;
    @Resource
    private GoodsInfoRepository goodsInfoRepository;

    @Resource
    private GoodsReposiroty goodsReposiroty;
    @Resource
    private GoodsTypeReposiroty goodsTypeReposiroty;

    @Override
    public String getPath() {
        return "./sql/goodsInfo.sql";
    }

    @Override
    public PageResult get(int page, int limit) {
        //TODO
        return goodsInfoRepository.findAll(page,limit);
    }

    @Override
    public void getSql(GoodsInfo goodsInfo, BufferedWriter writer) throws IOException {
//        Goods goods = new Goods();
//        goods.setName(goodsInfo.getGoodsname());
//        goods.setImg(goodsInfo.getPicture_URL());
//        String idCode = goodsInfo.getIdCode();
//       if(StringUtils.isEmpty(idCode)){
//            goods.setCode(UUID.randomUUID().toString());
//       }else{
//           goods.setCode(idCode);
//       }
//        BigDecimal price = goodsInfo.getPrice();
//        if(price!=null){
//            goods.setPrice(price);
//            goods.setRealPrice(price);
//        }
//        GoodsType goodsType = goodsTypeReposiroty.findByCode(EVERYDAY_GOODS.getCode());
//        goods.setGoodsType(goodsType);
//        goodsReposiroty.saveAndFlush(goods);
        //转的记录固定统一到一个商品分类下
        if (!writeGoodsType) {
            String goodsTypeSql = "INSERT INTO `goods_type` (`id`, `create_time`, `update_time`," +
                    " `code`, `name`, `parent_id`, `status`, `allow_import`) VALUES ('1000', '2017-10-31 18:02:36'," +
                    " '2017-12-06 15:33:08', 'old_goods', '旧商品', '1', '0', '0');\n";
            writer.write(goodsTypeSql);
        }

        writeGoodsType = true;


        StringBuilder sql = new StringBuilder("INSERT INTO `goods` (`id`, `create_time`, `update_time`, `cash_back`, " +
                "`description`, `name`, `price`, `real_price`, `status`, `stock`, " +
                "`goods_type_id`, `end_time`, `img`, `start_time`, `v_coins_rate`, `code`) VALUES (\n");


        insertFieldInMid(sql,goodsInfo.getGoodsid());
        insertFieldInMid(sql, new Date());
        insertFieldInMid(sql, new Date());
        insertFieldInMid(sql, 0);
        insertFieldInMid(sql, goodsInfo.getGoodsText());
        insertFieldInMid(sql, goodsInfo.getGoodsname());
        insertFieldInMid(sql, goodsInfo.getPrice());
        insertFieldInMid(sql, goodsInfo.getPrice());
        insertFieldInMid(sql, 0);
        insertFieldInMid(sql, 0);
        insertFieldInMid(sql, 1000);
        insertFieldInMid(sql, goodsInfo.getEndtime());
        insertFieldInMid(sql, goodsInfo.getPicture_URL());
        insertFieldInMid(sql, goodsInfo.getStartTime());
        insertFieldInMid(sql, 0);
        insertFieldEnd(sql, goodsInfo.getIdCode());


        sql.append(");\n");

        writer.write(sql.toString());
        writer.flush();

    }



    @Override
    public String getTitle() {
        return "商品信息迁移进度";
    }
}
