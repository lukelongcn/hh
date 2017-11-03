package com.h9.lottery.controller;

import com.h9.common.base.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * LotteryContorller:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/2
 * Time: 15:18
 */
@RestController
public class LotteryContorller {

    @RequestMapping("")
    public Result appCode(String code){

        return  Result.success();
    }


}
