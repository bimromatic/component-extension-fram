package com.bimromatic.component.lib_net;

import android.app.Application;
import android.content.Context;

import com.bimromatic.component.lib_base.app.ApplicationLifecycle;
import com.google.auto.service.AutoService;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/10/21
 * desc   :
 * version: 1.0
 */
@AutoService(ApplicationLifecycle.class)
public class NetApplication implements ApplicationLifecycle {
    @Override
    public void onAttachBaseContext(Context context) {

    }

    @Override
    public void onCreate(Application application) {

    }

    @Override
    public void onTerminate(Application application) {

    }
}
