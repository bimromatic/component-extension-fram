//package com.bimromatic.nest_tree.moudle_reader
//
//import android.app.Application
//import android.util.Log
//import com.wang.android.starter.IStarter
//import com.wang.android.starter.annotation.Starter
//import com.wang.android.starter.annotation.StarterFinish
//import com.wang.android.starter.annotation.StarterMethod
//
//
//@Starter
//class KotlinStarter : IStarter {
//
//    @StarterMethod(priority = 30, isSync = true, isDelay = true)
//    public fun initBimromatic() {
//        Log.e("initBimromatic","初始化"+Thread.currentThread().name)
//    }
//
//    @StarterFinish(listen = "initBimromatic")
//    public fun listenTest() {
//        Log.e("initBimromatic", "test初始化完成")
//    }
//
//
//    @StarterMethod(priority = 50, isSync = true, isDelay = true)
//    public fun initBimromatic2() {
//        Log.e("initBimromatic","初始化2"+Thread.currentThread().name)
//    }
//
//    @StarterFinish(listen = "initBimromatic2")
//    public fun listenTest2() {
//        Log.e("initBimromatic", "tes2t初始化完成")
//    }
//}