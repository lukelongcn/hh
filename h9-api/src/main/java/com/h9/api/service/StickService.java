package com.h9.api.service;

import com.alibaba.fastjson.JSONObject;
import com.h9.api.model.dto.StickDto;
import com.h9.api.model.vo.HomeVO;
import com.h9.api.model.vo.StickSampleVO;
import com.h9.api.model.vo.StickTypeDetailVo;
import com.h9.api.model.vo.StickTypeVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.db.entity.community.Stick;
import com.h9.common.db.entity.community.StickType;
import com.h9.common.db.entity.config.Banner;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.repo.BannerRepository;
import com.h9.common.db.repo.StickRepository;
import com.h9.common.db.repo.StickTypeRepository;
import com.h9.common.db.repo.UserRepository;
import com.h9.common.utils.DateUtil;
import lombok.extern.jbosslog.JBossLog;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * StickService:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/2
 * Time: 14:49
 */
@Component
public class StickService {

    Logger logger = Logger.getLogger(StickService.class);

    @Resource
    private StickTypeRepository stickTypeRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private CommonService commonService;
    @Resource
    private StickRepository stickRepository;
    @Resource
    private BannerRepository bannerRepository;




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


    public Result listStick(String type,int page,Integer limit){

        if(type.equals("config_home")){
            Page<Stick> home = stickRepository.find4Home(stickRepository.pageRequest(page,limit!=null?limit:5));
            PageResult<Stick> pageResult = new PageResult(home);
            return Result.success(pageResult.result2Result(StickSampleVO::new));
        }else if(type.equals("config_hot")){
            Page<Stick> home = stickRepository.find4Hot(stickRepository.pageRequest(page,limit!=null?limit:20));
            PageResult<Stick> pageResult = new PageResult(home);
            return Result.success(pageResult.result2Result(StickSampleVO::new));
        }else{
            Page<Stick> home = stickRepository.findType(type,stickRepository.pageRequest(page,limit!=null?limit:20));
            PageResult<Stick> pageResult = new PageResult(home);
            return Result.success(pageResult.result2Result(StickSampleVO::new));
        }
    }

    @Transactional
    public Result home(){
        Map<String, List<HomeVO>> listMap = new HashMap<>();
        try(Stream<Banner> activiBanner = bannerRepository.findActiviBanner(3, new Date())){
            Function<Banner, String> function = b -> b.getBannerType().getCode();
            listMap = activiBanner.collect(Collectors.groupingBy(function, Collectors.mapping(HomeVO::new, Collectors.toList())));
        }catch (Exception e){
            logger.debug(e.getMessage(),e);
        }
        return Result.success(listMap);
    }

    public Result typeDetail(long typeId){
        StickType stickType = stickTypeRepository.findOne(typeId);
        if(stickType==null){
            return Result.fail("分类不存再");
        }
        return Result.success(new StickTypeDetailVo(stickType));
    }


}
