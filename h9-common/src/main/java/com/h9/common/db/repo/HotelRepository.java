package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.base.PageResult;
import com.h9.common.db.entity.hotel.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by itservice on 2018/1/2.
 */
public interface HotelRepository  extends BaseRepository<Hotel>{

    @Query("select o from Hotel  o where o.city = ?1 and ( o.detailAddress like ?2 or o.hotelName like ?2) and o.status = 1")
    Page<Hotel> findByCityAndHotelName(String city, String hotelName, Pageable pageable);

    @Query("select o from Hotel  o where ( o.detailAddress like ?1 or o.hotelName like ?1 ) and o.status = 1")
    Page<Hotel> findByHotelName( String hotelName, Pageable pageable);

    @Query("select o.city from Hotel o where o.status = 1 group by o.city")
    List<String> findAllHotelCity();

    @Query("select o from Hotel  o where o.city = ?1  and o.status = 1")
    Page<Hotel> findByCity(String city,Pageable pageable);

    @Query("select o from Hotel o where o.status =1")
    Page<Hotel> findAllHotel(Pageable pageable);

    @Query("select o from Hotel o where o.status =1")
    PageResult<Hotel> findAllHotelList(PageRequest pageRequest, Sort sort);
}
