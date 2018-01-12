package com.h9.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.h9.admin.model.dto.recharge.BatchRechargeDTO;
import com.h9.admin.model.dto.recharge.BatchRechargeFile;
import com.h9.admin.model.vo.RechargeBatchList;
import com.h9.admin.model.vo.RechargeBatchListVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.common.ConfigService;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.*;
import com.h9.common.db.repo.*;
import com.h9.common.utils.DateUtil;
import com.h9.common.utils.MoneyUtils;
import com.h9.common.utils.POIUtils;
import com.mysql.jdbc.TimeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.h9.common.db.entity.BalanceFlow.BalanceFlowTypeEnum.BATCH_RECHARGE;

/**
 * Created by itservice on 2018/1/11.
 */
@Service
public class BatchRechargeService {
    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private RechargeBatchRepository rechargeBatchRepository;
    @Resource
    private RechargeBatchRecordRepository rechargeBatchRecordRepository;
    @Resource
    private FileService fileService;
    @Resource
    private UserRepository userRepository;
    @Resource
    private RedisBean redisBean;
    @Resource
    private CommonService commonService;
    @Resource
    private UserAccountRepository userAccountRepository;

    @Resource
    private ConfigService configService;

    public Result batchRechargeFile(MultipartFile file) {

        if (file == null) {
            return Result.fail("请选择要导入文件)");
        }

        InputStream is = null;
        try {
            is = file.getInputStream();
            List<POIUtils.RechargeBatchObject> rechargeBatchObjects = POIUtils.readExcel(is);
            Map<String, Object> mapVO = new HashMap<>();
            String cacheId = RedisKey.getBatchRechargeCacheId();
            mapVO.put("cacheId", cacheId);
            mapVO.put("rechargeData", rechargeBatchObjects);

            redisBean.setStringValue(cacheId, JSONObject.toJSONString(rechargeBatchObjects), 1, TimeUnit.DAYS);
//            Result result = fileService.fileUpload(file);
            String date = DateUtil.formatDate(new Date(), DateUtil.FormatType.SECOND2) + new Random(new Date().getTime()).nextInt();
            String key = date + "/" + file.getOriginalFilename();
            Result result = fileService.upload(file, key);
            if (result.getCode() == 1) {
                return Result.fail("上传失败");
            }
            String filePath = result.getData().toString();
            System.out.println(filePath);
            BatchRechargeFile batchRechargeFile = new BatchRechargeFile(file.getOriginalFilename(), filePath);
            redisBean.setStringValue(cacheId + ":file", JSONObject.toJSONString(batchRechargeFile));
            return Result.success(mapVO);
        } catch (IOException e) {
            logger.info(e.getMessage(), e);
            return Result.fail("上传文件失败");
        }
    }


    public Result importBatchRecharge(BatchRechargeDTO batchRechargeDTO, Long userId) {
        String batchNo = batchRechargeDTO.getBatchNo();
        List<RechargeBatch> findList = rechargeBatchRepository.findByBatchNo(batchNo);
        if (CollectionUtils.isNotEmpty(findList)) {
            return Result.fail("批次号重复!");
        }
        String value = redisBean.getStringValue(batchRechargeDTO.getCacheId());

        if (value == null) return Result.fail("cache 不存在,请重新上传");
        redisBean.setStringValue(batchRechargeDTO.getCacheId(), "", 1, TimeUnit.MILLISECONDS);
        List<POIUtils.RechargeBatchObject> rechargeBatchObjectList = JSONObject.parseArray(value, POIUtils.RechargeBatchObject.class);

        String key = batchRechargeDTO.getCacheId() + ":file";
        String fileValue = redisBean.getStringValue(key);
        if (StringUtils.isBlank(fileValue)) {
            return Result.fail("请重新上传");
        }
        redisBean.setStringValue(key, "", 1, TimeUnit.MILLISECONDS);

        BatchRechargeFile batchRechargeFile = JSONObject.parseObject(fileValue, BatchRechargeFile.class);

        //批次
        RechargeBatch rechargeBatch = new RechargeBatch()
                .setBatchNo(batchNo)
                .setRemark(batchRechargeDTO.getRemark())
                .setFileName(batchRechargeFile.getFileName())
                .setFilePath(batchRechargeFile.getFilePath())
                .setOptUserId(userId);
        rechargeBatch = rechargeBatchRepository.saveAndFlush(rechargeBatch);
        //批次记录
        Long id = rechargeBatch.getId();
        rechargeBatchObjectList.stream().map(el -> {
            RechargeBatchRecord rechargeBatchRecord = new RechargeBatchRecord();
            String phone = el.getPhone();
            User user = userRepository.findByPhone(phone);
            if (user == null) {
                rechargeBatchRecord.setStatus(RechargeBatchRecord.RechargeStatusEnum.NOT_USER.getCode());
                rechargeBatchRecord.setPhone(el.getPhone());
            } else {
                rechargeBatchRecord.setPhone(el.getPhone()).setNickName(user.getNickName());
                rechargeBatchRecord.setStatus(RechargeBatchRecord.RechargeStatusEnum.NOT_RECHARGE.getCode());
            }
            return rechargeBatchRecord.setRechargeBatchId(id)
                    .setMoney(el.getMoney())
                    .setRemarks(el.getRemark())
                    .setBatchNo(batchNo);

        }).forEach(el -> rechargeBatchRecordRepository.save(el));

        return Result.success();
    }

