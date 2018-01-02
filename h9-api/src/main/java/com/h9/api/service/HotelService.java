package com.h9.api.service;

import com.h9.api.model.vo.HotelDetailVO;
import com.h9.api.model.vo.HotelListVO;
import com.h9.common.base.Result;
import com.h9.common.db.entity.Hotel;
import com.h9.common.db.entity.HotelRoomType;
import com.h9.common.db.repo.HotelRepository;
import com.h9.common.db.repo.HotelRoomTypeRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by itservice on 2018/1/2.
 */
@Service
public class HotelService {

    @Resource
    private HotelRepository hotelRepository;

    @Resource
    private HotelRoomTypeRepository hotelRoomTypeRepository;

    public Result detail(Long hotelId) {
        Hotel hotel = hotelRepository.findOne(hotelId);

        if (hotel == null) return Result.fail("酒店不存在");

        HotelRoomType type = new HotelRoomType().setStatus(1);
        Example<HotelRoomType> of = Example.of(type);

        List<HotelRoomType> hotelRoomTypeList = hotelRoomTypeRepository.findAll(of);

        if (CollectionUtils.isNotEmpty(hotelRoomTypeList)) {
            return Result.success(new HotelDetailVO(hotel,hotelRoomTypeList));
        }
        return Result.success(new HotelDetailVO(hotel,null));
    }

    public Result hotelList(String city, String queryKey) {

        if (StringUtils.isNotBlank(queryKey) ) {
            List<Hotel> hotelList = hotelRepository.findByCityAndHotelName(city, "%"+queryKey+"%");
            if (CollectionUtils.isNotEmpty(hotelList)) {
                List<HotelListVO> voList = hotelList.stream().map(el -> new HotelListVO(el)).collect(Collectors.toList());
                return Result.success(voList);
            }else{
                return Result.fail("没有找到此类酒店");
            }
        }

        return Result.fail("请选择地点/酒店");
    }
}
