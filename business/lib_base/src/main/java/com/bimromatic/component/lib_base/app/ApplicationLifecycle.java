package com.bimromatic.component.lib_base.app;

import android.app.Application;
import android.content.Context;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/7/21
 * desc   :
 * version: 1.0
 */
public interface ApplicationLifecycle {

    /**
     * 同[Application.attachBaseContext]
     * @param context Context
     */
    void onAttachBaseContext(Context context);

    /**
     * 同[Application.onCreate]
     * @param application Application
     */
    void onCreate(Application application);

    /**
     * 同[Application.onTerminate]
     * @param application Application
     */

    //Android 8.0和9.0在AndroidManifest.xml文件中静态注册广播接收失效是由于官方对耗电量的优化，避免APP滥用广播的一种处理方式。除了少部分的广播仍支持静态注册（如开机广播），其余的都会出现失效的情况。
    //在Application的onCreate()方法中对广播接收进行动态注册，在onTerminate()方法中解绑注册，即可解决。
    void onTerminate(Application application);

}
