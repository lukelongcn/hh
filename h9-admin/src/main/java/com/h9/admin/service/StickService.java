package com.h9.admin.service;

import com.h9.admin.model.dto.stick.StickTypeDTO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.community.Stick;
import com.h9.common.db.entity.community.StickType;
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

    public Result addStickType(StickTypeDTO stickTypeDTO){
        StickType stickType = new StickType();
        BeanUtils.copyProperties(stickTypeDTO,stickType,"id");
        stickTypeRepository.save(stickType);
        return Result.success();
    }


    public Result getStick(int page,int limit){
       return Result.success(stickTypeRepository.findAll(page, limit));
    }



    
}
