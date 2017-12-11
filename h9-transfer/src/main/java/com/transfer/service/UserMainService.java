package com.transfer.service;

import com.h9.common.base.PageResult;
import com.h9.common.db.entity.User;
import com.h9.common.db.repo.UserRepository;
import com.transfer.SqlUtils;
import com.transfer.db.entity.UserInfo;
import com.transfer.db.repo.UserInfoRepository;
import com.transfer.service.base.BaseService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * UserMainService:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/11
 * Time: 9:19
 */
@Component
public class UserMainService extends BaseService<UserInfo> {

    @Resource
    private UserInfoRepository userInfoRepository;
    @Resource
    private UserRepository userRepository;


    @Override
    public String getPath() {
        return null;
    }

    @Override
    public PageResult get(int page, int limit) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        return userInfoRepository.findAll(page,limit,sort);
    }

    @Override
    public void getSql(UserInfo userInfo, BufferedWriter writer) throws IOException {
        User user = new User();
        user.setId(userInfo.getId());
        user.setH9UserId(userInfo.getId());
        user.setCreateTime(userInfo.getRegisterTime());
        user.setUpdateTime(userInfo.getRegisterTime());
        user.setLastLoginTime(userInfo.getRegisterTime());
        user.setLoginCount(1);
        user.setNickName(userInfo.getUsername());
        user.setOpenId(userInfo.getOpenID());
        user.setPhone(userInfo.getPhone());
        userRepository.save(user);
    }

    @Override
    public String getTitle() {
        return "用户迁移进度";
    }
}
