package com.transfer.service;

import com.h9.common.base.PageResult;
import com.h9.common.db.entity.SystemBlackList;
import com.h9.common.db.repo.SystemBlackListRepository;
import com.h9.common.utils.DateUtil;
import com.transfer.db.entity.T_BlackList;
import com.transfer.db.repo.T_BlackListRepository;
import com.transfer.service.base.BaseService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * BlackListService:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/2
 * Time: 15:46
 */
@Component
public class BlackListService extends BaseService<T_BlackList> {
    
    @Resource
    private SystemBlackListRepository systemBlackListRepository;
    
    @Resource
    private T_BlackListRepository t_blackListRepository;
    
    @Override
    public String getPath() {
        return null;
    }

    @Override
    public PageResult get(int page, int limit) {
        return t_blackListRepository.findAll(page,limit);
    }

    @Override
    public void getSql(T_BlackList t_blackList, BufferedWriter userWtriter) throws IOException {
        SystemBlackList systemBlackList = new SystemBlackList();
        systemBlackList.setStartTime(new Date());
        systemBlackList.setEndTime(DateUtil.getDate(new Date(),6, Calendar.YEAR));
        systemBlackList.setUserId(t_blackList.getUserId());
        systemBlackList.setCause("历史黑名单");
        systemBlackListRepository.save(systemBlackList);
    }

    @Override
    public String getTitle() {
        return "黑名单迁移进度";
    }
}
