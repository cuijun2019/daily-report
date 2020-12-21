package com.etone.project.core.aop;

import com.etone.project.core.db.CustomSqlSessionTemplate;
import com.etone.project.core.web.SpringContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: JMJacnMM4
 * Date: 16-7-20
 * Time: 上午10:39
 * To change this template use File | Settings | File Templates.
 */
@Component
@Aspect
public class MultiDataSourceAop {

    @Around("execution(* com.etone.project.modules.*.dao.*Mapper.*(..))")
    public Object doAround(ProceedingJoinPoint jp) throws Throwable {
        CustomSqlSessionTemplate csst= SpringContextHolder.getApplicationContext().getBean(CustomSqlSessionTemplate.class);
        csst.setSqlSessionFactory(jp.getSignature().getDeclaringTypeName()+"."+jp.getSignature().getName());
        return jp.proceed();
    }

}