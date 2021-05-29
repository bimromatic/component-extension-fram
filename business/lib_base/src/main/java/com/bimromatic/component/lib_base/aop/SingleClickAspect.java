package com.bimromatic.component.lib_base.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;

import timber.log.Timber;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/11/21
 * desc   : Anti repeated click section
 * version: 1.0
 */
@Aspect
public class SingleClickAspect {

    /**  Time of last click  */
    private long mLastTime;

    /**  Last click tag  */
    private String mLastTag;

    /**
     *  Method entry point
     */
    @Pointcut("execution(@com.bimromatic.component.lib_base.aop.SingleClick * *(..))")
    public void method() {}

    /**
     *  Method substitution at join point
     */
    @Around("method() && @annotation(singleClick)")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint, SingleClick singleClick) throws Throwable {
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        // Method class
        String className = codeSignature.getDeclaringType().getName();
        // Method name
        String methodName = codeSignature.getName();
        // Construction method  TAG
        StringBuilder builder = new StringBuilder(className + "." + methodName);
        builder.append("(");
        Object[] parameterValues = joinPoint.getArgs();
        for (int i = 0; i < parameterValues.length; i++) {
            Object arg = parameterValues[i];
            if (i == 0) {
                builder.append(arg);
            } else {
                builder.append(", ")
                        .append(arg);
            }
        }

        builder.append(")");

        String tag = builder.toString();
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - mLastTime < singleClick.value() && tag.equals(mLastTag)) {
            Timber.tag("SingleClick");
            Timber.i("%s 毫秒内发生快速点击：%s", singleClick.value(), tag);
            return;
        }
        mLastTime = currentTimeMillis;
        mLastTag = tag;
        // Execute the original method
        joinPoint.proceed();
    }
}
