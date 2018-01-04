package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.hotel.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by itservice on 2018/1/2.
 */
public interface HotelRepository  extends BaseRepository<Hotel>{

    @Query("select o from Hotel  o where o.city = ?1 or ( o.detailAddress like ?2 or o.hotelName = ?2)")
    Page<Hotel> findByCityAndHotelName(String city, String hotelName, Pageable pageable);

    @Query("select o.city from Hotel o group by o.city")
    List<String> findAllHotelCity();

}
