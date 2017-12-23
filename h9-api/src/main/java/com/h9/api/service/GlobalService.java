package com.h9.api.service;

import com.h9.common.base.Result;
import com.h9.common.db.entity.GlobalProperty;
import com.h9.common.db.repo.GlobalPropertyRepository;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * Created by 李圆 on 2017/12/23
 */
@Service
public class GlobalService {


    @Resource
    GlobalPropertyRepository globalPropertyRepository;

    @Transactional
    public String version(Integer client) {
        // 安卓
        if (client == 1) {
            GlobalProperty globalProperty = globalPropertyRepository.findByCode("androidDownload");
            System.out.println(globalProperty.getVal());
            return globalProperty.getVal();
        }
        // IOS
        if (client == 2) {
            GlobalProperty globalProperty1 = globalPropertyRepository.findByCode("iosDownload");
            return globalProperty1.getVal();
        }
        return "";
    }
}
