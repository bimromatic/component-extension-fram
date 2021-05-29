package com.bimromatic.component.lib_base.impl;

import android.widget.Toast;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/12/21
 * desc   : Toast 拦截器接口
 * version: 1.0
 */
public interface IToastInterceptor {
    /**
     * 根据显示的文本决定是否拦截该 Toast
     */
    boolean intercept(Toast toast, CharSequence text);
}
