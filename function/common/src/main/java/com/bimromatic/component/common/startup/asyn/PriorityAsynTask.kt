package com.bimromatic.component.common.startup.asyn

import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.bimromatic.component.lib_base.app.BaseApplication
import com.bimromatic.component.lib_base.provider.ContextProvider
import com.bimromatic.component.lib_base.utils.net.NetworkInformation
import com.bimromatic.component.common.BuildConfig
import com.bimromatic.component.common.R
import com.hjq.toast.ToastUtils
import com.kongqw.network.monitor.NetworkMonitorManager
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.DiskLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.smartzheng.launcherstarter.task.Task
import com.xander.performance.PERF
import java.io.File


/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/10/21
 * desc   : Priority mode initialization
 * version: 1.0
 */
class PriorityAsynTask:Task() {

    override fun needRunAsSoon(): Boolean {
        return true
    }
    override fun run() {
        Log.e("Task", "PriorityAsynTask");
        initARouter()
        initLogs()
        initToast()
        initNetWork()
        initPERF()
        //InitializeService.start(ContextProvider.getInstance().context);
    }
    private fun initLogs() {
        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                //.tag("nest_tree")
                .methodCount(0)
                .methodOffset(3)
                .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
        Logger.addLogAdapter(DiskLogAdapter())
    }

    private fun initNetWork() {
        NetworkInformation.sharedManager().setContext(ContextProvider.getInstance().context)
        //https://codechina.csdn.net/mirrors/kongqw/NetworkMonitor/-/tree/master/app
        NetworkMonitorManager.getInstance().init(ContextProvider.getInstance().application, 0)
    }


    /**
     * ???????????? ARouter ?????????
     */
    private fun initARouter(): String? {
        if (BaseApplication.context.getString(R.string.VERSION_STATUS) == BuildConfig.VERSION_STATUS) {
            ARouter.openLog()
            ARouter.openDebug()
        } else {
            Log.d("info", "this is realse");
        }
        ARouter.init(BaseApplication.getInstance())
        return "ARouter -->> init complete"
    }

    private fun initToast():String {
        ToastUtils.init(ContextProvider.getInstance().application)
        return "Toast -->> init complete"
    }


    private fun initPERF() {
        PERF.init(PERF.Builder()
                .checkUI(true, 100)// ?????? ui lock
                .checkIPC(true) // ?????? ipc ??????
                .checkFps(true, 1000) // ?????? fps
                .checkThread(true)// ????????????????????????
                .globalTag("test_perf")// ?????? logcat tag ,????????????
                .cacheDirSupplier { ContextProvider.getInstance().context.cacheDir } // issue ??????????????????
                .maxCacheSizeSupplier { 10 * 1024 * 1024 } // issue ??????????????????????????????
                .uploaderSupplier { // issue ???????????????????????????
                    PERF.LogFileUploader { logFile -> doUpload(logFile) }
                }
                .build()
        )
    }

    private fun doUpload(log: File): Boolean {
        return false
    }
}