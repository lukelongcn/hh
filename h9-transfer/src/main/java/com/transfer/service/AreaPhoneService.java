package com.transfer.service;

import com.h9.common.base.PageResult;
import com.h9.common.db.entity.AreaPhone;
import com.h9.common.db.repo.AreaPhoneRepository;
import com.transfer.db.entity.T_rmpr;
import com.transfer.db.repo.T_rmprRepository;
import com.transfer.service.base.BaseService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * AreaPhoneService:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/4
 * Time: 14:54
 */
@Component
public class AreaPhoneService extends BaseService<T_rmpr> {
    @Resource
    private T_rmprRepository t_rmprRepository;
    @Resource
    private AreaPhoneRepository areaPhoneRepository;

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public PageResult get(int page, int limit) {
        return t_rmprRepository.findAll(page,limit);
    }

    @Override
    public void getSql(T_rmpr t_rmpr, BufferedWriter userWtriter) throws IOException {
        AreaPhone areaPhone = new AreaPhone();
        areaPhone.setName(t_rmpr.getName());
        areaPhone.setArea(t_rmpr.getRegional());
        areaPhone.setPhone(t_rmpr.getMobileone());
        areaPhone.setType(t_rmpr.getType());
        areaPhone.setCreateTime(t_rmpr.getCreateTime());
        areaPhone.setUpdateTime(t_rmpr.getUpdateTime());
    }

    @Override
    public String getTitle() {
        return "区域手机本案迁移进度";
    }
}
