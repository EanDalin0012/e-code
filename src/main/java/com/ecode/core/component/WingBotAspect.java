package com.ecode.core.component;

import com.ecode.core.dto.GoogleWebhookRequest;
import com.ecode.core.dto.WingBotEvent;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

//@Aspect
//@Component
public class WingBotAspect {

//    @Inject
//    private ApplicationEventPublisher eventPublisher;
//
//    @Pointcut("execution(* com.ecode.admin.service.implement.CategoryServiceImplement.execute*(..)))" )
//    private void execute(){}
//
//    @Before("execute() && args(request)")
//    public void beforeExecute(GoogleWebhookRequest request) {
////        eventPublisher.publishEvent(new WingBotEvent(getRequest(request)));
//    }
//
//    @AfterReturning(pointcut = "execute() && args(request)", argNames = "request, response", returning = "response")
//    public void afterExecute(GoogleWebhookRequest request, Object response) {
////        eventPublisher.publishEvent(new WingBotEvent(getRequest(request, response)));
//    }
}
