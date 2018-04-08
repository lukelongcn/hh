package com.h9.admin.service;

import com.h9.admin.model.dto.CouponsDTO;
import com.h9.admin.model.vo.CouponVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.order.Coupon;
import com.h9.common.db.entity.order.Goods;
import com.h9.common.db.repo.CouponRespository;
import com.h9.common.db.repo.GoodsReposiroty;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:</p>
 *
 * @author LiYuan
 * @Date 2018/4/8
 */
@Service
public class CouponService {
    @Resource
    private CouponRespository couponRespository;
    @Resource
    private GoodsReposiroty goodsReposiroty;

    public Result coupons(Integer page, Integer limit) {
        PageResult<Coupon> pageResult = couponRespository.findAll(page, limit);
        if (pageResult == null) {
            return Result.fail("暂无记录");
        }
        return Result.success(pageResult.result2Result(CouponVO::new));
    }

    public Result addCoupons(CouponsDTO couponsDTO) {
        List<Long> goodsIdList = couponsDTO.getGoodIdList();
        if (CollectionUtils.isEmpty(goodsIdList)){
            return Result.fail("新增优惠券失败");
        }
        goodsIdList.forEach(gid->{
            Coupon coupon = new Coupon();
            coupon.setTitle(couponsDTO.getTitle());
            coupon.setCouponType(couponsDTO.getCouponType());
            coupon.setStartTime(couponsDTO.getStartTime());
            coupon.setEndTime(couponsDTO.getEndTime());
            Goods goods = goodsReposiroty.findOne(gid);
            if (goods != null){
                coupon.setGoodsId(goods);
            }
            // 制券数
            coupon.setLeftCount(couponsDTO.getAskCount());
            // 状态
            if (couponsDTO.getStartTime().after(new Date())){
                coupon.setStatus(1);
            } else if (couponsDTO.getEndTime().after(new Date())){
                coupon.setStatus(2);
            } else {
                coupon.setStatus(3);
            }
            couponRespository.saveAndFlush(coupon);
        });
        return Result.success("新增优惠券成功");
    }
}
