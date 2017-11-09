package com.h9.admin.service;

import com.h9.admin.model.vo.LotteryFlowVO;
import com.h9.common.db.entity.LotteryFlow;
import com.h9.common.db.repo.LotteryFlowRepository;
import com.h9.common.db.repo.UserRepository;
import com.h9.common.modle.dto.LotteryFlowDTO;
import com.h9.common.modle.dto.RewardQueryDTO;
import com.h9.admin.model.vo.RewardVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.Reward;
import com.h9.common.db.repo.ActivityRepository;
import com.h9.common.db.repo.RewardRepository;
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

    public Result<PageResult<LotteryFlowVO>> getLotteryFlows(LotteryFlowDTO lotteryFlowDTO){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        PageRequest pageRequest = this.lotteryFlowRepository.pageRequest(lotteryFlowDTO.getPageNumber(),lotteryFlowDTO.getPageSize(),sort);
        Page<LotteryFlow> lotteryFlows = this.lotteryFlowRepository.findAll(this.lotteryFlowRepository.buildSpecification(lotteryFlowDTO),pageRequest);
        PageResult<LotteryFlow> pageResult = new PageResult<>(lotteryFlows);
        return Result.success(this.toLotteryFlowVO(pageResult));
    }

    private PageResult<LotteryFlowVO> toLotteryFlowVO(PageResult lotteryFlows){
        List<LotteryFlowVO> rewardVOList = new ArrayList<>();
        for(LotteryFlow lotteryFlow:(List<LotteryFlow>)lotteryFlows.getData()){
            rewardVOList.add(LotteryFlowVO.toLotteryFlowVO(lotteryFlow));
        }
        lotteryFlows.setData(rewardVOList);
        return lotteryFlows;
    }

}
