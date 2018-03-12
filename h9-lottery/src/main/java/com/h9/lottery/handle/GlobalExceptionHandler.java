package com.h9.lottery.handle;

import com.alibaba.fastjson.JSONObject;
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
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map;

/**
 * Created by itservice on 2017/10/26.
 */
@SuppressWarnings("Duplicates")
@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = Logger.getLogger(this.getClass());

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

        if (e instanceof IllegalMonitorStateException) {
            logger.info(e.getMessage(), e);
            return new Result(1, "服务器处理中", ExceptionUtils.getMessage(e));
        }
        if (e instanceof NoHandlerFoundException) {
            return new Result(HttpStatus.NOT_FOUND.value(), "页面丢失");
        }

        if (e instanceof MissingServletRequestParameterException) {
            logger.info(e.getMessage(), e);
            return new Result(HttpStatus.BAD_REQUEST.value(), "参数错误", ExceptionUtils.getMessage(e));
        }

        if (e instanceof BindException) {
            String msg = ((BindException) e).getBindingResult().getFieldError().getDefaultMessage();
            return new Result(1, msg);
        }

        if (e instanceof MethodArgumentNotValidException) {
            String msg = ((MethodArgumentNotValidException) e).getBindingResult().getFieldError().getDefaultMessage();
            return new Result(1, msg);
        }

        if (e instanceof UnAuthException) {
            UnAuthException unAuthException = (UnAuthException) e;
            return new Result(unAuthException.getCode(), e.getMessage());
        }

        if (e instanceof HttpMessageNotReadableException) {
            logger.info(e.getMessage(), e);
            return new Result(1, "请输入正确格的的数据类型," + e.getMessage());
        }

        // 以上错误都不匹配
        logger.info(e.getMessage(), e);
        if (System.currentTimeMillis() - time > 5 * 60 * 1000) {
            mailService.sendtMail("徽酒服务器错误" + currentEnvironment, getMailContent(request,e));
            time = System.currentTimeMillis();
        }

        return new Result(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器繁忙，请稍后再试", ExceptionUtils.getStackTrace(e));
    }



    public String getMailContent(HttpServletRequest request,Exception ex){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("uri:" + request.getRequestURI()+"\n\r<BR>");
        String param = "";
        try {
            param =  printReqParams(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stringBuffer.append( JSONObject.toJSONString(param)+"\n\r<BR>");
        stringBuffer.append(ExceptionUtils.getStackTrace(ex));
        return stringBuffer.toString();
    }


    /**
     * description: 打印请求信息
     */
    private String printReqParams(HttpServletRequest httpServletRequest) throws IOException {

        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        String paramStr = JSONObject.toJSONString(parameterMap);

        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        Map<String, String> headers = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = httpServletRequest.getHeader(key);
            headers.put(key, value);
        }

        String param = new String(paramStr);
        int contentLength = httpServletRequest.getContentLength();
        if (contentLength != -1) {

            byte buffer[] = new byte[contentLength];
            for (int i = 0; i < contentLength; ) {

                int readlen = httpServletRequest.getInputStream().read(buffer, i,
                        contentLength - i);
                if (readlen == -1) {
                    break;
                }
                i += readlen;
            }
            param = new String(buffer);
            logger.info("request param: " + new String(buffer));
        } else {
            logger.info("request param: " + new String(paramStr));
        }
        logger.info("---------------------------------------------");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("request headers : " + JSONObject.toJSONString(headers)+"\n\r<BR>");
        stringBuffer.append("request headers : " + param);
        return stringBuffer.toString();
    }



}
