package com.h9.api.service;

import com.h9.api.model.dto.BankCardDTO;
import com.h9.common.base.Result;
import com.h9.common.db.entity.BankType;
import com.h9.common.db.entity.UserBank;
import com.h9.common.db.repo.BankCardRepository;
import com.h9.common.db.repo.BankTypeRepository;
import com.h9.common.utils.CharacterFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     *
     * @param bankCardDTO
     * @return
     */
    public Result addBankCard(Long userId, BankCardDTO bankCardDTO) {

        //判断银行卡号是否已被绑定
        UserBank user = bankCardRepository.findByNo(bankCardDTO.getNo());
        if (user != null) {
            if (user.getUserId().equals(userId)) {
                Long typeId = bankCardDTO.getBankTypeId();
                BankType bankType = bankTypeRepository.findOne(typeId);
                if (bankType == null) return Result.fail("此银行类型不存在");
                user.setBankType(bankType);
                user.setProvince(bankCardDTO.getProvice());
                user.setCity(bankCardDTO.getCity());
                user.setStatus(1);
                user.setName(bankCardDTO.getName());
                //设置 为默认银行卡
                UserBank defaultBank = bankCardRepository.getDefaultBank(userId);
                if (defaultBank != null) {
                    defaultBank.setDefaultSelect(0);
                    bankCardRepository.save(defaultBank);
                }
                user.setDefaultSelect(1);
                return Result.success("绑定成功");
            }
            return Result.fail("该卡已被他人绑定");
        }
        UserBank userBank = new UserBank();
        userBank.setUserId(userId);
        userBank.setName(bankCardDTO.getName());
        userBank.setNo(bankCardDTO.getNo());

        if (CharacterFilter.containChinese(bankCardDTO.getNo())) {
            return Result.fail("请输入纯数字的银行卡");
        }

        Long typeId = bankCardDTO.getBankTypeId();
        BankType bankType = bankTypeRepository.findOne(typeId);
        if (bankType == null) return Result.fail("此银行类型不存在");
        userBank.setBankType(bankType);
        userBank.setProvince(bankCardDTO.getProvice());
        userBank.setCity(bankCardDTO.getCity());
        userBank.setStatus(1);

        //设置 为默认银行卡
        UserBank defaultBank = bankCardRepository.getDefaultBank(userId);
        if (defaultBank != null) {
            defaultBank.setDefaultSelect(0);
            bankCardRepository.save(defaultBank);
        }
        userBank.setDefaultSelect(1);
        bankCardRepository.save(userBank);
        return Result.success("绑定成功");
    }

    /**
     * 解绑银行卡
     *
     * @param id
     * @param userId
     * @return
     */
    public Result updateStatus(Long id, Long userId) {
        UserBank userBank = bankCardRepository.findById(id);
        if (userBank == null) {
            return Result.fail("银行卡不存在");
        }
        if (!userId.equals(userBank.getUserId())) {
            return Result.fail("无权操作");
        }
        userBank.setStatus(3);
        bankCardRepository.save(userBank);
        return Result.success("解绑成功");
    }

    /**
     * 银行卡类型列表
     *
     * @return
     */
    public Result allBank() {
        List<BankType> all = bankTypeRepository.findTypeList();
        List<Map<String, String>> bankVoList = new ArrayList<>();
        if (CollectionUtils.isEmpty(all)) return Result.success();
        all.forEach(bank -> {
            Map<String, String> map = new HashMap<>();
            map.put("name", bank.getBankName());
            map.put("id", bank.getId() + "");
            bankVoList.add(map);
        });
        return Result.success(bankVoList);
    }

    @SuppressWarnings("Duplicates")
    public Result getMyBankList(long userId) {
        List<UserBank> userBankList = bankCardRepository.findByUserIdAndStatus(userId, 1);
        List<Map<String, String>> bankList = new ArrayList<>();
        userBankList.stream()
                .sorted((x, y) -> {
                    int defaultSelect = x.getDefaultSelect();
                    if (defaultSelect == 1) {
                        return -1;
                    } else {
                        return 1;
                    }
                })
                .forEach(bank -> {
                    if (bank.getStatus() == 1) {
                        Map<String, String> map = new HashMap<>();
                        map.put("bankImg", bank.getBankType().getBankImg());
                        map.put("name", bank.getBankType().getBankName());
                        String no = bank.getNo();

                        StringBuilder sbNo = new StringBuilder();
                        sbNo.append(no.substring(0, 4));
                        sbNo.append("**** **** ****");
                        sbNo.append(no.substring(no.length() - 4,no.length()));
                        map.put("no", sbNo.toString());
                        map.put("id", bank.getId() + "");
                        map.put("color", bank.getBankType().getColor());
                        bankList.add(map);
                    }

                });
        return Result.success(bankList);
    }
}
