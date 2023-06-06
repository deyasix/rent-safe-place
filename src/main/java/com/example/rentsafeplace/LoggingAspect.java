package com.example.rentsafeplace;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @After("execution(* com.example.rentsafeplace.service.RealtorService.login(..))")
    public void logAfterRealtorLogin(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature().toShortString());
    }

    @After("execution(* com.example.rentsafeplace.service.RealtorService.addRealtor(..))")
    public void logAfterRealtorRegister(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature().toShortString());
    }

    @After("execution(* com.example.rentsafeplace.service.CompanyService.login(..))")
    public void logAfterCompanyLogin(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature().toShortString());
    }

    @After("execution(* com.example.rentsafeplace.service.CompanyService.addCompany(..))")
    public void logAfterCompanyRegister(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature().toShortString());
    }

    @After("execution(* com.example.rentsafeplace.service.TenantService.login(..))")
    public void logAfterTenantLogin(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature().toShortString());
    }

    @After("execution(* com.example.rentsafeplace.service.TenantService.addTenant(..))")
    public void logAfterTenantRegister(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature().toShortString());
    }
}
