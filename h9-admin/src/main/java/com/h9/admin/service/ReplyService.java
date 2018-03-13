package com.h9.admin.service;

import com.h9.admin.model.dto.ReplyDTO;
import com.h9.admin.model.vo.ReplyMessageVO;
import com.h9.common.base.Result;
import com.h9.common.common.ConfigService;
import com.h9.common.constant.ParamConstant;
import com.h9.common.db.entity.wxEvent.ReplyMessage;
import com.h9.common.db.repo.ReplyMessageRepository;
import com.h9.common.modle.vo.Config;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李圆 on 2018/1/15
 */
@Service
public class ReplyService {
    @Resource
    ReplyMessageRepository replyMessageRepository;
    @Resource
    ConfigService configService;

    public Result addRule(ReplyDTO replyDTO){
        ReplyMessage replyMessage = new ReplyMessage();
        BeanUtils.copyProperties(replyDTO,replyMessage);
        replyMessage.setStatus(1);
        replyMessageRepository.save(replyMessage);
        return Result.success();
    }

    public Result getRuleType() {
        List<Config> mapListConfig = configService.getMapListConfig(ParamConstant.REPLY_RULE_TYPE);

        if(CollectionUtils.isEmpty(mapListConfig)){
            mapListConfig = new ArrayList<>();
        }
        return Result.success(mapListConfig);
    }

    public Result getReplyType() {
        List<Config> mapListConfig = configService.getMapListConfig(ParamConstant.REPLY_TYPE);

        if(CollectionUtils.isEmpty(mapListConfig)){
            mapListConfig = new ArrayList<>();
        }
        return Result.success(mapListConfig);
    }

    /**
     * 禁用
     */
    public Result disable(long id) {
        ReplyMessage replyMessage = replyMessageRepository.findOne(id);
        if (replyMessage == null){
            return Result.fail("该规则不存在");
        }
        replyMessage.setStatus(2);
        return Result.success("禁用成功");
    }


    /**
     * 详情
     * */
    public Result detail(long id) {
        ReplyMessage replyMessage = replyMessageRepository.findOne(id);
        if (replyMessage == null){
            return Result.fail("该规则不存在");
        }
        ReplyMessageVO messageVO = new ReplyMessageVO(replyMessage);
        return Result.success(messageVO);
    }

    /**
     * 所有规则详情
     */
    public Result all() {
        List<ReplyMessage> replyMessages = replyMessageRepository.findAllDetail();
        if (CollectionUtils.isEmpty(replyMessages)){
            return Result.fail("暂无规则");
        }
        return Result.success(replyMessages);
    }

    /**
     * 编辑
     */
    public Result update(long id, ReplyDTO replyDTO) {
        ReplyMessage replyMessage = replyMessageRepository.findOne(id);
        if (replyMessage == null){
            return Result.fail("该规则不存在");
        }
        BeanUtils.copyProperties(replyMessage,replyDTO);
        replyMessageRepository.saveAndFlush(replyMessage);
        return Result.success("编辑成功");
    }
}
