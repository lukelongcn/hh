package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.base.PageResult;
import com.h9.common.db.entity.user.UserCoupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:用户优惠券库</p>
 *
 * @author LiYuan
 * @Date 2018/4/3
 */
@Repository
public interface UserCouponsRepository extends BaseRepository<UserCoupon> {

    @Query("select s from UserCoupon s where s.userId = ?1 and s.state = ?2  order by s.id DESC ")
    Page<UserCoupon> findState(Long userId, Integer state, Pageable pageRequest);

    default PageResult<UserCoupon> findState(Long userId, Integer state,int page, int limit) {
        Page<UserCoupon> list = findState(userId, state,pageRequest(page, limit));
        return new PageResult(list);
    }
}
