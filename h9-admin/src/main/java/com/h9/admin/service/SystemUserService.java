package com.h9.admin.service;

import com.h9.admin.model.po.SystemUser;
import com.h9.admin.repository.SystemUserRepository;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: George
 * @date: 2017/10/30 18:40
 */
@Service
public class SystemUserService {
    @Resource
    private SystemUserRepository systemUserRepository;

    public SystemUser getByName(String name){
        return this.systemUserRepository.findByName(name);
    }

    public SystemUser getByNameAndPassword(String name,String password){
        return this.systemUserRepository.findByNameAndPassword(name,password);
    }

    public static SystemUser getCurrentUser() {
        SystemUser user = (SystemUser) SecurityUtils.getSubject().getSession().getAttribute("user");
        return user;
    }
}
