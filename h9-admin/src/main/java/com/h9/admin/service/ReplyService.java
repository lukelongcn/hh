package com.h9.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.h9.admin.model.dto.ReplyDTO;
import com.h9.admin.model.dto.WXReplySearchDTO;
import com.h9.admin.model.vo.ReplyMessageVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.ConfigService;
import com.h9.common.constant.ParamConstant;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.wxEvent.ReplyMessage;
import com.h9.common.db.repo.ReplyMessageRepository;
import com.h9.common.modle.vo.Config;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.h9.common.db.entity.wxEvent.ReplyMessage.matchStrategyEnum.*;

/**
 * Created by 李圆 on 2018/1/15
 */
@Service
public class ReplyService {


    Logger logger = Logger.getLogger(ReplyService.class);

    @Value("${wechat.js.appid}")
    private String jsAppId;
    @Value("${wechat.js.secret}")
    private String jsSecret;

    @Resource
    ReplyMessageRepository replyMessageRepository;
    @Resource
    ConfigService configService;

    public Result addRule(ReplyDTO replyDTO){
        ReplyMessage replyMessage = new ReplyMessage();
        replyMessage.setStatus(1);
        replyMessage.setKeyWord(replyDTO.getKeyWord());
        replyMessage.setContentType(replyDTO.getContentType());
        replyMessage.setOrderName(replyDTO.getOrderName());
        replyMessage.setContent(replyDTO.getContent());
        replyMessage.setMatchStrategy(replyDTO.getMatchStrategy());
        replyMessage.setSort(replyDTO.getSort());
        replyMessageRepository.saveAndFlush(replyMessage);
        return Result.success("添加成功");
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
     * 禁用或启用
     */
    public Result disable(long id) {
        ReplyMessage replyMessage = replyMessageRepository.findOne(id);
        if (replyMessage == null){
            return Result.fail("该规则不存在");
        }
        if (replyMessage.getStatus() == 1){
            replyMessage.setStatus(2);
            replyMessageRepository.saveAndFlush(replyMessage);
            return Result.success("禁用成功");
        } else {
            replyMessage.setStatus(1);
            replyMessageRepository.saveAndFlush(replyMessage);
            return Result.success("启用成功");
        }
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
     * @param page
     * @param limit
     */
    public Result all(Integer page, Integer limit) {
        PageResult<ReplyMessage> replyMessages = replyMessageRepository.findAllDetail(page,limit);
        if (replyMessages == null){
            return Result.fail("暂无规则");
        }
        return Result.success(replyMessages.result2Result(ReplyMessageVO::new));
    }

    /**
     * 编辑
     */
    public Result update(long id, ReplyDTO replyDTO) {
        ReplyMessage replyMessage = replyMessageRepository.findOne(id);
        if (replyMessage == null){
            return Result.fail("该规则不存在");
        }
        replyMessage.setStatus(replyDTO.getStatus());
        replyMessage.setKeyWord(replyDTO.getKeyWord());
        replyMessage.setContentType(replyDTO.getContentType());
        replyMessage.setOrderName(replyDTO.getOrderName());
        replyMessage.setContent(replyDTO.getContent());
        replyMessage.setMatchStrategy(replyDTO.getMatchStrategy());
        replyMessage.setSort(replyDTO.getSort());

        replyMessageRepository.saveAndFlush(replyMessage);
        return Result.success("编辑成功");
    }

    public Result<PageResult<ReplyMessageVO>> replyMessageList(WXReplySearchDTO wxReplySearchDTO) {

        Specification<ReplyMessage> specification = getReplyMessageSpecification(wxReplySearchDTO);
        Sort sort = new Sort( Sort.Direction.DESC,"id");
        PageRequest pageRequest = replyMessageRepository.pageRequest(wxReplySearchDTO.getPage(), wxReplySearchDTO.getLimit(),sort);
        Page<ReplyMessage> replyMessagesPage = replyMessageRepository.findAll(specification, pageRequest);
        PageResult<ReplyMessageVO> pageResult = new PageResult<>(replyMessagesPage).result2Result(el -> new ReplyMessageVO(el));

        return Result.success(pageResult);
    }

    private Specification<ReplyMessage> getReplyMessageSpecification(WXReplySearchDTO wxReplySearchDTO){
        return (root, query, builder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            String orderName = wxReplySearchDTO.getOrderName();
            if (StringUtils.isNotBlank(orderName)) {
                predicateList.add(builder.equal(root.get("orderName"), orderName));
            }

            Integer matchStrategy = wxReplySearchDTO.getMatchStrategy();
            if (matchStrategy != null) {
                predicateList.add(builder.equal(root.get("matchStrategy"), matchStrategy));
            }

            Integer status = wxReplySearchDTO.getStatus();

            if (status != null) {
                predicateList.add(builder.equal(root.get("status"), status));
            }

            return query.where(predicateList.toArray(new Predicate[predicateList.size()])).getRestriction();
        };

    }

    @Resource
    private RedisBean redisBean;

    public String getWeChatAccessToken() {
        String access_token = redisBean.getStringValue(RedisKey.wechatAccessToken);
        if (!StringUtils.isEmpty(access_token)) {
            return access_token;
        }
        try {
            RestTemplate restTemplate = new RestTemplate();
            String accessTokenReponse = restTemplate.getForObject(getTicketTokenUrl(), String.class);
            Map<String, Object> map = JSONObject.parseObject(accessTokenReponse, Map.class);
            access_token = (String) map.get("access_token");
            Integer expires_in = (Integer) map.get("expires_in");
            if (!StringUtils.isEmpty(access_token)) {
                redisBean.setStringValue(RedisKey.wechatAccessToken, access_token, expires_in - 60, TimeUnit.SECONDS);
                return access_token;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.debug("微信获取access_token失败", ex);
        }
        return null;
    }


    public String getTicketTokenUrl() {
        return MessageFormat.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}", jsAppId, jsSecret);
    }
}
