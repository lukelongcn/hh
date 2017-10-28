package com.h9.admin.handler;

import com.h9.common.base.Result;
import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Created by itservice on 2017/10/26.
 */
@SuppressWarnings("Duplicates")
@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = Logger.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object hanldeException(Exception e) {
        logger.info(e.getMessage(), e);
        if (e instanceof HttpRequestMethodNotSupportedException) {
            return new Result(HttpStatus.METHOD_NOT_ALLOWED.value(), HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase());
        }else if(e instanceof NoHandlerFoundException){
            return new Result(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
        } else {
            return new Result(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }
    }

}
