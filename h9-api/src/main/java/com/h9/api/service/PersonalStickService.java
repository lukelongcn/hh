package com.h9.api.service;

import com.h9.api.model.vo.SelfSignVO;
import com.h9.api.model.vo.community.PersonalCommentVO;
import com.h9.api.model.vo.community.PersonalGiveRewardVO;
import com.h9.api.model.vo.community.PersonalStickVO;
import com.h9.api.model.vo.community.StickSearchVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.ConfigService;
import com.h9.common.db.entity.community.Stick;
import com.h9.common.db.entity.community.StickComment;
import com.h9.common.db.entity.community.StickReward;
import com.h9.common.db.repo.StickCommentRepository;
import com.h9.common.db.repo.StickRepository;
import com.h9.common.db.repo.StickRewardResitory;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;

import javax.annotation.Resource;

import static com.h9.common.constant.ParamConstant.JIUYUAN_ICON;

/**
 * Created by 李圆 on 2018/1/19
 */
@Service
public class PersonalStickService {
    @Resource
    private StickCommentRepository stickCommentRepository;
    @Resource
    private StickRepository stickRepository;
    @Resource
    private StickRewardResitory stickRewardResitory;
    @Resource
    private ConfigService configService;
    /**
     * 我回复的
     */
    public Result comment(long userId, Integer page, Integer limit) {
        PageResult<StickComment> pageResult = stickCommentRepository.findCommentList(userId,page,limit);
        if ( pageResult == null) {
            return Result.fail("暂无回复");
        }
        return Result.success(pageResult.result2Result(PersonalCommentVO::new));
    }

    /**
     * 我发布的
     */
    public Result getPush(long userId, Integer page, Integer limit) {
        PageRequest pageRequest = stickRepository.pageRequest(page, limit);
        Page<Stick> stickPage = stickRepository.findPersonalStickList(userId,pageRequest);
        if (stickPage == null) {
            return Result.fail("暂无发布");
        }
        PageResult<PersonalStickVO> pageResult = new PageResult<>(stickPage).result2Result(PersonalStickVO::new);
        return Result.success(pageResult);
    }

    public Result giveReward(long userId, Integer page, Integer limit) {
        PageResult<StickReward> pageResult = stickRewardResitory.findGiveList(userId,page,limit);
        if ( pageResult == null) {
            return Result.fail("暂无打赏记录");
        }
        return Result.success(pageResult.result2Result(this::getGive));
    }
    private PersonalGiveRewardVO getGive(StickReward stickReward){
        PersonalGiveRewardVO personalGiveRewardVO = new PersonalGiveRewardVO(stickReward);
        // 酒元icon
        String icon = configService.getStringConfig(JIUYUAN_ICON);
        personalGiveRewardVO.setIcon(icon);
        return personalGiveRewardVO;
    }
}
