package com.h9.api.service;

import com.h9.api.config.SysConfig;
import com.h9.api.model.vo.OrderCouponsVO;
import com.h9.api.model.vo.UserCouponVO;
import com.h9.api.model.vo.coupon.ShareVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.coupon.Coupon;
import com.h9.common.db.entity.coupon.CouponGoodsRelation;
import com.h9.common.db.entity.coupon.UserCoupon;
import com.h9.common.db.entity.order.Goods;
import com.h9.common.db.repo.CouponGoodsRelationRep;
import com.h9.common.db.repo.CouponRespository;
import com.h9.common.db.repo.GoodsReposiroty;
import com.h9.common.db.repo.UserCouponsRepository;
import org.apache.commons.collections.CollectionUtils;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.groups.ConvertGroup;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.h9.common.db.entity.coupon.UserCoupon.statusEnum.UN_USE;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:优惠券相关</p>
 *
 * @author LiYuan
 * @Date 2018/4/3
 */
@Service
public class CouponService {

    @Resource
    private CouponRespository couponRespository;

    @Resource
    private UserCouponsRepository userCouponsRepository;

    @Resource
    private CouponGoodsRelationRep couponGoodsRelationRep;
    @Resource
    private GoodsReposiroty goodsReposiroty;

    @Resource
    private SysConfig sysConfig;

    @Resource
    private RedisBean redisBean;

    private Logger logger = Logger.getLogger(this.getClass());


    /**
     * 用户优惠券
     */
    @Transactional
    public Result getUserCoupons(Long userId, Integer state, Integer page, Integer limit) {

        if (state > 3 || state < 1) {
            return Result.fail("请选择正确的状态");
        }
        Integer ints = userCouponsRepository.updateTimeOut(userId, UserCoupon.statusEnum.TIMEOUT.getCode(), new Date()
                , UserCoupon.statusEnum.UN_USE.getCode());
        logger.info("更新 " + ints + " 条记录");
        PageResult<UserCoupon> pageResult = userCouponsRepository.findState(userId, state, page, limit);

        if (pageResult == null) {
            return Result.fail("暂无可用优惠券");
        }
        return Result.success(pageResult.result2Result(userCoupon -> {

            CouponGoodsRelation couponGoodsRelation = couponGoodsRelationRep.findByCouponIdFirst(userCoupon.getCoupon().getId(), 0);
            Goods goods = null;
            if (couponGoodsRelation != null) {
                Long goodsId = couponGoodsRelation.getGoodsId();
                goods = goodsReposiroty.findOne(goodsId);
            }

            UserCouponVO userCouponVO = new UserCouponVO(userCoupon, goods);
            return userCouponVO;

        }));
    }

    @Transactional
    public Result getOrderCoupons(Long userId, Long goodsId) {

        List<UserCoupon> userCouponList = userCouponsRepository.findByUserId(userId, 1);

        userCouponList = userCouponList.stream().filter(userCoupon -> {
            Coupon coupon = userCoupon.getCoupon();
            Date startTime = coupon.getStartTime();
            Date endTime = coupon.getEndTime();
            Date now = new Date();
            if (startTime.after(now)) {
                return false;
            }
            if (now.after(endTime)) {
                return false;
            }
            int state = userCoupon.getState();
            if (state != UN_USE.getCode()) {
                return false;
            }
            List<CouponGoodsRelation> relations = couponGoodsRelationRep.findByCouponId(userCoupon.getCoupon().getId(), 0);
            if (CollectionUtils.isNotEmpty(relations)) {
                Long gid = relations.get(0).getGoodsId();
                return gid.equals(goodsId);
            }
            return false;
        }).collect(Collectors.toList());


        if (CollectionUtils.isEmpty(userCouponList)) {

            return Result.success("暂无可用优惠券");
        }

        List<OrderCouponsVO> vo = userCouponList.stream().map(userCoupon -> {
            Goods goods = goodsReposiroty.findOne(goodsId);
            return new OrderCouponsVO(userCoupon, goods);
        }).collect(Collectors.toList());

        return Result.success(vo);
    }

    public Result sendCoupon(Long userId, Long userCouponId) {
        UserCoupon userCoupon = userCouponsRepository.findOne(userCouponId);
        if (userCoupon == null) {
            return Result.fail("优惠劵不存在");
        }
        List<CouponGoodsRelation> couponGoodsRelations = couponGoodsRelationRep.findByCouponId(userCoupon.getCoupon().getId(), 0);
        if (CollectionUtils.isNotEmpty(couponGoodsRelations)) {
            CouponGoodsRelation couponGoodsRelation = couponGoodsRelations.get(0);
            if (!couponCanUse(userCoupon)) {
                return Result.fail("此优惠劵不能使用");
            }
            if (!userId.equals(userCoupon.getUserId())) {
                return Result.fail("无权操作");
            }
            String host = sysConfig.getString("path.app.wechat_host");
            Goods goods = goodsReposiroty.findOne(couponGoodsRelation.getGoodsId());
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String key = RedisKey.getUuid2couponIdKey(uuid);
            redisBean.setStringValue(key, userCouponId + "", 3, TimeUnit.DAYS);
            String url = "/#/shopDataile?id=" + goods.getId() + "&coupon=" + uuid + "&goodsName=" + userCoupon.getGoodsName();

            try {
                url = URLEncoder.encode(url, "utf-8");
            } catch (UnsupportedEncodingException e) {
                logger.info(e.getMessage(), e);
            }
            url = host + url;
            ShareVO vo = new ShareVO(userCoupon, goods, url, "");
            return Result.success(vo);
        } else {
            return Result.fail("商品异常");
        }

    }

    /**
     * 优惠劵能否被使用
     *
     * @return
     */
    public boolean couponCanUse(Long userCouponId) {

        UserCoupon userCoupon = userCouponsRepository.findOne(userCouponId);
        if (userCoupon == null) {
            return false;
        }
        return couponCanUse(userCoupon);
    }

    /**
     * 优惠劵能否被使用
     *
     * @return
     */
    public boolean couponCanUse(UserCoupon userCoupon) {
        if (userCoupon == null) {
            return false;
        }
        Integer state = userCoupon.getState();

        if (state == UserCoupon.statusEnum.UN_USE.getCode()) {
            Coupon coupon = userCoupon.getCoupon();
            Date endTime = coupon.getEndTime();
            Date startTime = coupon.getStartTime();
            Date now = new Date();
            if (startTime.after(now)) {
                return false;
            }

            if (now.after(endTime)) {
                return false;
            }
        }

        return true;
    }
}
