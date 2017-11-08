package com.h9.admin.service;

import com.h9.admin.model.dto.PageDTO;
import com.h9.admin.model.dto.activity.RewardQueryDTO;
import com.h9.admin.model.vo.RewardVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.Activity;
import com.h9.common.db.entity.Reward;
import com.h9.common.db.repo.ActivityRepository;
import com.h9.common.db.repo.RewardRepository;
import com.h9.common.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
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


    public Result<PageResult<RewardVO>> getRewards(RewardQueryDTO rewardQueryDTO){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        PageRequest pageRequest = this.rewardRepository.pageRequest(rewardQueryDTO.getPageNumber(),rewardQueryDTO.getPageSize(),sort);
        Page<Reward> rewards = this.rewardRepository.findAll(this.buildSpecification(rewardQueryDTO),pageRequest);
        PageResult<Reward> pageResult = new PageResult<>(rewards);
        return Result.success(this.toRewardVO(pageResult));
    }

    private Specification<Reward> buildSpecification(RewardQueryDTO rewardQueryDTO){
        return new Specification<Reward>(){
            @Override
            public Predicate toPredicate(Root<Reward> root, CriteriaQuery<?> query, CriteriaBuilder cb){
                List<Predicate> predicates = new ArrayList<>();
                if(!StringUtils.isEmpty(rewardQueryDTO.getCode())){
                    predicates.add(cb.like(root.get("code").as(String.class),"%"+rewardQueryDTO.getCode()+"%"));
                }
                if(rewardQueryDTO.getStartTime()!=null){
                    predicates.add(cb.greaterThanOrEqualTo(root.get("startTime").as(Date.class),rewardQueryDTO.getStartTime()));
                }
                if(rewardQueryDTO.getEndTime()!=null){
                    predicates.add(cb.lessThan(root.get("endTime").as(Date.class),DateUtil.addDays(rewardQueryDTO.getEndTime(),1)));
                }
                Predicate[] pre = new Predicate[predicates.size()];
                return query.where(predicates.toArray(pre)).getRestriction();
            }
        };
    }


    private PageResult<RewardVO> toRewardVO(PageResult rewards){
        List<RewardVO> rewardVOList = new ArrayList<>();
        for(Reward reward:(List<Reward>)rewards.getData()){
            rewardVOList.add(RewardVO.toRewardVO(reward));
        }
        rewards.setData(rewardVOList);
        return rewards;
    }
}
