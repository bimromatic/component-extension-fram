package com.bimromatic.component.widget_spencer;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/19/21
 * desc   : 生命周期管理，防止内存泄露
 * version: 1.0
 */
public class SpencerManagersLifecycle implements Application.ActivityLifecycleCallbacks {

    private Activity mActivity;
    private SpencerManagers<?> mSpencerManagers;

    SpencerManagersLifecycle(SpencerManagers<?> spencerManagers, Activity activity) {
        mActivity = activity;
        mSpencerManagers = spencerManagers;
    }

    /**
     * 注册监听
     */
    void register() {
        if (mActivity == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            mActivity.registerActivityLifecycleCallbacks(this);
        } else {
            mActivity.getApplication().registerActivityLifecycleCallbacks(this);
        }
    }

    /**
     * 取消监听
     */
    void unregister() {
        if (mActivity == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            mActivity.unregisterActivityLifecycleCallbacks(this);
        } else {
            mActivity.getApplication().unregisterActivityLifecycleCallbacks(this);
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}

    @Override
    public void onActivityStarted(Activity activity) {}

    @Override
    public void onActivityResumed(Activity activity) {}

    @Override
    public void onActivityPaused(Activity activity) {
        // 一定要在 onPaused 方法中销毁掉，如果放在 onDestroyed 方法中还是有一定几率会导致内存泄露
        if (mActivity != activity || !mActivity.isFinishing() || mSpencerManagers == null || !mSpencerManagers.isShow()) {
            return;
        }
        mSpencerManagers.cancel();
    }

    @Override
    public void onActivityStopped(Activity activity) {}

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (mActivity != activity) {
            return;
        }
        // 释放 Activity 的引用
        mActivity = null;

        if (mSpencerManagers == null) {
            return;
        }
        if (mSpencerManagers.isShow()) {
            mSpencerManagers.cancel();
        }
        mSpencerManagers.recycle();
        mSpencerManagers = null;
    }
}
