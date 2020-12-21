package com.etone.project.core.aop;//package com.etone.project.core.aop;
//
//import org.aopalliance.intercept.MethodInterceptor;
//import org.aopalliance.intercept.MethodInvocation;
//
//import java.lang.reflect.Method;
//
///**
// * Created with IntelliJ IDEA.
// * User: JMJacnMM4
// * Date: 16-7-21
// * Time: 上午11:39
// * To change this template use File | Settings | File Templates.
// */
//public class DataSourceAop implements MethodInterceptor {
//
//    public DataSourceAop() {
//    }
//
//    @Override
//    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
//        Method method = methodInvocation.getMethod();
//        String methodName = method.getName();
//        Class<?> cls = method.getDeclaringClass();
//        Object service = methodInvocation.getThis();
//        Object[] args = methodInvocation.getArguments();
//        Integer actionType = -1;
//        return methodInvocation;
//    }
//}
