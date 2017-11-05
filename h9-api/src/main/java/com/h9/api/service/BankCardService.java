package com.h9.api.service;

import com.h9.api.model.dto.BankCardDTO;
import com.h9.common.base.Result;
import com.h9.common.db.entity.BankType;
import com.h9.common.db.entity.UserBank;
import com.h9.common.db.repo.BankCardRepository;
import com.h9.common.db.repo.BankTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 李圆
 * on 2017/11/2
 */
@Service
@Transactional
public class BankCardService {

    @Autowired
    private BankCardRepository bankCardRepository;

    @Resource
    private BankTypeRepository bankTypeRepository;
    /**
     * 添加银行卡
     * @param bankCardDTO
     * @return
     */
    public Result addBankCard(Long userId,BankCardDTO bankCardDTO){

        //判断银行卡号是否已被绑定
        if(bankCardRepository.findByNo(bankCardDTO.getNo())!=null){
            return Result.fail();
        }
        UserBank userBank = new UserBank();
        userBank.setUserId(userId);
        userBank.setName(bankCardDTO.getName());
        userBank.setNo(bankCardDTO.getNo());
        Long typeId = bankCardDTO.getBankTypeId();
        BankType bankType = bankTypeRepository.findOne(typeId);
        if(bankType == null) return Result.fail("此银行类型不存在");
        userBank.setBankType(bankType);
        userBank.setProvice(bankCardDTO.getProvice());
        userBank.setCity(bankCardDTO.getCity());
        userBank.setStatus(1);
        userBank.setBankImg(bankType.getBankImg());

        bankCardRepository.save(userBank);
        return Result.success();
    }

    /**
     * 解绑银行卡
     * @param status 银行状态
     * @return
     */
    public Result updateStatus(Long id,Integer status){
        UserBank userBank = bankCardRepository.findById(id);
        if(userBank!=null){
            userBank.setStatus(status);
            bankCardRepository.save(userBank);
            return Result.success();
        }else{
            return Result.fail();
        }
    }

    public Result allBank() {
        List<BankType> all = bankTypeRepository.findAll();
        List<Map<String, String>> bankVoList = new ArrayList<>();
        if(CollectionUtils.isEmpty(all)) return Result.success();
        all.forEach(bank -> {
            Map<String, String> map = new HashMap<>();
            map.put("name", bank.getBankName());
            map.put("id", bank.getId() + "");
            bankVoList.add(map);
        });
        return Result.success(bankVoList);
    }
}
