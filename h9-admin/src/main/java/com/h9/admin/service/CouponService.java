package com.h9.admin.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.h9.admin.model.dto.coupon.CouponsDTO;
import com.h9.admin.model.dto.coupon.CouponUserRelationDTO;
import com.h9.admin.model.vo.CouponVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.entity.coupon.Coupon;
import com.h9.common.db.entity.coupon.CouponGoodsRelation;
import com.h9.common.db.entity.order.Goods;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.coupon.UserCoupon;
import com.h9.common.db.repo.*;
import com.h9.common.utils.CheckoutUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.h9.common.db.entity.coupon.UserCoupon.statusEnum.UN_USE;

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
    @Resource
    private UserRepository userRepository;
    @Resource
    private POIService poiService;
    @Resource
    private UserCouponsRepository userCouponsRepository;
    @Resource
    private CouponGoodsRelationRep couponGoodsRelationRep;

    private Logger logger = Logger.getLogger(this.getClass());

    @Resource
    private RedisBean redisBean;

    public Result coupons(Integer page, Integer limit) {
        PageResult<Coupon> pageResult = couponRespository.findAll(page, limit);
        if (pageResult == null) {
            return Result.fail("暂无记录");
        }
        return Result.success(pageResult.result2Result(el -> {
            List<CouponGoodsRelation> list = couponGoodsRelationRep.findByCouponId(el.getId(), 0);
            CouponGoodsRelation couponGoodsRelation = couponGoodsRelationRep.findByCouponIdFirst(el.getId(), 0);
            Goods goods = null;
            if (couponGoodsRelation != null) {
                goods = goodsReposiroty.findOne(couponGoodsRelation.getGoodsId());
            }
            CouponVO couponVO = new CouponVO(el, goods);
            return couponVO;
        }));
    }

    public Result addCoupons(CouponsDTO couponsDTO) {
        List<Long> goodsIdList = couponsDTO.getGoodIdList();
        if (CollectionUtils.isEmpty(goodsIdList)) {
            return Result.fail("选择商品列表不能为空");
        }

        if (CollectionUtils.isEmpty(goodsIdList)) {
            return Result.fail();
        }
        for (Long gid : goodsIdList) {

            Coupon coupon = new Coupon();
            coupon.setTitle(couponsDTO.getTitle());
            coupon.setStartTime(couponsDTO.getStartTime());
            coupon.setEndTime(couponsDTO.getEndTime());
            // 剩余数  制券数
            coupon.setLeftCount(couponsDTO.getAskCount());
            coupon.setAskCount(couponsDTO.getAskCount());
            // 状态
//            if (couponsDTO.getStartTime().after(new Date())) {
//                coupon.setStatus(UN_EFFECT.getCode());
//            } else if (couponsDTO.getEndTime().after(new Date()) && new Date().after(couponsDTO.getStartTime())) {
//                coupon.setStatus(EFFECT.getCode());
//            } else {
//                coupon.setStatus(TIMEOUT.getCode());
//            }
            couponRespository.saveAndFlush(coupon);

            Goods goods = goodsReposiroty.findOne(gid);
            if (goods == null) {
                return Result.fail("商品id :" + goods.getId() + " 不存在.");
            }
            CouponGoodsRelation goodsRelation = new CouponGoodsRelation(null, coupon.getId(), gid, 0);
            couponGoodsRelationRep.save(goodsRelation);
        }

        return Result.success("新增优惠券成功");
    }

    /**
     * 增回 商品关联 至 优惠劵
     *
     * @param goodsIdList
     * @param coupon
     * @return
     */
    @Deprecated
    private Result addGoods2Coupon(List<Long> goodsIdList, Coupon coupon) {
        if (CollectionUtils.isEmpty(goodsIdList)) {
            return Result.fail();
        }
        for (Long gid : goodsIdList) {
            Goods goods = goodsReposiroty.findOne(gid);
            if (goods == null) {
                return Result.fail("商品id :" + goods.getId() + " 不存在.");
            }
            CouponGoodsRelation goodsRelation = new CouponGoodsRelation(null, coupon.getId(), gid, 0);
            couponGoodsRelationRep.save(goodsRelation);
        }
        return Result.success();
    }

