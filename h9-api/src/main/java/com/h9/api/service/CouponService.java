package com.h9.api.service;

import com.h9.api.config.SysConfig;
import com.h9.api.model.vo.OrderCouponsVO;
import com.h9.api.model.vo.UserCouponVO;
import com.h9.api.model.vo.coupon.ShareVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.MyLock;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.coupon.Coupon;
import com.h9.common.db.entity.coupon.CouponGoodsRelation;
import com.h9.common.db.entity.coupon.CouponSendRecord;
import com.h9.common.db.entity.coupon.UserCoupon;
import com.h9.common.db.entity.order.Goods;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.repo.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

    @Resource
    private UserRepository userRepository;
    @Resource
    private CouponSendRecordRep couponSendRecordRep;
    @Resource
    private MyLock myLock;

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
            User user = userRepository.findOne(userId);

            CouponSendRecord couponSendRecord = new CouponSendRecord(null, user, null,
                    userCoupon, 0, null, null);

            couponSendRecordRep.save(couponSendRecord);
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
        String locKey = "coupon:lock:id:" + userCoupon.getId();
        String lockValue = this.myLock.getLock(locKey);
        if (StringUtils.isNotEmpty(lockValue)) {
            // 有人在操作这张劵
            return false;
        }
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

    public Result receiveCoupon(Long userId, String uuid) {

        String key = RedisKey.getUuid2couponIdKey(uuid);
        String userCouponId = redisBean.getStringValue(key);
        if (StringUtils.isEmpty(userCouponId)) {
            logger.info("key : " + key + " 在redis 中为空");
            return Result.fail("此优惠劵已被领取");
        }
        redisBean.setStringValue(key, "", 1, TimeUnit.MICROSECONDS);

        Long userCoupondIdLong = Long.valueOf(userCouponId);
        UserCoupon userCoupon = userCouponsRepository.findOne(userCoupondIdLong);
        if (userCoupon == null) {
            logger.info("userCouponId : " + userCouponId + " 在数据库中没有找对应记录");
            return Result.fail("此优惠劵已被领取");
        }

        if (!couponCanUse(userCoupon)) {
            logger.info("优惠劵Id " + userCoupon.getId() + " 不能使用,已过期或者已使用");
            return Result.fail("此优惠劵已被领取");
        }
        //对优惠劵加锁
        String locKey = "coupon:lock:id:" + userCouponId;
        myLock.lock(locKey, 1, TimeUnit.MINUTES);

        userCoupon.setUserId(userId);
        userCouponsRepository.save(userCoupon);
        myLock.unLock(locKey);

        return Result.success();
    }
}
