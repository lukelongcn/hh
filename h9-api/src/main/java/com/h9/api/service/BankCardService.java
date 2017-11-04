package com.h9.api.service;

import com.h9.api.model.dto.BankCardDTO;
import com.h9.common.base.Result;
import com.h9.common.db.entity.UserBank;
import com.h9.common.db.repo.BankCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * Created by 李圆
 * on 2017/11/2
 */
@Service
@Transactional
public class BankCardService {

    @Autowired
    private BankCardRepository bankCardRepository;

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
        userBank.setProvice(bankCardDTO.getProvice());
        userBank.setCity(bankCardDTO.getCity());
        userBank.setStatus(bankCardDTO.getStatus());

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
}
