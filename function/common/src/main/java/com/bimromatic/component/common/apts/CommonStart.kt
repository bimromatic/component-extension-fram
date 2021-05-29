package com.bimromatic.component.common.apt

import android.app.Activity
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.bimromatic.component.lib_base.app.BaseApplication
import com.bimromatic.component.lib_base.manager.AppBlockCanaryContext
import com.bimromatic.component.lib_base.provider.ContextProvider
import com.bimromatic.component.lib_base.status.EmptyStatus
import com.bimromatic.component.lib_base.status.LoadingStatus
import com.bimromatic.component.lib_base.status.NoneNetStatus
import com.bimromatic.component.lib_base.utils.net.NetworkInformation
import com.bimromatic.component.common.BuildConfig
import com.bimromatic.component.common.R
import com.github.moduth.blockcanary.BlockCanary
import com.hjq.toast.ToastUtils
import com.kingja.loadsir.core.LoadSir
import com.kongqw.network.monitor.NetworkMonitorManager
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.DiskLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.snail.collie.Collie
import com.snail.collie.CollieListener
import com.snail.collie.Config
import com.snail.collie.battery.BatteryInfo
import com.snail.collie.mem.TrackMemoryInfo
import com.wang.android.starter.IStarter
import com.wang.android.starter.annotation.Starter
import com.wang.android.starter.annotation.StarterMethod
import com.xander.performance.PERF
import com.xuexiang.xaop.XAOP
import java.io.File

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/18/21
 * desc   :
 * version: 1.0
 */

@Starter(mainProcessOnly = false)
class CommonStart : IStarter {


    @StarterMethod(priority = 99, isSync = true, isDelay = false)
    fun initArouters() {
        Log.d("JavaStarter", "初始化" + Thread.currentThread().name)
        initARouter()
    }

    @StarterMethod(priority = 98, isSync = true, isDelay = false)
    fun initContent() {
        Log.d("JavaStarter", "初始化" + Thread.currentThread().name)
        initLoadsir()
    }

    @StarterMethod(priority = 97, isSync = true, isDelay = false)
    fun initBlockC() {
        Log.d("JavaStarter", "初始化" + Thread.currentThread().name)
        initBlockCanary()
    }

    @StarterMethod(priority = 97, isSync = true, isDelay = false)
    fun initCollieMonitor() {
        Log.d("JavaStarter", "初始化" + Thread.currentThread().name)
        //initCollie()
    }

    @StarterMethod(priority = 95, isSync = true, isDelay = false)
    fun initPerf() {
        Log.d("JavaStarter", "初始化" + Thread.currentThread().name)
        //initPERF()
    }



    @StarterMethod(priority = 99, isSync = false, isDelay = false)
    fun initLog() {
        Log.d("JavaStarter", "初始化" + Thread.currentThread().name)
        initLogs()
    }

    @StarterMethod(priority = 98, isSync = false, isDelay = false)
    fun initToasts() {
        Log.d("JavaStarter", "初始化" + Thread.currentThread().name)
        initToast()
    }


    @StarterMethod(priority = 97, isSync = false, isDelay = false)
    fun initNet() {
        Log.d("JavaStarter", "初始化" + Thread.currentThread().name)
        initNetWork()
    }

    @StarterMethod(priority = 96, isSync = false, isDelay = false)
    fun initAop() {
        Log.d("JavaStarter", "初始化" + Thread.currentThread().name)
        initXaop()
    }

    private fun initLoadsir(): String {
        LoadSir.beginBuilder()
                .addCallback(LoadingStatus())
                .addCallback(EmptyStatus())
                .addCallback(NoneNetStatus())
                .setDefaultCallback(LoadingStatus::class.java)
                .commit()
        return "LoadSir -->> init complete"
    }

    private fun initBlockCanary(): String {
        BlockCanary.install(ContextProvider.getInstance().application, AppBlockCanaryContext()).start()
        return "BlockCanary -->> init complete"
    }

    private fun initCollie(): String {
        Collie.getInstance().init(ContextProvider.getInstance().application, Config(true, true, true, true, true, true), CollieListeners())
        return "Collie -->> init complete"
    }

