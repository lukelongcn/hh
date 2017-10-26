package com.h9.admin.handle;

import com.h9.common.base.Result;
import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

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
        } else {
            return new Result(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }
    }

}
