package com.h9.common.db.repo;

/**
 * Created by 李圆 on 2018/1/3
 */

import com.h9.common.base.BaseRepository;
import com.h9.common.base.PageResult;
import com.h9.common.db.entity.order.Address;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserAdvice;

import net.bytebuddy.asm.Advice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface AdviceRespository extends BaseRepository<UserAdvice> {

    @Query("select a from UserAdvice a order by a.createTime DESC")
    Page<UserAdvice> findAdviceList(Pageable pageRequest);

    default PageResult<UserAdvice> findAdviceList(int page, int limit) {
        Page<UserAdvice> AdviceList = findAdviceList(pageRequest(page, limit));
        return new PageResult(AdviceList);
    }

}
