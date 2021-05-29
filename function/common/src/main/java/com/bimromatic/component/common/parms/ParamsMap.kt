package com.bimromatic.component.common.parms

import java.util.*

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/23/21
 * desc   :
 * version: 1.0
 */
object ParamsMap {

    private var mParams: MutableMap<String, Any>? = null

    fun getmParams(): Map<String, Any>? {
        if (mParams == null) {
            mParams = HashMap()
        } else {
            mParams!!.clear()
        }
        return mParams
    }
}