package com.h9.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.h9.admin.model.dto.HotelOrderSearchDTO;
import com.h9.admin.model.dto.hotel.EditHotelDTO;
import com.h9.admin.model.dto.hotel.EditRoomDTO;
import com.h9.admin.model.vo.HotelListVO;
import com.h9.admin.model.vo.HotelOrderListVO;
import com.h9.admin.model.vo.HotelRoomListVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.config.HtmlContent;
import com.h9.common.db.entity.hotel.Hotel;
import com.h9.common.db.entity.hotel.HotelOrder;
import com.h9.common.db.entity.hotel.HotelRoomType;
import com.h9.common.db.repo.HotelOrderRepository;
import com.h9.common.db.repo.HotelRepository;
import com.h9.common.db.repo.HotelRoomTypeRepository;
import com.h9.common.db.repo.HtmlContentRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.h9.common.db.entity.hotel.HotelOrder.OrderStatusEnum.REFUND_MONEY;

/**
 * Created by itservice on 2018/1/4.
 */
@Service
public class HotelService {

    @Resource
    private HotelRepository hotelRepository;
    @Resource
    private HotelRoomTypeRepository hotelRoomTypeRepository;
    @Resource
    private HotelOrderRepository hotelOrderRepository;

    private Logger logger = Logger.getLogger(this.getClass());

    public Result hotelList(int page, int limit) {

        return Result.success(hotelRepository.findAll(page, limit).result2Result(hotel ->{

            Long roomCount = hotelRoomTypeRepository.countByHotel(hotel);
            return new HotelListVO(hotel, roomCount.intValue());
        }));

    }

    public Result editHotel(EditHotelDTO editHotelDTO) {

        Hotel hotel = null;

        if (editHotelDTO.getId() == null) {
            hotel = new Hotel();
            hotel.setStatus(Hotel.Status.NORMAL.getCode());
        } else {
            hotel = hotelRepository.findOne(editHotelDTO.getId());
        }
        BeanUtils.copyProperties(editHotelDTO, hotel);
        hotel.setImages(editHotelDTO.getImages());

        String hotelInfo = editHotelDTO.getHotelInfo();

        hotelInfo = appendHtml(hotelInfo);
        hotel.setHotelInfo(hotelInfo);
        String startReserveTime = editHotelDTO.getStartReserveTime();
        String endReserveTime = editHotelDTO.getEndReserveTime();

        Result result = validTime(startReserveTime, endReserveTime);
        if (result.getCode() == 1) return result;

        hotelRepository.saveAndFlush(hotel);

        return Result.success();
    }

    private String appendHtml(String html) {
        String htmlModel = "<html><body>" + html + "</body></html>";
        return htmlModel;
    }

    private Result validTime(String... time) {

        if (time.length > 0) {

            for (int i = 0; i < time.length; i++) {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                try {
                    Date date = format.parse("2017-09-03 " + time[i]);
                } catch (ParseException e) {
                    logger.info(e.getMessage(), e);
                    return Result.fail("时间格式不对,请写 eg: 08:30 格式");
                }
            }

            return Result.success();
        }

        return Result.fail("time 不能为空");
    }

    public Result roomList(Long hotelId, int page, int limit) {

        Hotel hotel = hotelRepository.findOne(hotelId);
        if (hotel == null) return Result.fail("此酒店不存在的。");

        return Result.success(hotelRoomTypeRepository.findAll(page, limit).map(HotelRoomListVO::new));

    }

    public Result editRoom(EditRoomDTO editRoomDTO) {

        Long hotelId = editRoomDTO.getHotelId();
        Hotel hotel = hotelRepository.findOne(hotelId);

        if (hotel == null) return Result.fail("酒店不存在");
        HotelRoomType room = null;
        if (editRoomDTO.getId() != null) {
            room = hotelRoomTypeRepository.findOne(editRoomDTO.getId());
        } else {
            room = new HotelRoomType();
        }

        if (room == null) room = new HotelRoomType();

        BeanUtils.copyProperties(editRoomDTO, room);
        List<String> image = editRoomDTO.getImage();
        if (CollectionUtils.isNotEmpty(image)) {
            room.setImage(JSONObject.toJSONString(image));
        }

        room.setHotel(hotel);
        hotelRoomTypeRepository.save(room);

        return Result.success();
    }

