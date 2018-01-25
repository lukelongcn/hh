package com.h9.api.controller;

import com.h9.api.model.dto.EventDTO;
import com.h9.common.base.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by itservice on 2018/1/25.
 */

@RestController
public class EventController {

    @PostMapping(value = "/wx/event",consumes = "application/xml")
    public  Result test(@RequestBody EventDTO eventDTO){

        System.out.println(eventDTO);
        return Result.success(eventDTO);
    }


}
