package com.h9.api.service;

import com.h9.api.model.dto.AddressDTO;
import com.h9.common.base.Result;
import com.h9.common.db.entity.Address;
import com.h9.common.db.entity.City;
import com.h9.common.db.entity.Province;
import com.h9.common.db.repo.AddressRepository;
import com.h9.common.db.repo.CityRepository;
import com.h9.common.db.repo.ProvinceRepository;

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


    /**
     * 地址列表
     * @param userId
     * @return
     */
    public Result allAddress(Long userId) {
        List<Address>   allAddress = addressRepository.findAddressList(userId);
        if (CollectionUtils.isEmpty(allAddress))
            return Result.success("该用户没有存储过地址");
        return Result.success(allAddress);
    }

    /**
     * 所有省
     * @return
     */
    public Result allProvices() {
        List<Province> allProvices = provinceRepository.findAllProvinces();
        List<Map<String, String>> provinceList = new ArrayList<>();
        if (CollectionUtils.isEmpty(allProvices)) return Result.success();
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
        List<City> allCities = cityRepository.findAllCities(pid);
        List<Map<String, String>> cityList = new ArrayList<>();
        if (CollectionUtils.isEmpty(allCities)) return Result.success();
        allCities.forEach(city -> {
            Map<String, String> cmap = new HashMap<>();
            cmap.put("name", city.getName());
            cmap.put("id", city.getId() + "");
            cityList .add(cmap);
        });
        return Result.success(cityList);
    }

    public Result addAddress(Long userId,AddressDTO addressDTO){
        //List<Address>   allAddress = addressRepository.findAddressList(userId);
        //if(allAddress == null)
        Address address = new Address();
        address.setUserId(addressDTO.getUserId());
        address.setName(addressDTO.getName());
        address.setPhone(addressDTO.getPhone());

        String provinceName = addressDTO.getProvince();
        String cityName = addressDTO.getCity();
        address.setProvince(provinceName);
        address.setCity(cityName);

        Long pid = provinceRepository.findByName(provinceName);
        Long cid = cityRepository.findCid(pid,cityName);
        address.setProvincialCity(pid+","+cid);

        //address.setDistict(addressDTO.getDistict());
        address.setAddress(addressDTO.getAddress());
        addressRepository.save(address);
        return Result.success("地址添加成功");
    }


}
