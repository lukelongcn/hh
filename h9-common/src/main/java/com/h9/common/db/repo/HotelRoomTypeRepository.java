package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.base.PageResult;
import com.h9.common.db.entity.hotel.Hotel;
import com.h9.common.db.entity.hotel.HotelRoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by itservice on 2018/1/2.
 */
public interface HotelRoomTypeRepository extends BaseRepository<HotelRoomType> {

    Page<HotelRoomType> findByHotel(Hotel hotel, Pageable pageable);

    List<HotelRoomType> findByHotel(Hotel hotel);

    @Query("select count(hrt)from HotelRoomType hrt where hrt.hotel=?1 and hrt.status=1")
    Long countByHotel(Hotel hotel);

    @Query("select h from HotelRoomType h where h.hotel.id = ?1 and h.status = 1")
    Page<HotelRoomType> findAllRoom(Long hotelId,Pageable pageable);
    default PageResult<HotelRoomType> findAllRoom(Long hotelId, int page, int limit){
        Page<HotelRoomType> hotelRooms = findAllRoom(hotelId,pageRequest(page,limit));
        return new PageResult<>(hotelRooms);
    }

    List<HotelRoomType> findByHotelAndStatus(Hotel hotel,Integer status);
}
