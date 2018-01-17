package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.hotel.Hotel;
import com.h9.common.db.entity.hotel.HotelRoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by itservice on 2018/1/2.
 */
public interface HotelRoomTypeRepository extends BaseRepository<HotelRoomType> {

    Page<HotelRoomType> findByHotel(Hotel hotel, Pageable pageable);

    List<HotelRoomType> findByHotel(Hotel hotel);

    Long countByHotel(Hotel hotel);
}
