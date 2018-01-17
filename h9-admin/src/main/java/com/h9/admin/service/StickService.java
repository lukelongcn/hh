package com.h9.admin.service;

import com.h9.admin.model.dto.stick.StickDTO;
import com.h9.admin.model.dto.stick.StickTypeDTO;
import com.h9.admin.model.dto.stick.UpdateStickDTO;
import com.h9.admin.model.vo.StickCommentSimpleVO;
import com.h9.admin.model.vo.StickCommentVO;
import com.h9.admin.model.vo.StickDetailVO;
import com.h9.admin.model.vo.StickReportVO;
import com.h9.admin.model.vo.StickRewardVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.db.entity.community.Stick;
import com.h9.common.db.entity.community.StickComment;
import com.h9.common.db.entity.community.StickReport;
import com.h9.common.db.entity.community.StickType;
import com.h9.common.db.entity.community.StickReward;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserAccount;
import com.h9.common.db.repo.StickCommentRepository;
import com.h9.common.db.repo.StickReportRepository;
import com.h9.common.db.repo.StickRepository;
import com.h9.common.db.repo.StickRewardResitory;
import com.h9.common.db.repo.StickTypeRepository;
import com.h9.common.db.repo.UserAccountRepository;
import com.h9.common.db.repo.UserRepository;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * StickService:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/29
 * Time: 17:18
 */
@Component
public class StickService {
    
    @Resource
    private StickTypeRepository stickTypeRepository;
    @Resource
    private StickReportRepository stickReportRepository;
    @Resource
    private StickRewardResitory stickRewardResitory;
    @Resource
    private UserRepository userRepository;
    @Resource
    private StickRepository stickRepository;
    @Resource
    private StickCommentRepository stickCommentRepository;
    @Resource
    private UserAccountRepository userAccountRepository;

    public Result addStickType(StickTypeDTO stickTypeDTO){
        String name = stickTypeDTO.getName();
        StickType type = stickTypeRepository.findByName(name);
        if(type!=null){
            return Result.fail(name+"已经存在");
        }
        StickType stickType = new StickType();
        BeanUtils.copyProperties(stickTypeDTO,stickType,"id");
        stickTypeRepository.save(stickType);
        return Result.success();
    }


    public Result getStick(int page,int limit){
       return Result.success(stickTypeRepository.findAll(page, limit));
    }


    public Result getReport(Integer page, Integer limit) {
        PageResult<StickReport> pageResult = stickReportRepository.findReportList(page, limit);
        if (pageResult == null){
            return Result.success("暂无举报");
        }
        return Result.success(pageResult.result2Result(StickReportVO::new));
    }

    /**
     * 拿到打赏记录
     */
    public Result getReward(Integer page, Integer limit, long stickId) {
        PageResult<StickReward> pageResult = stickRewardResitory.findRewardList(stickId,page, limit);
        if (pageResult == null){
            return Result.success("暂无打赏记录");
        }
        return Result.success(pageResult.result2Result(StickRewardVO::new));
    }

    /**
     * 添加马甲贴子
     */
    public Result addStick(StickDTO stickDto) {
        StickType stickType = stickTypeRepository.findOne(stickDto.getTypeId());
        if(stickType == null){
            return Result.fail("请选择分类");
        }
        Stick stick = new Stick();
        User user = userRepository.findOne(stickDto.getUserId());
        stick.setTitle(stickDto.getTitle());
        stick.setContent(stickDto.getContent());
        stick.setStickType(stickType);
        stick.setUser(user);
        Stick s= stickRepository.saveAndFlush(stick);
        return Result.success("添加成功");
    }

    /**
     *  评论列表
     * @return R
     */
    public Result getComment(Integer page, Integer limit, long stickId) {
            PageResult<StickComment> pageResult = stickCommentRepository.findCommentList(stickId,page, limit);
            if (pageResult == null){
                return Result.success("暂无评论");
            }
            return Result.success(pageResult.result2Result(this::stickComent2Vo));
    }

    private StickCommentVO stickComent2Vo(StickComment stickComment) {
        // 拿到回复的回复列表
        List<StickCommentSimpleVO> stickCommentSimpleVOS = new ArrayList<>();
        long stickCommentParentId = stickComment.getId();
        List<StickComment> stickCommentChild= stickCommentRepository.findByBackId(stickCommentParentId);
        if (CollectionUtils.isNotEmpty(stickCommentChild)){
            stickCommentSimpleVOS = stickCommentChild.stream().map(StickCommentSimpleVO::new).collect(Collectors.toList());
        }
        return new StickCommentVO(stickComment,stickCommentSimpleVOS);
    }

    /**
     * 编辑
     */
    @Transactional
    public Result updateStick(long stickId, UpdateStickDTO updateStickDTO) {
        StickType stickType = stickTypeRepository.findOne(updateStickDTO.getTypeId());
        if(stickType == null){
            return Result.fail("请选择分类");
        }
        Stick stick = stickRepository.findById(stickId);
        if (stick == null){
            return Result.fail("贴子不存在或已被删除");
        }
        stick.setTitle(updateStickDTO.getTitle());
        stick.setContent(updateStickDTO.getContent());
        stick.setStickType(stickType);
        Stick s= stickRepository.saveAndFlush(stick);
        return Result.success("编辑成功");
    }


    /**
     * 删除帖子
     */
    public Result delete(long stickId) {
        Stick stick = stickRepository.findById(stickId);
        if (stick == null){
            return Result.fail("帖子已被删除或禁用");
        }
        stick.setState(3);
        stickRepository.save(stick);
        return Result.success("删除成功");
    }

    /**
     *  拿到所有贴子详情
     * @return Result
     */
    public Result allDetail(Integer pageNumber, Integer pageSize) {
        PageResult<Stick> sticklist = stickRepository.findAll(pageNumber,pageSize);
        if (sticklist == null){
            return Result.fail("暂无贴子");
        }
        return Result.success(sticklist.result2Result(this::stickDetail2Vo));
    }

    private StickDetailVO stickDetail2Vo(Stick stick) {
        UserAccount userAccount = userAccountRepository.findByUserId(stick.getUser().getId());

        StickDetailVO stickDetailVO = new StickDetailVO(stick);
        stickDetailVO.setRewardMoney(userAccount.getRewardMoney());
        return stickDetailVO;
    }

}
