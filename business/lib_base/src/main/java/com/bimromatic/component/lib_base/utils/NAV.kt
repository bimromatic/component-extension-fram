package com.bimromatic.component.lib_base.utils

import android.content.Context
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter


/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/21/21
 * desc   :
 * version: 1.0
 */
object NAV {

    fun go(path: String?) {
        ARouter.getInstance().build(path).navigation()
    }

    fun go(path: String?, bundle: Bundle?) {
        ARouter.getInstance().build(path).with(bundle).navigation()
    }

    fun go(path: String?, resultCode: Int) {
        ARouter.getInstance().build(path).withFlags(resultCode).navigation()
    }

    fun go(path: String?, bundle: Bundle?, resultCode: Int) {
        ARouter.getInstance().build(path).with(bundle).withFlags(resultCode).navigation()
    }

    fun go(context: Context?, path: String?) {
        ARouter.getInstance().build(path).navigation(context)
    }

    fun go(context: Context?, path: String?, bundle: Bundle?) {
        ARouter.getInstance().build(path).with(bundle).navigation(context)
    }

    fun go(context: Context?, path: String?, resultCode: Int) {
        ARouter.getInstance().build(path).withFlags(resultCode).navigation(context)
    }

    fun go(context: Context?, path: String?, bundle: Bundle?, resultCode: Int) {
        ARouter.getInstance().build(path).with(bundle).withFlags(resultCode).navigation(context)
    }

    fun go(context: Context?, path: String?, enterAnim: Int, exitAnim: Int) {
        ARouter.getInstance().build(path)
                .withTransition(enterAnim, exitAnim).navigation(context)
    }

    fun go(context: Context?, path: String?, bundle: Bundle?, enterAnim: Int, exitAnim: Int) {
        ARouter.getInstance().build(path)
                .with(bundle)
                .withTransition(enterAnim, exitAnim).navigation(context)
    }

    fun go(context: Context?, path: String?, resultCode: Int, enterAnim: Int, exitAnim: Int) {
        ARouter.getInstance().build(path)
                .withFlags(resultCode)
                .withTransition(enterAnim, exitAnim).navigation(context)
    }

    fun go(context: Context?, path: String?, bundle: Bundle?, resultCode: Int, enterAnim: Int, exitAnim: Int) {
        ARouter.getInstance().build(path)
                .with(bundle).withFlags(resultCode)
                .withTransition(enterAnim, exitAnim).navigation(context)
    }
}