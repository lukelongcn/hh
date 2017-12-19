package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.base.PageResult;
import com.h9.common.db.entity.Address;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by itservice on 2017/11/15.
 */
public interface AddressRepository extends BaseRepository<Address> {
    /**
     * 查找地址列表
     *
     * @param userId
     * @return
     */
    @Query("select a from Address a where a.userId = ?1 and status = 1 order by id DESC")
    Page<Address> findAddressList(Long userId, Pageable pageRequest);

    default PageResult<Address> findAddressList(Long userId, int page, int limit) {
        Page<Address> AddressList = findAddressList(userId, pageRequest(page, limit));
        return new PageResult(AddressList);
    }

    Address findById(Long id);

    List<Address> findByUserId(Long userId);

    Address findByUserIdAndDefaultAddressAndStatus(Long userId, Integer defaultValue, Integer status);

    @Query(value = "select * from address  where user_id =?1 and status = 1 order by update_time desc limit 0,1", nativeQuery = true)
    Address findByLastUpdate(Long userId);

    @Modifying
    @Query("update Address a set a.defaultAddress = 0 where a.userId = ?1")
    void updateDefault(Long userId);

    @Modifying
    @Query("update Address a set a.defaultAddress = 0 where a.userId = ?1 and a.id <> ?2")
    void updateElseDefault(Long userId, Long aid);

    @Query(value = "select @@IDENTITY",nativeQuery = true)
    Long findInsertId();
}