//    public Result changeCouponState(Long id, Integer state) {
//
//        if (state > 3 || state < 1) {
//            return Result.fail("请输入正确的状态 1 未生效 0生效中 2已失效");
//        }
//        Coupon coupon = couponRespository.findOne(id);
//        if (coupon == null) {
//            return Result.fail("修改状态失败");
//        }
////        coupon.setStatus(state);
//        couponRespository.saveAndFlush(coupon);
//        return Result.success("修改状态成功");
//    }

    @Transactional
    public Result updateCoupons(Long id, CouponsDTO couponsDTO) {
        Coupon coupon = couponRespository.findOne(id);
        if (coupon == null) {
            return Result.fail("该优惠券不存在");
        }

        coupon.setTitle(couponsDTO.getTitle());
        coupon.setCouponType(1);
        coupon.setStartTime(couponsDTO.getStartTime());
        coupon.setEndTime(couponsDTO.getEndTime());

        Integer ints = couponGoodsRelationRep.removeAllByCouponId(coupon.getId());
        logger.info("更新 " + ints + " 条记录");

        List<Long> goodIdList = couponsDTO.getGoodIdList();
        if (goodIdList.size() > 1) {
            return Result.fail("只能关联一个商品");
        }
        Long gid = goodIdList.get(0);
        Goods goods = goodsReposiroty.findOne(gid);
        if (goods == null) {
            return Result.fail("商品 " + goods.getId() + " 不存在");
        }
        CouponGoodsRelation relation = new CouponGoodsRelation(null, coupon.getId(), gid, 0);
        couponGoodsRelationRep.save(relation);
//        addGoods2Coupon(goodIdList, coupon);
        // 制券数
        if (coupon.getAskCount() > couponsDTO.getAskCount()) {
            return Result.fail("新制券数必须大于原制券数");
        }
        coupon.setAskCount(couponsDTO.getAskCount());

        couponRespository.saveAndFlush(coupon);
        return Result.success("编辑优惠券成功");
    }

    public Result handlerFile(MultipartFile file, Long couponId) {
        if (file == null) {
            return Result.fail("请选择文件");
        }
        List<Object> list = new ArrayList<>();
        try {
            poiService.excel2Object(file.getInputStream(), list, CouponUserRelationDTO.class);
        } catch (IOException e) {
            logger.info(e);
            return Result.fail("上传异常");
        }


        Map<Object, Object> mapVo = new HashMap<>();

        List<CouponUserRelationDTO> okList = new ArrayList<>();

        Coupon coupon = couponRespository.findOne(couponId);
        int leftCount = coupon.getLeftCount();
        int sum = sumCouponListCount(okList);
        if (sum > leftCount) {
            return Result.fail("表格中所赠送的优惠劵大于优惠劵的剩于张数，目前剩于 " + leftCount);
        }

        List<CouponUserRelationDTO> filterList = errorUser(list, okList);


        String tempId = UUID.randomUUID().toString().replace("-", "");
        String json = JSONObject.toJSONString(okList);

        redisBean.setStringValue("coupon:" + tempId, json, 5, TimeUnit.MINUTES);

        if (CollectionUtils.isNotEmpty(filterList)) {
            mapVo.put("errorRecord", filterList);
            mapVo.put("tempId", tempId);
            return Result.success(mapVo);
        }
        mapVo.put("tempId", tempId);
        return Result.success(mapVo);
    }

    public int sumCouponListCount(List<CouponUserRelationDTO> okList) {
        int sum = okList.stream().map(el -> {
            try {
                Integer count = Integer.valueOf(el.getCount());
                return count;
            } catch (NumberFormatException e) {
                logger.info(e.getMessage(), e);
                return 0;
            }
        }).reduce((e1, e2) -> {
            return e1 + e2;
        }).get();

        return sum;
    }

    private List<CouponUserRelationDTO> errorUser(List<Object> srcList, List<CouponUserRelationDTO> okList) {

        List<CouponUserRelationDTO> filterList = srcList.stream()
                .map(el -> (CouponUserRelationDTO) el)
                .filter(obj -> {
                    CouponUserRelationDTO couponUserRelationDTO = (CouponUserRelationDTO) obj;
                    String phone = couponUserRelationDTO.getPhone();
                    String count = couponUserRelationDTO.getCount();
                    User user = userRepository.findByPhone(phone);
                    if (user == null) {
                        obj.setMsg("用户不存在");
                        return true;
                    }
                    boolean is = CheckoutUtil.isNumeric(count);
                    if (!is) {
                        obj.setMsg(obj.getPhone() + " 对应数量 " + count + " 不正确");
                        return true;
                    }
                    couponUserRelationDTO.setUserId(user.getId());
                    okList.add(couponUserRelationDTO);
                    return false;
                }).collect(Collectors.toList());

        return filterList;
    }

    /**
     * 发送优惠劵
     *
     * @param tempId
     * @param couponId
     * @return
     */
    public Result sendCoupon(String tempId, Long couponId) {

        Coupon coupon = couponRespository.findOne(couponId);

        if (coupon == null) return Result.fail("优惠劵不存在");

        String json = redisBean.getStringValue("coupon:" + tempId);

        if (StringUtils.isBlank(json)) {
            return Result.fail("数据已失效");
        }

        List<CouponUserRelationDTO> couponUserRelationDTOS = JSONArray.parseArray(json, CouponUserRelationDTO.class);

        for (CouponUserRelationDTO couponUserRelationDTO : couponUserRelationDTOS) {
            String count = couponUserRelationDTO.getCount();
            Integer countInt = Integer.valueOf(count);
            int leftCount = coupon.getLeftCount();
            if (countInt > leftCount) {
                return Result.fail("优惠劵id: " + coupon.getId() + " 数量不够了，目前剩于数量为 " + leftCount);
            }
            for (int i = 0; i < countInt; i++) {
                UserCoupon userCoupon = new UserCoupon(null, couponUserRelationDTO.getUserId(), coupon, UN_USE.getCode(), null);
                userCouponsRepository.save(userCoupon);
            }
        }
        int leftCount = coupon.getLeftCount();
        int i = leftCount - couponUserRelationDTOS.size();
        coupon.setLeftCount(i);
        couponRespository.save(coupon);
        redisBean.setStringValue("coupon:" + tempId, "", 1, TimeUnit.MICROSECONDS);
        return Result.success();
    }

}
