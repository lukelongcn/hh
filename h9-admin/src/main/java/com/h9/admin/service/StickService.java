package com.h9.admin.service;

import com.h9.admin.model.dto.stick.StickTypeDTO;
import com.h9.admin.model.vo.StickReportVO;
import com.h9.admin.model.vo.UserAdviceVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.community.Stick;
import com.h9.common.db.entity.community.StickReport;
import com.h9.common.db.entity.community.StickType;
import com.h9.common.db.entity.user.UserAdvice;
import com.h9.common.db.repo.StickReportRepository;
import com.h9.common.db.repo.StickTypeRepository;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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
}
