package com.t1.requestlogingstarter.logging;

import com.t1.requestlogingstarter.logging.service.LoggingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class LogInterceptor implements HandlerInterceptor {
    private final LoggingService loggingService;
    @Value("${logging.external.request:false}")
    private boolean hasLoggingExternalRequest;
    @Value("${logging.external.response:false}")
    private boolean hasLoggingExternalResponse;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (hasLoggingExternalRequest && "GET".equals(request.getMethod())) {
            loggingService.logRequest(request, null);
        }
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (hasLoggingExternalResponse && "GET".equals(request.getMethod())) {
            loggingService.logResponse(request, response, null);
        }
    }
}
