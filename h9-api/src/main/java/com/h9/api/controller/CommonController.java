package com.h9.api.controller;


import com.h9.common.db.repo.AgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * CommonController:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/31
 * Time: 11:22
 */
@RestController
@RequestMapping("/common")
public class CommonController {
    @Autowired
    private AgreementRepository agreementRepository;

    @GetMapping(value = "/{name}")
    public String agreement(@RequestParam("name") String name){
        return agreementRepository.agreement(name);
    }

}
