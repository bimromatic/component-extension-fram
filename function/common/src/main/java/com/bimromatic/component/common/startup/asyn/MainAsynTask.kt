package com.bimromatic.component.common.startup.asyn

import android.app.Activity
import android.util.Log
import com.bimromatic.component.lib_base.manager.AppBlockCanaryContext
import com.bimromatic.component.lib_base.provider.ContextProvider
import com.bimromatic.component.lib_base.status.EmptyStatus
import com.bimromatic.component.lib_base.status.LoadingStatus
import com.bimromatic.component.lib_base.status.NoneNetStatus
import com.github.moduth.blockcanary.BlockCanary
import com.kingja.loadsir.core.LoadSir
import com.smartzheng.launcherstarter.task.Task
import com.snail.collie.Collie
import com.snail.collie.CollieListener
import com.snail.collie.Config
import com.snail.collie.battery.BatteryInfo
import com.snail.collie.mem.TrackMemoryInfo
import com.xander.performance.PERF
import java.io.File


/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/10/21
 * desc   : Main thread mode initialization
 * version: 1.0
 */
class MainAsynTask:Task() {

    override fun runOnMainThread(): Boolean {
        return true
    }

    override fun run() {
        initLoadsir()
        initBlockCanary()
        initCollie()
        initPERF()
    }

    private fun initLoadsir():String{
        LoadSir.beginBuilder()
                .addCallback(LoadingStatus())
                .addCallback(EmptyStatus())
                .addCallback(NoneNetStatus())
                .setDefaultCallback(LoadingStatus::class.java)
                .commit()
        return "LoadSir -->> init complete"
    }

    private fun initBlockCanary():String {
        BlockCanary.install(ContextProvider.getInstance().application, AppBlockCanaryContext()).start()
        return "BlockCanary -->> init complete"
    }

    private fun initCollie() {
        Collie.getInstance().init(ContextProvider.getInstance().application,  Config(true, true, true, true, true, true),CollieListeners())
    }

    class CollieListeners: CollieListener {
        override fun onAppColdLaunchCost(duration: Long, procName: String?) {
            Log.d(" 启动耗时 ", "启动耗时 " + duration +" processName "+procName)
        }

        override fun onActivityLaunchCost(activity: Activity?, duration: Long, finishNow: Boolean) {
            Log.d("启动耗时", "activity启动耗时 " + activity?.javaClass?.simpleName + " " + duration + " finishNow "+finishNow)
        }

        override fun onFpsTrack(activity: Activity?, currentCostMils: Long, currentDropFrame: Long, isInFrameDraw: Boolean, averageFps: Long) {
            if (currentDropFrame >= 2)
                Log.d("掉帧", "Activity " + activity?.javaClass?.simpleName + " 掉帧 " + currentDropFrame + " 是否因为Choro 绘制掉帧 " + isInFrameDraw + " 1s 平均帧率" + averageFps)
        }

        override fun onANRAppear(activity: Activity?) {
            Log.d("ANR", "Activity " + activity?.javaClass?.simpleName + " ANR  " )
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
            Log.d("电量流量消耗",  " 电量流量消耗 " +batteryInfo?.cost)
        }
    }

    fun initPERF() {
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
    }

    private fun doUpload(log: File): Boolean {
        return false
    }
}