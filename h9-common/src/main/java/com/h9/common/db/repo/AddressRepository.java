package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.base.PageResult;
import com.h9.common.db.entity.Address;
import com.h9.common.db.entity.BalanceFlow;
import com.h9.common.db.entity.Orders;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by itservice on 2017/11/15.
 */
public interface AddressRepository extends BaseRepository<Address> {

    /**
     *查找地址列表
     * @param userId
     * @return
     */
    @Query("select a from Address a where a.userId = ?1 and status = 1 order by id DESC")
    Page<Address> findAddressList(Long userId,Pageable pageRequest);

    default PageResult<Address> findAddressList(Long userId, int page, int limit){
        Page<Address> AddressList = findAddressList(userId, pageRequest(page, limit));
        return new PageResult(AddressList);
    }

    Address findById(Long id);
}
