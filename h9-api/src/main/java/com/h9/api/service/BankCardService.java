package com.h9.api.service;

import com.h9.api.model.dto.BankCardDTO;
import com.h9.common.base.Result;
import com.h9.common.db.entity.UserBank;
import com.h9.common.db.repo.BankCardRepository;
import com.h9.common.db.repo.BankTypeRepository;
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

    @Autowired
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
        userBank.setBankType(bankTypeRepository.findByBankName(bankCardDTO.getName()));
        userBank.setProvice(bankCardDTO.getProvice());
        userBank.setCity(bankCardDTO.getCity());
        userBank.setStatus(bankCardDTO.getStatus());

        bankCardRepository.save(userBank);
        return Result.success();
    }

    /**
     * 解绑银行卡
     * @param id
     * @param userId
     * @return
     */
    public Result updateStatus(Long id,Long userId){
        UserBank userBank = bankCardRepository.findById(id);
        if(userBank == null) {
            return Result.fail("银行卡不存在");
        }
        if (userId.equals(userBank.getUserId())) {
            return Result.fail("用户名不一致");
        }
        userBank.setStatus(3);
        bankCardRepository.save(userBank);
        return Result.success();
    }
}
