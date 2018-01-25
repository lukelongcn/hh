package com.h9.api.controller;

import com.h9.api.model.dto.VerifyTokenDTO;
import com.h9.api.service.EventService;
import com.h9.common.utils.CheckoutUtil;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by itservice on 2018/1/25.
 */

@RestController
public class EventController {

    @Resource
    private EventService eventService;

    private Logger logger = Logger.getLogger(this.getClass());
    @GetMapping(value = "/wx/event")
    public String verifyToken( VerifyTokenDTO verifyTokenDTO) {
       return eventService.handle(verifyTokenDTO);
    }


}
