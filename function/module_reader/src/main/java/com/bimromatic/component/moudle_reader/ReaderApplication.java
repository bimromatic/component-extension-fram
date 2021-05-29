package com.bimromatic.component.moudle_reader;


import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.bimromatic.component.lib_base.app.ApplicationLifecycle;
import com.bimromatic.component.lib_base.app.InitDepend;
import com.google.auto.service.AutoService;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/7/21
 * desc   :
 * version: 1.0
 */
@AutoService(ApplicationLifecycle.class)
public class ReaderApplication implements ApplicationLifecycle {

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
