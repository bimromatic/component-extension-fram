package com.bimromatic.component.lib_base.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/11/21
 * desc   : Anti repeated click notes
 * version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SingleClick {
    /* Click interval */
    long value() default 1000;
}
