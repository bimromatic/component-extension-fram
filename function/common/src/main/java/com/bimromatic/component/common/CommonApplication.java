package com.bimromatic.component.common;

import android.app.Application;
import android.content.Context;

import com.bimromatic.component.common.startup.asyn.BlockAsynTask;
import com.bimromatic.component.common.startup.asyn.MainAsynTask;
import com.bimromatic.component.common.startup.asyn.PriorityAsynTask;
import com.bimromatic.component.lib_base.app.ApplicationLifecycle;
import com.bimromatic.component.lib_base.app.BaseApplication;
import com.bimromatic.component.lib_base.provider.ContextProvider;
import com.google.auto.service.AutoService;
import com.smartzheng.launcherstarter.LauncherStarter;
import com.wang.android.launch.XStarter;


/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/8/21
 * desc   :
 * version: 1.0
 */
@AutoService(ApplicationLifecycle.class)
public class CommonApplication implements ApplicationLifecycle {

    private  CommonApplication instance;


    @Override
    public void onAttachBaseContext(Context context) {
        instance = this;
    }

    @Override
    public void onCreate(Application application) {
        initDepends(application);
    }

    private void initDepends(Application application) {
        //充分利用CPU多核，自动梳理任务顺序
//        LauncherStarter.init(ContextProvider.getInstance().getApplication());
//        LauncherStarter.createInstance()
//                .addTask(new BlockAsynTask())
//                .addTask(new MainAsynTask())
//                .addTask(new PriorityAsynTask())
//                .start();
        // 是否为测试模式
        XStarter.isDebug = true;
        XStarter.emit(BaseApplication.getInstance());

    }

    @Override
    public void onTerminate(android.app.Application application) {

    }

}
