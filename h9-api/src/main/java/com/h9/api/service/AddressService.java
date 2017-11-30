package com.h9.api.service;

import com.h9.api.model.dto.AddressDTO;
import com.h9.api.model.dto.Areas;
import com.h9.api.model.vo.AddressVO;
import com.h9.api.model.vo.SimpleAddressVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.*;
import com.h9.common.db.repo.*;

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
    DistictRepository distictRepository;

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
     * 所有省
     * @return
     */
    public Result allProvices() {
        List<Province> allProvices = provinceRepository.findAllProvinces();
        List<Map<String, String>> provinceList = new ArrayList<>();
        if (CollectionUtils.isEmpty(allProvices)){ return Result.success();}
        allProvices.forEach(province -> {
            Map<String, String> pmap = new HashMap<>();
            pmap.put("name", province.getName());
            pmap.put("id", province.getId() + "");
            provinceList.add(pmap);
        });
        return Result.success(provinceList);
    }

    /**
     * 省对应的市
     * @return
     */
    public Result allCities(Long pid) {
        List<City> allCities = cityRepository.findCities(pid);
        List<Map<String, String>> cityList = new ArrayList<>();
        if (CollectionUtils.isEmpty(allCities)){ return Result.success();}
        allCities.forEach(city -> {
            Map<String, String> cmap = new HashMap<>();
            cmap.put("name", city.getName());
            cmap.put("id", city.getId() + "");
            cityList .add(cmap);
        });
        return Result.success(cityList);
    }

    /**
     * 市对应的区
     * @return
     */
    public Result allDisticts(Long cid) {
        List<Distict> allDisticts = distictRepository.findDisticts(cid);
        List<Map<String, String>> distictList = new ArrayList<>();
        if (CollectionUtils.isEmpty(allDisticts)){ return Result.success();}
        allDisticts.forEach(distict -> {
            Map<String, String> dmap = new HashMap<>();
            dmap.put("name", distict.getName());
            dmap.put("id", distict.getCity().getId() + "");
            distictList .add(dmap);
        });
        return Result.success(distictList);
    }

    /**
     * 省市区
     * @return
     */
    public Result allAreas() {

        List<Province> allProvices = provinceRepository.findAllProvinces();
        List<Map<String, String>> provinceList = new ArrayList<>();
        if (CollectionUtils.isEmpty(allProvices)){ return Result.success();}
        allProvices.forEach(province -> {
            Map<String, String> pmap = new HashMap<>();
            pmap.put("name", province.getName());
            pmap.put("id", province.getId() + "");
            provinceList.add(pmap);
        });
        List<City> allCities = cityRepository.findAllCities();
        List<Map<String, String>> cityList = new ArrayList<>();
        if (CollectionUtils.isEmpty(allCities)){ return Result.success();}
        allCities.forEach(city -> {
            Map<String, String> cmap = new HashMap<>();
            cmap.put("name", city.getName());
            cmap.put("id", city.getProvince().getId() + "");
            cityList .add(cmap);
        });

        List[] lists = {provinceList,cityList};
        return Result.success(lists);
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
        address.setProvince(provinceName);
        address.setCity(cityName);

        Long pid = provinceRepository.findPid(provinceName);
        Long cid = cityRepository.findCid(pid,cityName);
        address.setProvincialCity(pid+","+cid);

        address.setDistict(addressDTO.getDistict());
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
        if (!userId.equals(address.getUserId())){ return Result.fail("无权操作"); }
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
      //  addressRepository.updateDefault(userId);
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
        if (lastUpdateAddress != null)
            return Result.success(new SimpleAddressVO(lastUpdateAddress, user));
        return Result.success();
    }
}
