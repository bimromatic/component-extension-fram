package com.bimromatic.component.lib_base.utils

import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Process

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/8/21
 * desc   : Process tool class
 * version: 1.0
 */
object ProcessUtils {

    /**
     *  Get all current processes
     *
     * @param context Context context
     * @return List<ActivityManager.RunningAppProcessInfo>  All current processes
     */
    fun getRunningAppProcessList(context: Context): List<ActivityManager.RunningAppProcessInfo> {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return activityManager.runningAppProcesses
    }

    /**
     * Determine whether the process ID belongs to the process of the process name
     *
     * @param context Context 上下文
     * @param processId Int 进程Id
     * @param processName String 进程名
     * @return Boolean
     */
    fun isPidOfProcessName(context: Context, processId: Int, processName: String): Boolean {
        // 遍历所有进程找到该进程id对应的进程
        for (process in getRunningAppProcessList(context)) {
            if (process.pid == processId) {
                // 判断该进程id是否和进程名一致
                return (process.processName == processName)
            }
        }
        return false
    }

    /**
     * Get the name of the main process
     *
     * @param context Context 上下文
     * @return String 主进程名
     * @throws PackageManager.NameNotFoundException if a package with the given name cannot be found on the system.
     */
    @Throws(PackageManager.NameNotFoundException::class)
    fun getMainProcessName(context: Context): String {
        val applicationInfo = context.packageManager.getApplicationInfo(context.packageName, 0)
        return applicationInfo.processName
    }

    /**
     *  Determine whether the current process is the main process
     *
     * @param context Context 上下文
     * @return Boolean
     * @throws PackageManager.NameNotFoundException if a package with the given name cannot be found on the system.
     */
    @Throws(PackageManager.NameNotFoundException::class)
    fun isMainProcess(context: Context): Boolean {
        val processId = Process.myPid()
        val mainProcessName = getMainProcessName(context)
        return isPidOfProcessName(context, processId, mainProcessName)
    }
}