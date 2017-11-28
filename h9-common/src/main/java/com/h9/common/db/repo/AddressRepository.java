package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.Address;

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
    @Query("select a from Address a where a.userId = ?1 and status = 1")
    List<Address> findAddressList(Long userId);
}
