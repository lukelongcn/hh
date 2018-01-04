package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.hotel.HotelOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

/**
 * Created by itservice on 2018/1/3.
 */
public interface HotelOrderRepository extends BaseRepository<HotelOrder> {

    Page<HotelOrder> findByOrderStatusIn(Collection status, Pageable pageRequest);


}
