package com.h9.api.handler;

import org.jboss.logging.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by itservice on 2017/10/26.
 */
public class GlobalExceptionHandler implements HandlerExceptionResolver {
    private Logger logger = Logger.getLogger(this.getClass());
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        logger.info(e.getMessage(),e);
        return null;
    }
}
