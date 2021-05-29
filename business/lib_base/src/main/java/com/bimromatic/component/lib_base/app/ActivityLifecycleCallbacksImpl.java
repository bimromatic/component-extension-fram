package com.bimromatic.component.lib_base.app;

import android.app.Activity;
import android.app.Application;
import android.net.TrafficStats;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bimromatic.component.lib_base.manager.ActivityManager;
import com.bimromatic.component.lib_base.manager.ForegroundPushManager;
import com.bimromatic.component.lib_base.provider.ContextProvider;
import com.kongqw.network.monitor.NetworkMonitorManager;
import com.orhanobut.logger.Logger;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/7/21
 * desc   : Activity lifecycle monitoring
 * version: 1.0
 */
public class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks{

    private String TAG = this.getClass().getSimpleName();
    private int refCount = 0;

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        NetworkMonitorManager.getInstance().register(activity);
        ActivityManager.addActivity(activity);
        Logger.i(activity.getClass().getSimpleName()+"--> onActivityCreated");
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        Logger.i(activity.getClass().getSimpleName()+"--> onActivityStarted");
        //Logger.e("流量检测:", TrafficStats.getUidRxBytes());
        refCount++;
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        Logger.i(activity.getClass().getSimpleName()+"--> onActivityResumed");
        ForegroundPushManager.INSTANCE.stopNotification(ContextProvider.getInstance().getApplication());
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        Logger.i(activity.getClass().getSimpleName()+"--> onActivityPaused");
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        Logger.i(activity.getClass().getSimpleName()+"--> onActivityStopped");
        refCount --;
        if (refCount == 0) {
            ForegroundPushManager.INSTANCE.showNotification(ContextProvider.getInstance().getContext());
        }
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        Logger.i(activity.getClass().getSimpleName()+"--> onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        NetworkMonitorManager.getInstance().unregister(activity);
        ActivityManager.removeActivity(activity);
        Logger.i(activity.getClass().getSimpleName()+"--> onActivityDestroyed");
    }
}
