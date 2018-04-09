package com.h9.admin.job;

import com.h9.admin.service.ActivityService;
import com.h9.admin.service.CouponService;
import org.jboss.logging.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:用户优惠券过期定时任务</p>
 *
 * @author LiYuan
 * @Date 2018/4/9
 */
@Component
public class UserCouponJob {
    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private Lock lock;

    @Resource
    private CouponService couponService;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void scan() {

        logger.info("start change status UserCouponJob ----------->");
        String lockKey = "h9:UserCouponJob:lock";

        if (!lock.getLock(lockKey, 1, TimeUnit.MINUTES)) {
            return;
        }
        couponService.startChangeStatusUserCoupon();
    }
}
