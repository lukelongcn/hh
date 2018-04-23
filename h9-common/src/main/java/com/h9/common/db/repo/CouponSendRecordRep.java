package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.coupon.CouponSendRecord;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Ln on 2018/4/19.
 */
public interface CouponSendRecordRep extends BaseRepository<CouponSendRecord> {

    @Query("select o from CouponSendRecord o where o.uuid = ?1")
    CouponSendRecord findByUuid(String uuid);
}
