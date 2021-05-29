package com.bimromatic.component.lib_base.utils.net

import android.content.Context
import android.net.TrafficStats
import android.os.Handler
import android.os.Message
import java.util.*


/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/19/21
 * desc   :
 * version: 1.0
 */
class NetWorkSpeedUtils(context: Context?, mHandler: Handler?) {

    private var context: Context? = null
    private var mHandler: Handler? = null
    private var lastTotalRxBytes: Long = 0
    private var lastTimeStamp: Long = 0

    init {
        this.context = context
        this.mHandler = mHandler
    }


    var task: TimerTask = object : TimerTask() {
        override fun run() {
            showNetSpeed()
        }
    }

    fun startShowNetSpeed() {
        lastTotalRxBytes = getTotalRxBytes()
        lastTimeStamp = System.currentTimeMillis()
        Timer().schedule(task, 1000, 1000) // 1s后启动任务，每2s执行一次
    }

    private fun getTotalRxBytes(): Long {
        //return if (TrafficStats.getUidRxBytes(context?.getApplicationInfo()?.uid).let {  } == TrafficStats.UNSUPPORTED.toLong()) 0 else TrafficStats.getTotalRxBytes() / 1024 //转为KB
        return if (context?.getApplicationInfo()?.uid?.let { TrafficStats.getUidRxBytes(it) } == TrafficStats.UNSUPPORTED.toLong()) 0 else TrafficStats.getTotalRxBytes() / 1024 //转为KB
    }

    private fun showNetSpeed() {
        val nowTotalRxBytes = getTotalRxBytes()
        val nowTimeStamp = System.currentTimeMillis()
        val speed = (nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp) //毫秒转换
        val speed2 = (nowTotalRxBytes - lastTotalRxBytes) * 1000 % (nowTimeStamp - lastTimeStamp) //毫秒转换
        lastTimeStamp = nowTimeStamp
        lastTotalRxBytes = nowTotalRxBytes
        val msg: Message = mHandler!!.obtainMessage()
        msg.what = 100
        msg.obj = "$speed.$speed2 kb/s"
        mHandler?.sendMessage(msg) //更新界面
    }
}