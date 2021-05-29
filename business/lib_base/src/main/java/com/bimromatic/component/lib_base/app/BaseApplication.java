package com.bimromatic.component.lib_base.app;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.bimromatic.component.lib_base.BuildConfig;
import com.bimromatic.component.lib_base.aop.DebugLog;
import com.bimromatic.component.lib_base.provider.ContextProvider;
import com.bytedance.boost_multidex.BoostMultiDex;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;


/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/6/21
 * desc   :
 * version: 1.0
 */

public class BaseApplication extends MultiDexApplication {


    private static BaseApplication instance;
    public static Context context;
    private LoadModuleProxy loadModuleProxy ;

    /**
     * 获得当前app运行的Application
     */
    public static BaseApplication getInstance() {
        if (instance == null) {
            throw new NullPointerException(
                    "please inherit BaseApplication or call setApplication.");
        }
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
       if(android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
           BoostMultiDex.install(base);
       }
        xcrash.XCrash.init(base);
        context = base;
        instance = this;
        loadModuleProxy = new LoadModuleProxy();
        loadModuleProxy.onAttachBaseContext(base);
    }

    //创建应用程序时回调，回调时机早于任何 Activity
    @DebugLog("启动耗时")
    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacksImpl());
        loadModuleProxy.onCreate(this);
        initDepends();

    }

    private void initDepends() {

        //NetStatusBus.getInstance().init(this);
    }

    //终止应用程序时调用，不能保证一定会被调用
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    //当后台应用程序终止，但前台用用程序内存还不够时调用该方法，可在该方法中释放一些不必要的资源来应对这种情况
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    //配置发生变化时回调该方法，如手机屏幕旋转等
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    //通知应用的不同内存情况，下面内存级别说明来自
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
