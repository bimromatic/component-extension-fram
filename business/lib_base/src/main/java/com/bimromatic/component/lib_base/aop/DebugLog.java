package com.bimromatic.component.lib_base.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/21/21
 * desc   :
 * version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface DebugLog {
    String value() default "DebugLog";
}



