package com.h9.admin.service;

import com.h9.common.db.entity.WithdrawalsRecord;
import com.h9.common.db.repo.*;
import com.h9.common.modle.dto.PageDTO;
import com.h9.admin.model.dto.basis.BankTypeAddDTO;
import com.h9.admin.model.dto.basis.BankTypeEditDTO;
import com.h9.admin.model.dto.basis.GlobalPropertyEditDTO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.BankType;
import com.h9.common.db.entity.GlobalProperty;
import com.h9.common.modle.vo.GlobalPropertyVO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: George
 * @date: 2017/11/5 14:34
 */
@Service
@Transactional
public class BasisService {

    @Resource
    private GlobalPropertyRepository globalPropertyRepository;
    @Resource
    private BankTypeRepository bankTypeRepository;
    @Resource
    private LotteryFlowRepository lotteryFlowRepository;
    @Resource
    private WithdrawalsRecordRepository withdrawalsRecordRepository;
    @Resource
    private UserAccountRepository userAccountRepository;
    public Result<GlobalPropertyVO> addGlobalProperty(GlobalProperty globalProperty){
        if(this.globalPropertyRepository.findByCode(globalProperty.getCode())!=null){
            return Result.fail("标识已存在");
        }
        return Result.success(GlobalPropertyVO.toGlobalPropertyVO(this.globalPropertyRepository.save(globalProperty)));
    }

    public Result<GlobalPropertyVO> updateGlobalProperty(GlobalPropertyEditDTO globalPropertyEditDTO){
        GlobalProperty globalProperty = this.globalPropertyRepository.findOne(globalPropertyEditDTO.getId());
        if(globalProperty==null){
            return Result.fail("参数不存在");
        }
        if(this.globalPropertyRepository.findByIdNotAndCode(globalPropertyEditDTO.getId(),globalPropertyEditDTO.getCode())!=null){
            return Result.fail("标识已存在");
        }
        BeanUtils.copyProperties(globalPropertyEditDTO,globalProperty);
        return Result.success(GlobalPropertyVO.toGlobalPropertyVO(this.globalPropertyRepository.save(globalProperty)));
    }

    public Result<PageResult<GlobalPropertyVO>> getGlobalProperties(PageDTO pageDTO){
        PageRequest pageRequest = this.globalPropertyRepository.pageRequest(pageDTO.getPageNumber(),pageDTO.getPageSize());
        Page<GlobalPropertyVO> globalPropertys = this.globalPropertyRepository.findAllByPage(pageRequest);
        PageResult<GlobalPropertyVO> pageResult = new PageResult<>(globalPropertys);
        return Result.success(pageResult);
    }

    public Result deleteGlobalProperty(long globalPropertyId){
        this.globalPropertyRepository.delete(globalPropertyId);
        return Result.success();
    }

    public Result<BankType> addBankType(BankTypeAddDTO bankTypeAddDTO){
        if(this.bankTypeRepository.findByBankName(bankTypeAddDTO.getBankName())!=null){
            return Result.fail("银行已存在");
        }
        return Result.success(this.bankTypeRepository.save(bankTypeAddDTO.toBankType()));
    }

    public Result<BankType> updateBankType(BankTypeEditDTO bankTypeEditDTO){
        if(this.bankTypeRepository.findByIdNotAndBankName(bankTypeEditDTO.getId(),bankTypeEditDTO.getBankName())!=null){
            return Result.fail("银行已存在");
        }
        BankType bankType = this.bankTypeRepository.findOne(bankTypeEditDTO.getId());
        if(bankType==null){
            return Result.fail("银行不存在");
        }
        BeanUtils.copyProperties(bankTypeEditDTO,bankType);
        return Result.success(this.bankTypeRepository.save(bankType));
    }

    public Result<BankType> updateBankTypeStatus(long id){
        BankType bankType = this.bankTypeRepository.findOne(id);
        if(bankType==null){
            return Result.fail("银行不存在");
        }
        if(bankType.getStatus()==BankType.StatusEnum.DISABLED.getId()){
            bankType.setStatus(BankType.StatusEnum.ENABLED.getId());
        }else{
            bankType.setStatus(BankType.StatusEnum.DISABLED.getId());
        }
        return Result.success(this.bankTypeRepository.save(bankType));
    }

    public Result<PageResult<BankType>> getBankTypes(PageDTO pageDTO){
        PageRequest pageRequest = this.bankTypeRepository.pageRequest(pageDTO.getPageNumber(),pageDTO.getPageSize());
        Page<BankType> bankTypes = this.bankTypeRepository.findAllByPage(pageRequest);
        PageResult<BankType> pageResult = new PageResult<>(bankTypes);
        return Result.success(pageResult);
    }

    public Result statisticsLottery() {
        BigDecimal lotteryCount = lotteryFlowRepository.getLotteryCount();
        BigDecimal withdrawalsCount = withdrawalsRecordRepository.getWithdrawalsCount(WithdrawalsRecord.statusEnum.FINISH.getCode());
        BigDecimal userVCoins = userAccountRepository.getUserVCoins();
        BigDecimal totalVCoins = BigDecimal.valueOf(66666);
        Map<String,String> map = new HashMap<>();
        map.put("lotteryCount",lotteryCount.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        map.put("withdrawalsCount",withdrawalsCount.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        map.put("surplus",userVCoins.toString());
        map.put("totalVCoins",totalVCoins.toString());
        return Result.success(map);
    }
}
