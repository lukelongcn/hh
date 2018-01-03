package com.h9.admin.service;

import com.h9.admin.model.vo.LotteryFlowActivityVO;
import com.h9.common.db.entity.lottery.Lottery;
import com.h9.common.db.repo.*;
import com.h9.common.modle.dto.LotteryFlowActivityDTO;
import com.h9.common.modle.dto.RewardQueryDTO;
import com.h9.admin.model.vo.RewardVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.lottery.Reward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: George
 * @date: 2017/11/7 19:40
 */
@Service
@Transactional
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private RewardRepository rewardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LotteryFlowRepository lotteryFlowRepository;
    @Autowired
    private LotteryRepository lotteryRepository;



    public Result<PageResult<RewardVO>> getRewards(RewardQueryDTO rewardQueryDTO){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        PageRequest pageRequest = this.rewardRepository.pageRequest(rewardQueryDTO.getPageNumber(),rewardQueryDTO.getPageSize(),sort);
        Page<Reward> rewards = this.rewardRepository.findAllReward(rewardQueryDTO,pageRequest);
        PageResult<Reward> pageResult = new PageResult<>(rewards);
        return Result.success(this.toRewardVO(pageResult));
    }

    private PageResult<RewardVO> toRewardVO(PageResult rewards){
        List<RewardVO> rewardVOList = new ArrayList<>();
        for(Reward reward:(List<Reward>)rewards.getData()){
            rewardVOList.add(RewardVO.toRewardVO(reward));
        }
        rewards.setData(rewardVOList);
        return rewards;
    }

    public Result<PageResult<LotteryFlowActivityVO>> getLotteryFlows(LotteryFlowActivityDTO lotteryFlowActivityDTO){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Page<Lottery> lotteryFlows = this.lotteryRepository.findAll(this.lotteryRepository
                .buildActivitySpecification(lotteryFlowActivityDTO),lotteryFlowActivityDTO.toPageRequest(sort));
        PageResult<Lottery> pageResult = new PageResult<>(lotteryFlows);
        return Result.success(pageResult.result2Result(LotteryFlowActivityVO::new));
    }


}