    public Result batchList(Integer pageNumber, Integer size) {
        PageRequest pageRequest = rechargeBatchRepository.pageRequest(pageNumber, size);
        Page<RechargeBatch> all = rechargeBatchRepository.findAll(pageRequest);
        Page<RechargeBatchList> rechargeBatchLists = all.map(el -> {
            List<RechargeBatchRecord> rechargeBatchRecordList = rechargeBatchRecordRepository.findByRechargeBatchId(el.getId());

            int rechargeCount = 0;
            BigDecimal money = new BigDecimal(0);
            BigDecimal totalMoney = new BigDecimal(0);
            for (RechargeBatchRecord rr : rechargeBatchRecordList) {

                totalMoney = totalMoney.add(rr.getMoney());
                if (rr.getStatus() == 1) {
                    rechargeCount++;
                    money = money.add(rr.getMoney());
                }
            }

            String moneyRate = MoneyUtils.formatMoney(money, "0") + " / " + MoneyUtils.formatMoney(totalMoney, "0");
            RechargeBatchList rechargeBatchList = new RechargeBatchList(el, rechargeCount + " / " + rechargeBatchRecordList.size(), moneyRate);
            return rechargeBatchList;
        });

        return Result.success(new PageResult<>(rechargeBatchLists));
    }

    public Result rechargeRecordList(Long batchId) {
        List<RechargeBatchListVO> rechargeBatchListVOList = rechargeBatchRecordRepository
                .findAll(Example.of(new RechargeBatchRecord().setRechargeBatchId(batchId).setStatus(null)))
                .stream()
                .map(el -> new RechargeBatchListVO(el)).collect(Collectors.toList());

        return Result.success(rechargeBatchListVOList);
    }

    public Result recharge(List<Long> ids, Long userId) {
        if (CollectionUtils.isEmpty(ids)) {
            return Result.fail("请传入ids");
        }
        ids.forEach(id -> {
            RechargeBatchRecord rechargeBatchRecord = rechargeBatchRecordRepository.findOne(id);
            if (rechargeBatchRecord != null
                    && rechargeBatchRecord.getStatus() == RechargeBatchRecord.RechargeStatusEnum.NOT_RECHARGE.getCode()) {
                User user = userRepository.findByPhone(rechargeBatchRecord.getPhone());
                if (user != null) {
//                    UserAccount userAccount = userAccountRepository.findByUserId(user.getId());
//                    if(userAccount != null){
//                        BigDecimal balance = userAccount.getBalance();
//                        balance = balance.add(rechargeBatchRecord.getMoney());
//                        userAccount.setBalance(balance);
                    commonService.setBalance(userId, rechargeBatchRecord.getMoney(), BATCH_RECHARGE.getId(), null, "", BATCH_RECHARGE.getName());
                    rechargeBatchRecord.setStatus(RechargeBatchRecord.RechargeStatusEnum.RECHARGE.getCode());
                    rechargeBatchRecord.setOptUserId(userId);
//                    userAccountRepository.save(userAccount);
                    rechargeBatchRecordRepository.save(rechargeBatchRecord);
//                    }
                }
            }
        });
        return Result.success();
    }

    public Result templateUrl() {
        String url = "https://cdn-h9.thy360.com//2018011212310080969775/template.xlsx";
        String downUrl = configService.getStringConfig("downloadTemplate");
        if (StringUtils.isBlank(downUrl)) {
            logger.info("模块下载参数未配置");
            return Result.fail("模块下载参数未配置");
        }
        return Result.success(downUrl);
    }

}