    public Result modifyHotelStatus(Long hotelId, Integer status) {
        Hotel hotel = hotelRepository.findOne(hotelId);
        if (hotel == null) return Result.fail("酒店不存在");

        hotel.setStatus(status == 1 ? 1 : 0);
        hotelRepository.save(hotel);
        return Result.success();
    }

    public Result modifyHotelRoomStatus(Long hotelId, Integer status, Long roomId) {
        Hotel hotel = hotelRepository.findOne(hotelId);
        if (hotel == null) return Result.fail("酒店不存在");

        HotelRoomType hotelRoomType = hotelRoomTypeRepository.findOne(roomId);

        if (hotelRoomType == null) return Result.fail("房间号不存在");

        hotelRoomType.setStatus(status == 1 ? 1 : 0);

        hotelRoomTypeRepository.save(hotelRoomType);
        return Result.success();
    }

    public Result ordersList(HotelOrderSearchDTO hotelOrderSearchDTO, int page, int limit) {

        Specification<HotelOrder> hotelOrderSpecification = orderSpecification(hotelOrderSearchDTO);
        PageResult<HotelOrderListVO> result = null;

        if (hotelOrderSearchDTO == null) {

            result = hotelOrderRepository.findAll(page, limit).map(HotelOrderListVO::new);
        } else {
            result = hotelOrderRepository.findAll(hotelOrderSpecification, page, limit).map(HotelOrderListVO::new);
        }

        return Result.success(result);

    }

    private Specification<HotelOrder> orderSpecification(HotelOrderSearchDTO hotelOrderSearchDTO) {
        return (root, query, builder) -> {
            Long hotelOrderId = hotelOrderSearchDTO.getHotelOrderId();
            String phone = hotelOrderSearchDTO.getPhone();
            Date startDate = hotelOrderSearchDTO.getStartDate();
            Date endDate = hotelOrderSearchDTO.getEndDate();
            Integer status = hotelOrderSearchDTO.getStatus();

            List<Predicate> predicateList = new ArrayList<>();

            if (hotelOrderId != null) {
                predicateList.add(builder.equal(root.get("id"), hotelOrderId));
            }

            if (StringUtils.isNotBlank(phone)) {
                predicateList.add(builder.equal(root.get("phone"), phone));
            }

            if (startDate != null && endDate != null) {
                predicateList.add(builder.between(root.get("createTime"), startDate, endDate));
            }

            if (status != null) {
                predicateList.add(builder.equal(root.get("orderStatus"), status));
            }

            return builder.and(predicateList.toArray(new Predicate[predicateList.size()]));
        };
    }

    public Result changeOrderStatus(Long hotelOrderId, Integer status) {

        HotelOrder hotelOrder = hotelOrderRepository.findOne(hotelOrderId);
        if (hotelOrder == null) return Result.fail("订单不存在");

        Result result = null;
        switch (status) {
            case 1:
                //确认订单
                result = affirmOrder(hotelOrder);
                break;

            case 2:
                //退款订单
                result = refundOrder(hotelOrder);
                break;

            default:
                return Result.fail("请传正确的status");
        }
        return result;
    }

    private Result refundOrder(HotelOrder hotelOrder) {
        if (hotelOrder == null) return Result.fail("退款失败，订单不存在");
        //TODO 调用微信退款
        hotelOrder.setOrderStatus(REFUND_MONEY.getCode());
        hotelOrderRepository.save(hotelOrder);
        return Result.success();
    }

    public Result affirmOrder(HotelOrder hotelOrder) {

        if (hotelOrder == null) return Result.fail("确认失败，订单不存在");
        hotelOrder.setOrderStatus(HotelOrder.OrderStatusEnum.SUCCESS.getCode());
        hotelOrderRepository.save(hotelOrder);
        return Result.success();
    }
}
