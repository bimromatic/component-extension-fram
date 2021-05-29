package com.bimromatic.component.common.startup.asyn

import android.util.Log
import com.smartzheng.launcherstarter.task.Task

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/10/21
 * desc   : Block mode initialization
 * version: 1.0
 */
class BlockAsynTask:Task() {

    override fun needWait(): Boolean {
        return true
    }

    override fun dependsOn(): MutableList<Class<out Task>> {
        return mutableListOf(MainAsynTask::class.java)
    }

    override fun run() {
        Log.e("Task","BlockAsynTask");
    }
}