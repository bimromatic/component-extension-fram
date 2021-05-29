package com.bimromatic.component.lib_base.manager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bimromatic.component.lib_base.R

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/17/21
 * desc   :
 * version: 1.0
 */
object ForegroundPushManager {

    val notificationId = 200

    //显示通知
    fun showNotification(context: Context){
        var notification = createForegroundNotification(context)
        NotificationManagerCompat.from(context).notify(notificationId, notification!!)
    }

    //隐藏通知
    fun stopNotification(context: Context){
        NotificationManagerCompat.from(context).cancel(notificationId)
    }

    /**
     * 创建服务通知
     */
    private fun createForegroundNotification(context: Context): Notification? {
        val notificationManager = NotificationManagerCompat.from(context)
        // 唯一的通知通道的id.
        val notificationChannelId = "notification_channel_id_01"

        // Android8.0以上的系统，新建消息通道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //用户可见的通道名称
            val channelName = "Foreground Service Notification"
            //通道的重要程度
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(notificationChannelId, channelName, importance)
            notificationChannel.description = "Channel description"
            //LED灯
            notificationChannel.enableLights(false)
            //无声音
            notificationChannel.setSound(null, null)
            //不震动
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val builder = NotificationCompat.Builder(context, notificationChannelId)
        //通知小图标
        builder.setSmallIcon(R.mipmap.ic_launcher)
        //通知标题
        builder.setContentTitle("bimromatic.Debug")
        //通知内容
        builder.setContentText("服务正在运行中")
        //点击通知栏关闭通知
        builder.setAutoCancel(true)
        //不能清除通知
        builder.setOngoing(true)
        //设定通知显示的时间
        builder.setWhen(System.currentTimeMillis())
        //设定启动的内容
        val activityIntent = Intent(Intent.ACTION_MAIN)
        activityIntent.addCategory(Intent.CATEGORY_LAUNCHER)

        //我这里写的每次点击通知跳转的是MainActivity，可以自己处理跳转事件
        if (ActivityManager.currentActivity()!=null){
            activityIntent.component = ComponentName(context, ActivityManager.currentActivity().javaClass)
        }else{
            activityIntent.component = ComponentName(context, "com.bimromatic.nest_tree.MainActivity")
        }
        activityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        val pendingIntent = PendingIntent.getActivity(context, 1, activityIntent, PendingIntent.FLAG_CANCEL_CURRENT )
        builder.setContentIntent(pendingIntent)
        //创建通知并返回
        return builder.build()
    }
}