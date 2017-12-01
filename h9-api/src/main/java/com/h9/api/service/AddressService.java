package com.h9.api.service;

import com.h9.api.model.dto.AddressDTO;
import com.h9.api.model.dto.Areas;
import com.h9.api.model.vo.AddressVO;
import com.h9.api.model.vo.SimpleAddressVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.Address;
import com.h9.common.db.entity.China;
import com.h9.common.db.entity.City;
import com.h9.common.db.entity.Distict;
import com.h9.common.db.entity.Province;
import com.h9.common.db.entity.User;
import com.h9.common.db.repo.AddressRepository;
import com.h9.common.db.repo.ChinaRepository;
import com.h9.common.db.repo.CityRepository;
import com.h9.common.db.repo.DistictRepository;
import com.h9.common.db.repo.ProvinceRepository;
import com.h9.common.db.repo.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;


/**
 * Created by 李圆 on 2017/11/27
 */
@Service
@Transactional
public class AddressService {

    @Resource
    AddressRepository addressRepository;
    @Resource
    ProvinceRepository provinceRepository;
    @Resource
    CityRepository cityRepository;

    @Resource
    ChinaRepository chinaRepository;

    @Resource
    private UserRepository userRepository;

    /**
     * 地址列表
     * @param userId
     * @return
     */
    public Result allAddress(Long userId, int page, int limit) {
        PageResult<Address> pageResult = addressRepository.findAddressList(userId, page, limit);
        if (pageResult == null){
            return Result.success("该用户没有存储过地址");
        }
        return Result.success(pageResult.result2Result(AddressVO::convert));
    }


    /**
     * 省市区
     * @return
     */
    public Result allAreas() {

        List<Province> allProvices = provinceRepository.findAllProvinces();
        List<Areas> areasList = new ArrayList<>();
        if (CollectionUtils.isEmpty(allProvices)){ return Result.success();}
        allProvices.forEach(province -> {

            List<City> allCities = cityRepository.findCities(province.getId());
            List<Areas> cityList = new ArrayList<>();
            allCities.forEach(city -> {
                Areas careas=new Areas(city.getId()+"",city.getName());
                cityList .add(careas);
            });

            Areas pareas=new Areas(province.getId()+"",province.getName(),cityList);
            areasList.add(pareas);
        });

        return Result.success(areasList);
    }

    public Result allAres() {
        List<China> allProvices = chinaRepository.findAllProvinces();
        if (CollectionUtils.isEmpty(allProvices)){ return Result.success();}

        List<Areas> areasList = new ArrayList<>();
        List<Areas> cityList = new ArrayList<>();
        List<Areas> areaList = new ArrayList<>();

        List<China> allCities = chinaRepository.findByLevel(2);
        List<China> allAreas = chinaRepository.findByLevel(3);
        allCities.forEach(citys->{

            allAreas.forEach(areas->{
                    Areas a =new Areas(areas.getId()+"",areas.getName());
                    areaList .add(a);
        });
            Areas careas=new Areas(citys.getId()+"",citys.getName(),areaList);
            cityList .add(careas);
        });

        //List<China> allAreas = chinaRepository.findByLevel(3);

        allProvices.forEach(province -> {

            //List<China> allCities = chinaRepository.findCities(province.getCode());

            /*allCities.forEach(city -> {

                allAreas.forEach(area -> {
                    Areas areas=new Areas(area.getCode(),area.getName());
                    areaList .add(areas);
                });

                Areas careas=new Areas(city.getCode(),city.getName(),areaList);
                cityList .add(careas);
            });*/

            Areas pareas=new Areas(province.getCode(),province.getName(),cityList);
            areasList.add(pareas);
        });

        return Result.success(areasList);
    }


    /**
     * 添加收货地址
     * @param userId
     * @param addressDTO
     * @return
     */
    public Result addAddress(Long userId,AddressDTO addressDTO){

        Address address = new Address();
        address.setUserId(userId);
        address.setName(addressDTO.getName());
        address.setPhone(addressDTO.getPhone());

        String provinceName = addressDTO.getProvince();
        String cityName = addressDTO.getCity();
        String areaName = address.getDistict();
        address.setProvince(provinceName);
        address.setCity(cityName);
        address.setDistict(areaName);

        String  p_parentCode = chinaRepository.findPid(provinceName);
        String  c_parentCode = chinaRepository.findCid(p_parentCode,cityName);
        String  a_parentCode = chinaRepository.findCid(c_parentCode,areaName);
        address.setProvincialCity(p_parentCode+","+c_parentCode+","+a_parentCode);

        address.setAddress(addressDTO.getAddress());
        // 设置是否为默认地址
        address.setDefaultAddress(addressDTO.getDefaultAddress());

        // 使用状态设为开启
        address.setStatus(1);
        addressRepository.save(address);
        return Result.success("地址添加成功");
    }


    /**
     * 修改地址启用状态
     * @param userId
     * @param aid
     * @return
     */
    public Result deleteAddress(Long userId, Long aid) {
        Address address = addressRepository.findById(aid);
        if (address == null){ return Result.fail("地址不存在"); }
        if (!userId.equals(address.getUserId())){ return Result.fail("登录用户与地址所属用户不一致，无权操作"); }
        address.setStatus(0);
        addressRepository.save(address);
        return Result.success("删除地址成功");
    }


    /**
     * 修改收货地址
     * @param userId
     * @param aid
     * @return
     */
    public Result updateAddress(Long userId, Long aid,AddressDTO addressDTO) {

        Address address = addressRepository.findById(aid);
        if (address == null){ return Result.fail("地址不存在"); }
        if (!userId.equals(address.getUserId())){ return Result.fail("无权操作"); }

        address.setUserId(userId);
        address.setName(addressDTO.getName());
        address.setPhone(addressDTO.getPhone());

        String provinceName = addressDTO.getProvince();
        String cityName = addressDTO.getCity();
        address.setProvince(provinceName);
        address.setCity(cityName);

        Long pid = provinceRepository.findPid(provinceName);
        Long cid = cityRepository.findCid(pid,cityName);
        address.setProvincialCity(pid+","+cid);

        address.setDistict(addressDTO.getDistict());
        address.setAddress(addressDTO.getAddress());
        // 设置是否为默认地址
        if(addressDTO.getDefaultAddress() == 1){
            addressRepository.updateDefault(userId);
        }
        address.setDefaultAddress(addressDTO.getDefaultAddress());
        // 使用状态设为开启
        address.setStatus(1);
        addressRepository.save(address);
        return Result.success("地址修改成功");
    }


    /**
     * 设定默认地址
     * @param userId
     * @param aid
     * @return
     */
    public Result defualtAddress(Long userId, Long aid) {
        addressRepository.updateDefault(userId);
        Address address = addressRepository.findById(aid);
        if (address == null){ return Result.fail("地址不存在"); }
        if (!userId.equals(address.getUserId())){ return Result.fail("无权操作"); }
        address.setDefaultAddress(1);
        address.setStatus(1);
        addressRepository.save(address);
        return Result.success("设定默认地址成功");
    }


    public Result getDefaultAddress(Long userId) {

        User user = userRepository.findOne(userId);
        Address address = addressRepository.findByUserIdAndDefaultAddress(userId, 1);
        if (address != null) {
            return Result.success(new SimpleAddressVO(address, user));
        }
        Address lastUpdateAddress = addressRepository.findByLastUpdate(userId);
        if (lastUpdateAddress != null){
            return Result.success(new SimpleAddressVO(lastUpdateAddress, user));
        }
        return Result.success();
    }


}
