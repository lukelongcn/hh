package com.h9.api.handle;

import com.h9.common.base.Result;
import com.h9.common.common.MailService;
import com.h9.common.common.ServiceException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Created by itservice on 2017/10/26.
 */
@SuppressWarnings("Duplicates")
@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = Logger.getLogger(this.getClass());

    String request_head = "http:localhost";

    @Resource
    private MailService mailService;

    @Value("${h9.current.envir}")
    private String currentEnvironment;

    public static long time = System.currentTimeMillis();

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object hanldeException(Exception e, HttpServletRequest request) {

        if (e instanceof MethodArgumentTypeMismatchException) {
            logger.info(e.getMessage(),e);
            return new Result(1, "请传入正确的参数," + e.getMessage());
        }


        if (e instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) e;
            return new Result(serviceException.getCode(), serviceException.getMessage());
        }


        if (e instanceof HttpRequestMethodNotSupportedException) {
            return new Result(HttpStatus.METHOD_NOT_ALLOWED.value(), "请求方法不被允许", ExceptionUtils.getMessage(e));
        }

        if (e instanceof NoHandlerFoundException) {
            return new Result(HttpStatus.NOT_FOUND.value(), "页面丢失");
        }

        if (e instanceof MissingServletRequestParameterException) {
            logger.info(e.getMessage(), e);
            logger.info("参数错误");
            return new Result(HttpStatus.BAD_REQUEST.value(), "参数错误", ExceptionUtils.getMessage(e));
        }

        if (e instanceof BindException) {
            String msg = ((BindException) e).getBindingResult().getFieldError().getDefaultMessage();
            logger.info(msg);
            return new Result(1, msg);
        }

        if (e instanceof MethodArgumentNotValidException) {
            String msg = ((MethodArgumentNotValidException) e).getBindingResult().getFieldError().getDefaultMessage();
            logger.info(msg);
            return new Result(1, msg);
        }

        if (e instanceof UnAuthException) {
            UnAuthException unAuthException = (UnAuthException) e;
            logger.info(e.getMessage());
            return new Result(unAuthException.getCode(), e.getMessage());
        }

        if (e instanceof HttpMessageNotReadableException) {
            logger.info(e.getMessage(), e);
            return new Result(1, "请输入正确格的的数据类型," + e.getMessage());
        }



        // 以上错误都不匹配
        logger.info(e.getMessage(), e);
        if (System.currentTimeMillis() - time > 5 * 60 * 1000) {

            String url = request.getRequestURL().toString();
            String content = "url: " + url +" "+ ExceptionUtils.getStackTrace(e);
            content += request.getHeader("token");

            if (!url.startsWith(request_head)) {
                mailService.sendtMail("徽酒服务器错误" + currentEnvironment, content);
            }
            time = System.currentTimeMillis();
        }
        logger.info("hanldeException 服务器繁忙，请稍后再试");
        return new Result(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器繁忙，请稍后再试", ExceptionUtils.getStackTrace(e));
    }

}
