package com.bimromatic.component;

import android.content.Context;
import com.bimromatic.component.lib_base.app.ApplicationLifecycle;
import com.google.auto.service.AutoService;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/7/21
 * desc   :
 * version: 1.0
 */
@AutoService(ApplicationLifecycle.class)
public class Application implements ApplicationLifecycle{

    private static Application instance;

    public static Application getInstance() {
        if (instance == null) {
            throw new NullPointerException(
                    "please inherit Application or call setApplication.");
        }
        return instance;
    }


    @Override
    public void onAttachBaseContext(Context context) {
    }

    @Override
    public void onCreate(android.app.Application application) {

    }

    @Override
    public void onTerminate(android.app.Application application) {
    }

}
