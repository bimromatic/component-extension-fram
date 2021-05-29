package com.bimromatic.component.lib_base.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.bimromatic.component.lib_base.utils.net.NetworkInformation;
import com.orhanobut.logger.Logger;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/7/21
 * desc   : Load component proxy class
 *        : The work of component initialization will be implemented by the agent class agent
 * version: 1.0
 */
public class LoadModuleProxy implements ApplicationLifecycle{

    private ServiceLoader mLoader = ServiceLoader.load(ApplicationLifecycle.class);
    private Iterator<ApplicationLifecycle> mIterator;

    @Override
    public void onAttachBaseContext(Context context) {
        mIterator = mLoader.iterator();
        while (mIterator.hasNext()){
            ApplicationLifecycle it =  mIterator.next();
            Log.d("ApplicationInit",""+ it.getClass().getSimpleName());
            it.onAttachBaseContext(context);
        }
    }

    @Override
    public void onCreate(Application application) {
        mIterator = mLoader.iterator();
        while (mIterator.hasNext()){
            ApplicationLifecycle it =  mIterator.next();
            it.onCreate(application);
        }
    }

    @Override
    public void onTerminate(Application application) {
        mIterator = mLoader.iterator();
        while (mIterator.hasNext()){
            ApplicationLifecycle it =  mIterator.next();
            it.onTerminate(application);
        }
    }
}
