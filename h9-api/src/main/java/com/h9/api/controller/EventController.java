package com.h9.api.controller;

import com.h9.api.model.dto.EventDTO;
import com.h9.api.model.dto.VerifyTokenDTO;
import com.h9.api.service.EventService;
import com.h9.common.utils.CheckoutUtil;
import com.h9.common.utils.XMLUtils;
import org.jboss.logging.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.Map;

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

    @PostMapping(value = "/wx/event",consumes = "text/xml")
    public String wxEventCallback(HttpServletRequest request) throws Exception{
        Map<String, String> map = null;
        try {
            map = XMLUtils.parseXml(request);
        } catch (Exception e) {
            logger.info(e.getMessage(),e);
        }
        return eventService.handler(map);
    }

}
