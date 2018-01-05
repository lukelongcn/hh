package com.h9.admin.service;

import com.h9.admin.model.dto.hotel.EditHotelDTO;
import com.h9.admin.model.dto.hotel.EditRoomDTO;
import com.h9.admin.model.vo.BalanceFlowVO;
import com.h9.admin.model.vo.HotelListVO;
import com.h9.admin.model.vo.HotelRoomListVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.hotel.Hotel;
import com.h9.common.db.entity.hotel.HotelRoomType;
import com.h9.common.db.repo.HotelRepository;
import com.h9.common.db.repo.HotelRoomTypeRepository;
import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by itservice on 2018/1/4.
 */
@Service
public class HotelService {

    @Resource
    private HotelRepository hotelRepository;
    @Resource
    private HotelRoomTypeRepository hotelRoomTypeRepository;

    private Logger logger = Logger.getLogger(this.getClass());

    public Result hotelList(int page,int limit) {

        return Result.success(hotelRepository.findAll(page, limit).result2Result(HotelListVO::new));

    }

    public Result editHotel(EditHotelDTO editHotelDTO) {

        Hotel hotel = null;

        if(editHotelDTO.getId() == null){
            hotel = new Hotel();
        }else{
            hotel = hotelRepository.findOne(editHotelDTO.getId());
        }
        BeanUtils.copyProperties(editHotelDTO,hotel);

        String startReserveTime = editHotelDTO.getStartReserveTime();
        String endReserveTime = editHotelDTO.getEndReserveTime();

        Result result = validTime(startReserveTime, endReserveTime);
        if(result.getCode() == 1) return result;

        hotelRepository.save(hotel);
        return Result.success();
    }

    private Result validTime(String... time){

        if(time.length > 0){

            for(int i = 0;i<time.length;i++) {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                try {
                    Date date = format.parse("2017-09-03 "+time[i]);
                } catch (ParseException e) {
                    logger.info(e.getMessage(),e);
                    return Result.fail("时间格式不对,请写 eg: 08:30 格式");
                }
            }

            return Result.success();
        }

        return Result.fail("time 不能为空");
    }

    public Result roomList(Long hotelId,int page,int limit) {

        Hotel hotel = hotelRepository.findOne(hotelId);
        if(hotel == null) return Result.fail("此酒店不存在的。");

        return Result.success(hotelRoomTypeRepository.findAll(page, limit).map(HotelRoomListVO::new));

    }

    public Result editRoom(EditRoomDTO editRoomDTO) {

        Long hotelId = editRoomDTO.getHotelId();
        Hotel hotel = hotelRepository.findOne(hotelId);

        if(hotel == null) return Result.fail("酒店不存在");
        HotelRoomType room = null;
        if(editRoomDTO.getId() != null){
             room = hotelRoomTypeRepository.findOne(editRoomDTO.getId());
        }else{
            room = new HotelRoomType();
        }

        if(room == null) room = new HotelRoomType();

        BeanUtils.copyProperties(editRoomDTO,room);

        room.setHotel(hotel);
        hotelRoomTypeRepository.save(room);

        return Result.success();
    }
}
