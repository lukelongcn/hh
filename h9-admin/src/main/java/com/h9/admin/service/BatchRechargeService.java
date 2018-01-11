package com.h9.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.h9.admin.model.dto.recharge.BatchRechargeDTO;
import com.h9.admin.model.dto.recharge.BatchRechargeFile;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.RechargeBatch;
import com.h9.common.db.entity.RechargeBatchRecord;
import com.h9.common.db.entity.User;
import com.h9.common.db.repo.RechargeBatchRecordRepository;
import com.h9.common.db.repo.RechargeBatchRepository;
import com.h9.common.db.repo.RechargeRecordRepository;
import com.h9.common.db.repo.UserRepository;
import com.h9.common.utils.POIUtils;
import com.mysql.jdbc.TimeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
            Result result = fileService.fileUpload(file);
            if(result.getCode() == 1){
                return Result.fail("上传失败");
            }

            String filePath = result.getData().toString();
            BatchRechargeFile batchRechargeFile = new BatchRechargeFile(file.getOriginalFilename(), filePath);
            redisBean.setStringValue(cacheId + ":file", JSONObject.toJSONString(batchRechargeFile));
            return Result.success(mapVO);
        } catch (IOException e) {
            logger.info(e.getMessage(), e);
            return Result.fail("上传文件失败");
        }
    }


    public Result batchRecharge(BatchRechargeDTO batchRechargeDTO) {
        String batchNo = batchRechargeDTO.getBatchNo();
        List<RechargeBatch> findList = rechargeBatchRepository.findByBatchNo(batchNo);
        if (CollectionUtils.isNotEmpty(findList)) {
            return Result.fail("批次号重复!");
        }
        String value = redisBean.getStringValue(batchRechargeDTO.getCacheId());

        if (value == null) return Result.fail("cache 不存在,请重新上传");

        List<POIUtils.RechargeBatchObject> rechargeBatchObjectList = JSONObject.parseArray(value, POIUtils.RechargeBatchObject.class);

        String key = batchRechargeDTO.getCacheId() + ":file";
        String fileValue = redisBean.getStringValue(key);
        if (StringUtils.isBlank(fileValue)) {
            return Result.fail("请重新上传");
        }

        BatchRechargeFile batchRechargeFile = JSONObject.parseObject(fileValue, BatchRechargeFile.class);

        //批次
        RechargeBatch rechargeBatch = new RechargeBatch()
                .setBatchNo(batchNo)
                .setRemark(batchRechargeDTO.getRemark())
                .setFileName(batchRechargeFile.getFileName())
                .setFilePath(batchRechargeFile.getFilePath());
        rechargeBatch = rechargeBatchRepository.saveAndFlush(rechargeBatch);
        //批次记录

        Long id = rechargeBatch.getId();
        rechargeBatchObjectList.stream().map(el -> {
            RechargeBatchRecord rechargeBatchRecord = new RechargeBatchRecord();
            String phone = el.getPhone();
            User user = userRepository.findByPhone(phone);
            if (user == null) {
                rechargeBatchRecord.setStatus(3);
            }else{
                rechargeBatchRecord.setPhone(el.getPhone()).setNickName(user.getNickName());
                rechargeBatchRecord.setStatus(3);
            }
            return rechargeBatchRecord.setRechargeBatchId(id)
                    .setMoney(el.getMoney())
                    .setRemarks(el.getRemark());

        }).forEach(el -> rechargeBatchRecordRepository.save(el));

        return Result.success();
    }

    public Result batchList(Integer pageNumber, Integer size) {

        return null;
    }
}
