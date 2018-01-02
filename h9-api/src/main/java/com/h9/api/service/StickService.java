package com.h9.api.service;

import com.h9.api.model.dto.StickDto;
import com.h9.api.model.vo.StickSampleVO;
import com.h9.api.model.vo.StickTypeVO;
import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.db.entity.community.Stick;
import com.h9.common.db.entity.community.StickType;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.repo.StickRepository;
import com.h9.common.db.repo.StickTypeRepository;
import com.h9.common.db.repo.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * StickService:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/2
 * Time: 14:49
 */
@Component
public class StickService {

    @Resource
    private StickTypeRepository stickTypeRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private CommonService commonService;
    @Resource
    private StickRepository stickRepository;



    public Result getStickType(){
        List<StickType> stickTypes = stickTypeRepository.findAll();
        List<StickTypeVO> stickTypeVOS = new ArrayList<>();
        if(stickTypes !=null){
            stickTypeVOS = stickTypes.stream().map(StickTypeVO::new).collect(Collectors.toList());
        }
        return Result.success(stickTypeVOS);
    }

    @Transactional
    public Result addStick(Long userId, StickDto stickDto){
        User user = userRepository.findOne(userId);
        StickType stickType = stickTypeRepository.findOne(stickDto.getTypeId());
        if(stickType == null){
            return Result.fail("请选择分类");
        }
        Stick stick = new Stick();
        stick.setTitle(stickDto.getTitle());
        stick.setContent(stickDto.getContent());
        stick.setStickType(stickType);
        stick.setUser(user);
        double latitude = stickDto.getLatitude();
        double longitude = stickDto.getLongitude();
        stick.setLatitude(stickDto.getLatitude());
        stick.setLongitude(longitude);
        if(latitude!=0&&longitude!=0){
            CommonService.AddressResult addressDetail = commonService.getAddressDetail(latitude, longitude);
            if(addressDetail!=null){
                stick.setAddress(addressDetail.getDetailAddress());
                stick.setCity(addressDetail.getCity());
                stick.setProvince(addressDetail.getProvince());
                stick.setDistrict(addressDetail.getDistrict());
            }
        }
        Stick stickFromDb = stickRepository.save(stick);
        return Result.success(new StickSampleVO(stickFromDb));
    }





}
