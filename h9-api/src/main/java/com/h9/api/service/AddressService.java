package com.h9.api.service;


import com.h9.api.model.dto.AddressDTO;
import com.h9.api.model.dto.Areas;
import com.h9.api.model.vo.AddressVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.*;
import com.h9.common.db.repo.*;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import java.util.List;

import java.util.stream.Collectors;


/**
 * Created by 李圆 on 2017/11/27
 */
@Service
public class AddressService {

    Logger logger = Logger.getLogger(AddressService.class);

    @Resource
    AddressRepository addressRepository;

    @Resource
    ChinaRepository chinaRepository;

    @Resource
    RedisBean redisBean;


    /**
     * 地址列表
     * @param userId
     * @return
     */
    @Transactional
    public Result allAddress(Long userId, int page, int limit) {
        PageResult<Address> pageResult = addressRepository.findAddressList(userId, page, limit);
        if (pageResult == null){
            return Result.success("该用户没有存储过地址");
        }
        return Result.success(pageResult.result2Result(AddressVO::convert));
    }


    /**
     * 所有地区
     * @return
     */
    public Result allArea(){
        List<Areas> formCahce = findFormCahce();
        if(!CollectionUtils.isEmpty(formCahce)){
            return Result.success(formCahce);
        }
        List<Areas> fromDb = findFromDb();
        if(null == fromDb){
            return Result.fail("暂无数据");
        }
        return Result.success(fromDb);
    }

    @Transactional
    public List<Areas> findFromDb(){
        //从数据库获取数据
        Long startTime = System.currentTimeMillis();
        List<China> allProvices = chinaRepository.findAllProvinces();
        if (CollectionUtils.isEmpty(allProvices)) {
            return null;
        }
        List<Areas> areasList = allProvices.stream().map(Areas::new).collect(Collectors.toList());
        Long end = System.currentTimeMillis();
        logger.debugv("时间"+(end-startTime));
//        存储到redis
        redisBean.setObject(RedisKey.addressKey,areasList);
        return areasList;
    }


    public List<Areas> findFormCahce(){
//        从reids里面取
        return redisBean.getArray(RedisKey.addressKey,Areas.class);
    }


    /**
     * 添加收货地址
     * @param userId
     * @param addressDTO
     * @return
     */
    @Transactional
    public Result addAddress(Long userId,AddressDTO addressDTO){

        Address address = new Address();
        address.setUserId(userId);
        address.setName(addressDTO.getName());
        address.setPhone(addressDTO.getPhone());

        String provinceName = chinaRepository.findName(addressDTO.getPid());
        String cityName = chinaRepository.findName(addressDTO.getCid());
        String areaName = chinaRepository.findName(addressDTO.getAid());
        address.setProvince(provinceName);
        address.setCity(cityName);
        address.setDistict(areaName);
        //设值地址id
        address.setPid(addressDTO.getPid());
        address.setCid(addressDTO.getCid());
        address.setAid(addressDTO.getAid());
        //设值详细地址
        address.setAddress(addressDTO.getAddress());
        // 设置是否为默认地址
        if(addressDTO.getDefaultAddress() == 1){
            addressRepository.updateDefault(userId);
        }
        address.setDefaultAddress(addressDTO.getDefaultAddress());

        // 使用状态设为开启
        address.setStatus(1);
        addressRepository.save(address);
        return Result.success("保存成功");
    }


    /**
     * 修改地址启用状态
     * @param userId
     * @param aid
     * @return
     */
    @Transactional
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
    @Transactional
    public Result updateAddress(Long userId, Long aid,AddressDTO addressDTO) {

        Address address = addressRepository.findById(aid);
        if (address == null){ return Result.fail("地址不存在"); }
        if (!userId.equals(address.getUserId())){ return Result.fail("无权操作"); }

        address.setName(addressDTO.getName());
        address.setPhone(addressDTO.getPhone());

        String provinceName = chinaRepository.findName(addressDTO.getPid());
        String cityName = chinaRepository.findName(addressDTO.getCid());
        String areaName = chinaRepository.findName(addressDTO.getAid());
        address.setProvince(provinceName);
        address.setCity(cityName);
        address.setDistict(areaName);
        address.setPid(addressDTO.getPid());
        address.setCid(addressDTO.getCid());
        address.setAid(addressDTO.getAid());
        //设值详细地址
        address.setAddress(addressDTO.getAddress());

        // 使用状态设为开启
        address.setStatus(1);

        // 设置是否为默认地址
        if(addressDTO.getDefaultAddress() == 1){
            addressRepository.updateElseDefault(userId,aid);
            address.setDefaultAddress(1);
        } else {
            address.setDefaultAddress(0);
        }
        addressRepository.save(address);

        return Result.success("保存成功");
    }

    /**
     * 设定默认地址
     * @param userId
     * @param aid
     * @return
     */
    @Transactional
    public Result defualtAddress(Long userId, Long aid) {
        addressRepository.updateDefault(userId);
        Address address = addressRepository.findById(aid);
        if (address == null){ return Result.fail("地址不存在"); }
        if (!userId.equals(address.getUserId())){ return Result.fail("无权操作"); }
        address.setDefaultAddress(1);
        address.setStatus(1);
        addressRepository.save(address);
        return Result.success("设定成功");
    }


    @Transactional
    public Result getDefaultAddress(Long userId) {

        Address address = addressRepository.findByUserIdAndDefaultAddressAndStatus(userId, 1,1);
        if (address != null) {
            return Result.success(new AddressVO(address));
        }
        Address lastUpdateAddress = addressRepository.findByLastUpdate(userId);
        if (lastUpdateAddress != null){
            return Result.success(new AddressVO(lastUpdateAddress));
        }

        return Result.fail("",1);
    }


    @Transactional
    public Result getDetailAddress(Long userId, Long id) {
        Address address = addressRepository.findById(id);
        if (address == null){ return Result.fail("地址不存在"); }
        if (!userId.equals(address.getUserId())){ return Result.fail("无权操作"); }
        return Result.success(address);
    }
}
