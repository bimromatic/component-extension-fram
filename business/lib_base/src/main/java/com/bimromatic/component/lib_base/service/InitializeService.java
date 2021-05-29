package com.bimromatic.component.lib_base.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/8/21
 * desc   :
 *
 * 在Service中 初始化耗时的SDK等（BaseAppDeletage调用）
 * 为了在application中 不进行耗时操作 影响冷启动 白屏时间增加
 *
 * * IntentService特点：
 * * 1.在任务完成后将自动停止。
 * * 2.任务在队列中执行，是有先后顺序的。
 * * 3.任务在子线程中运行，可以执行耗时任务。
 *
 * version: 1.0
 */
public class InitializeService extends JobIntentService {

    private static final String ACTION_INIT = "initApplication";

    public InitializeService() {

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT);
        context.startService(intent);
    }


    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT.equals(action)) {
                initApplication();
            }
        }
    }

    private void initApplication() {
        initBugly();
        initSp();
        initArouter();
    }

    private void initSp() {
    }


    private void initArouter() {
    }

    /**
     * 初始化腾讯bug管理平台
     * 子线程进行初始化SDK操作
     */
    private void initBugly() {
        /* Bugly SDK初始化
         * 参数1：上下文对象
         * 参数2：APPID，平台注册时得到,注意替换成你的appId
         * 参数3：是否开启调试模式，调试模式下会输出'CrashReport'tag的日志
         * 注意：如果您之前使用过Bugly SDK，请将以下这句注释掉。
         */
        //        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        //        strategy.setAppVersion(AppUtils.getAppVersionName());
        //        strategy.setAppPackageName(AppUtils.getAppPackageName());
        //        strategy.setAppReportDelay(20000);                          //Bugly会在启动20s后联网同步数据
        //
        //        /*  第三个参数为SDK调试模式开关，调试模式的行为特性如下：
        //            输出详细的Bugly SDK的Log；
        //            每一条Crash都会被立即上报；
        //            自定义日志将会在Logcat中输出。
        //            建议在测试阶段建议设置成true，发布时设置为false。*/
        //
        //        CrashReport.initCrashReport(getApplicationContext(), "126dde5e58", true ,strategy);
    }
}
