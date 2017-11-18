package com.h9.admin.interceptor;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 替换Request对象
 * 不过滤内部请求，如服务器内部使用forward转发请求和<%@ include file="/index.jsp"%>的情况
 */
@Component
public class RequestReplaceFilter extends OncePerRequestFilter {
    private Logger logger = Logger.getLogger(this.getClass());
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!(request instanceof CustomServletRequestWrapper)) {
            request = new CustomServletRequestWrapper(request);
            logger.info("CustomServletRequestWrapper");
        }
        filterChain.doFilter(request, response);
        logger.info("not CustomServletRequestWrapper");
    }
}
