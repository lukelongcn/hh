package com.h9.api.service;

import com.h9.api.model.dto.BankCardDTO;
import com.h9.common.base.Result;
import com.h9.common.db.entity.UserBank;
import com.h9.common.db.repo.BankCardRepository;
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

    @Resource
    private BankCardRepository bankCardRepository;

    /**
     * 添加银行卡
     * @param bankCardDTO
     * @return
     */
    public Result addBankCard(BankCardDTO bankCardDTO,String name){

        UserBank userBank = new UserBank();
        userBank.setUserId(bankCardDTO.getUser_id());
        userBank.setName(name);
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
        userBank.setStatus(status);
        bankCardRepository.save(userBank);
        return Result.success();
    }
}
