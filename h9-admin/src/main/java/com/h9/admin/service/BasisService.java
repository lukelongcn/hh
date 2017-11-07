package com.h9.admin.service;

import com.h9.admin.model.dto.PageDTO;
import com.h9.admin.model.dto.basis.BankTypeAddDTO;
import com.h9.admin.model.dto.basis.GlobalPropertyEditDTO;
import com.h9.common.db.entity.BankType;
import com.h9.common.db.repo.BankTypeRepository;
import com.h9.common.modle.vo.GlobalPropertyVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.GlobalProperty;
import com.h9.common.db.repo.GlobalPropertyRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: George
 * @date: 2017/11/5 14:34
 */
@Service
@Transactional
public class BasisService {

    @Autowired
    private GlobalPropertyRepository globalPropertyRepository;
    @Autowired
    private BankTypeRepository bankTypeRepository;

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

}