    class CollieListeners : CollieListener {
        override fun onAppColdLaunchCost(duration: Long, procName: String?) {
            Log.d(" 启动耗时 ", "启动耗时 " + duration + " processName " + procName)
        }

        override fun onActivityLaunchCost(activity: Activity?, duration: Long, finishNow: Boolean) {
            Log.d("启动耗时", "activity启动耗时 " + activity?.javaClass?.simpleName + " " + duration + " finishNow " + finishNow)
        }

        override fun onFpsTrack(activity: Activity?, currentCostMils: Long, currentDropFrame: Long, isInFrameDraw: Boolean, averageFps: Long) {
            if (currentDropFrame >= 2)
                Log.d("掉帧", "Activity " + activity?.javaClass?.simpleName + " 掉帧 " + currentDropFrame + " 是否因为Choro 绘制掉帧 " + isInFrameDraw + " 1s 平均帧率" + averageFps)
        }

        override fun onANRAppear(activity: Activity?) {
            Log.d("ANR", "Activity " + activity?.javaClass?.simpleName + " ANR  ")
        }

        override fun onLeakActivity(activity: String?, count: Int) {
            Log.d("内存泄露", "内存泄露 " + activity?.javaClass?.simpleName + " 数量 " + count)
        }

        override fun onCurrentMemoryCost(trackMemoryInfo: TrackMemoryInfo?) {
            Log.d("内存", "内存  " + trackMemoryInfo?.procName + " java内存  "
                    + trackMemoryInfo?.appMemory?.dalvikPss + " native内存  " +
                    trackMemoryInfo?.appMemory?.nativePss);
        }

        override fun onTrafficStats(activity: Activity?, value: Long) {
            Log.d("流量消耗", "" + activity?.javaClass?.getSimpleName() + " 流量消耗 " + value * 1.0f / (1024 * 1024) + "M")
        }

        override fun onBatteryCost(batteryInfo: BatteryInfo?) {
            Log.d("电量流量消耗", " 电量流量消耗 " + batteryInfo?.cost)
        }
    }

    fun initARouter(): String? {
        if (BaseApplication.context.getString(R.string.VERSION_STATUS) == BuildConfig.VERSION_STATUS) {
            ARouter.openLog()
            ARouter.openDebug()
        } else {
            Log.d("info", "this is realse");
        }
        ARouter.init(BaseApplication.getInstance())
        return "ARouter -->> init complete"
    }

    fun initLogs(): String {
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

        return "Logger -->> init complete"
    }

    fun initToast(): String {
        ToastUtils.init(ContextProvider.getInstance().application)
        return "Toast -->> init complete"
    }

    fun initNetWork(): String {
        NetworkInformation.sharedManager().setContext(ContextProvider.getInstance().context)
        //https://codechina.csdn.net/mirrors/kongqw/NetworkMonitor/-/tree/master/app
        NetworkMonitorManager.getInstance().init(ContextProvider.getInstance().application, 0)
        return "NetworkMonitorManager -->> init complete"
    }

    fun initPERF(): String {
        PERF.init(PERF.Builder()
                .checkUI(true, 100)// 检查 ui lock
                .checkIPC(true) // 检查 ipc 调用
                .checkFps(true, 1000) // 检查 fps
                .checkThread(true)// 检查线程和线程池
                .globalTag("test_perf")// 全局 logcat tag ,方便过滤
                .cacheDirSupplier { ContextProvider.getInstance().context.cacheDir } // issue 文件保存目录
                .maxCacheSizeSupplier { 10 * 1024 * 1024 } // issue 文件最大占用存储空间
                .uploaderSupplier { // issue 文件的上传接口实现
                    PERF.LogFileUploader { logFile -> doUpload(logFile) }
                }
                .build()
        )

        return "PERF -->> init complete"
    }

    private fun doUpload(log: File): Boolean {
        return false
    }

    private fun initXaop() :String{
        XAOP.init(ContextProvider.getInstance().application); //初始化插件
        XAOP.debug(true); //日志打印切片开启
        XAOP.setPriority(Log.INFO); //设置日志打印的等级,默认为0
        return "XAOP -->> init complete"
    }

}