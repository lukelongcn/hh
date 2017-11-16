package com.h9.admin.service;

import com.h9.admin.model.dto.basis.*;
import com.h9.admin.model.vo.StatisticsItemVO;
import com.h9.common.common.ConfigService;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.entity.*;
import com.h9.common.modle.vo.SystemUserVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.repo.*;
import com.h9.common.modle.dto.PageDTO;
import com.h9.common.modle.vo.GlobalPropertyVO;
import com.h9.common.utils.MD5Util;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    @Resource
    private VCoinsFlowRepository vCoinsFlowRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private ConfigService configService;
    public Result<GlobalPropertyVO> addGlobalProperty(GlobalProperty globalProperty){
        if(this.globalPropertyRepository.findByCode(globalProperty.getCode())!=null){
            //TODO 添加至 redis 缓存中
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
        this.configService.expireConfig(globalPropertyEditDTO.getCode());
        return Result.success(GlobalPropertyVO.toGlobalPropertyVO(this.globalPropertyRepository.save(globalProperty)));
    }

    public Result<PageResult<GlobalPropertyVO>> getGlobalProperties(String key,PageDTO pageDTO){
        PageRequest pageRequest = this.globalPropertyRepository.pageRequest(pageDTO.getPageNumber(),pageDTO.getPageSize());
        Page<GlobalProperty> globalProperties = this.globalPropertyRepository.findAll(this.globalPropertyRepository
                .buildActivitySpecification(key),pageRequest);
        Page<GlobalPropertyVO> globalPropertyVOPage = GlobalPropertyVO.toGlobalPropertyVO(globalProperties);
        PageResult<GlobalPropertyVO> pageResult = new PageResult<>(globalPropertyVOPage);
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
        BigDecimal totalVCoins = vCoinsFlowRepository.getGrantVCoins();
        userVCoins = userVCoins == null ? BigDecimal.valueOf(0):userVCoins;
        totalVCoins = totalVCoins == null ? BigDecimal.valueOf(0):totalVCoins;
        List<StatisticsItemVO> list = new ArrayList<>();
        list.add(new StatisticsItemVO("奖金",lotteryCount.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString(),"总奖金（元）"));
        list.add(new StatisticsItemVO("提现金额",withdrawalsCount.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString(),"总提现奖金（元）"));
        list.add(new StatisticsItemVO("V币",totalVCoins.setScale(2,BigDecimal.ROUND_HALF_UP).toString(),"总V币"));
        list.add(new StatisticsItemVO("剩余V币",userVCoins.setScale(2,BigDecimal.ROUND_HALF_UP).toString(),"剩余V币总量"));
        return Result.success(list);
    }

    public Result addUser(SystemUserAddDTO systemUserAddDTO){
        User user = this.userRepository.findByPhone(systemUserAddDTO.getPhone());
        if(user!=null){
            if(user.getIsAdmin()==User.IsAdminEnum.ADMIN.getId()){
                return Result.fail("用户已存在");
            }else{
                user.setStatus(User.IsAdminEnum.ADMIN.getId());
                this.userRepository.save(systemUserAddDTO.toUser(user));
            }
        }else{
            User u = this.userRepository.save(systemUserAddDTO.toUser(user));
            UserAccount userAccount = new UserAccount();
            userAccount.setBalance(BigDecimal.ZERO);
            userAccount.setUserId(u.getId());
            userAccount.setvCoins(BigDecimal.ZERO);
            this.userAccountRepository.save(userAccount);
        }
        return Result.success("成功");
    }

    public Result updateUser(SystemUserEditDTO systemUserEditDTO){
        User user = this.userRepository.findOne(systemUserEditDTO.getId());
        if(user==null){
            return Result.fail("用户不存在");
        }
        if(user.getIsAdmin()!=User.IsAdminEnum.ADMIN.getId()){
            return Result.fail("该用户不是后台用户");
        }
        user.setPassword(MD5Util.getMD5(systemUserEditDTO.getPassword()));
        user.setNickName(systemUserEditDTO.getNickName());
        user.setStatus(systemUserEditDTO.getStatus());
        this.userRepository.save(user);
        return Result.success("成功");
    }

    public Result updateUserStatus(long id){
        User user = this.userRepository.findOne(id);
        if(user==null){
            return Result.fail("用户不存在");
        }
        if(user.getIsAdmin()!=User.IsAdminEnum.ADMIN.getId()){
            return Result.fail("该用户不是后台用户");
        }
        if(user.getStatus()==User.StatusEnum.DISABLED.getId()){
            user.setStatus(User.StatusEnum.ENABLED.getId());
        }else{
            user.setStatus(User.StatusEnum.DISABLED.getId());
        }
        this.userRepository.save(user);
        return Result.success("成功");
    }

    public Result<PageResult<SystemUserVO>> getUsers(PageDTO pageDTO){
        PageRequest pageRequest = this.bankTypeRepository.pageRequest(pageDTO.getPageNumber(),pageDTO.getPageSize());
        Page<SystemUserVO> systemUserVOS = this.userRepository.findAllByPage(pageRequest);
        PageResult<SystemUserVO> pageResult = new PageResult<>(systemUserVOS);
        return Result.success(pageResult);
    }
}
