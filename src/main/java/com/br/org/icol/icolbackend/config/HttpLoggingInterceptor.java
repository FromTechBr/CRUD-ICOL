package com.br.org.icol.icolbackend.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class HttpLoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger("HTTP_REQUESTS");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
            throws Exception {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        
        String fullUrl = queryString != null ? uri + "?" + queryString : uri;
        
        logger.info("[{}] {} - Remote IP: {}", method, fullUrl, request.getRemoteAddr());
        
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
            Object handler, Exception ex) throws Exception {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        int status = response.getStatus();
        
        logger.info("[{}] {} - Status: {} - Response Time", method, uri, status);
    }
}
