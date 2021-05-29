package com.bimromatic.component.common.observer

import android.view.Gravity
import com.alibaba.fastjson.JSONException
import com.alibaba.fastjson.JSONObject
import com.bimromatic.component.lib_net.code.NetGlobleStatusCode
import com.bimromatic.component.lib_net.entiy.BaseEntity
import com.bimromatic.component.lib_net.impl.IBaseView
import com.google.gson.JsonParseException
import com.hjq.toast.ToastUtils
import io.reactivex.rxjava3.observers.DisposableObserver
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/23/21
 * desc   :
 * version: 1.0
 */
abstract class BaseObserver<T>: DisposableObserver<BaseEntity<T>>() {

    protected var view: IBaseView? = null

    /**
     * 网络连接失败  无网
     */
    val NETWORK_ERROR = 100000

    /**
     * 解析数据失败
     */
    val PARSE_ERROR = 1008

    /**
     * 网络问题
     */
    val BAD_NETWORK = 1007

    /**
     * 连接错误
     */
    val CONNECT_ERROR = 1006

    /**
     * 连接超时
     */
    val CONNECT_TIMEOUT = 1005

    /**
     * 其他所有情况
     */
    val NOT_TRUE_OVER = 1004


    fun BaseObserver(view: IBaseView?) {
        this.view = view
        // 设置吐司重心
        ToastUtils.setGravity(Gravity.CENTER, 0, 0)
    }

    fun BaseObserver() {}

    override fun onStart() {
        //view?.showLoading()
    }

    override fun onNext(o: BaseEntity<T>) {
        try {
            view?.hideLoading()
            if (o.code === NetGlobleStatusCode.SUCCESS_CODE) {
                onSuccess(JSONObject.parseObject(JSONObject.toJSON(o).toString()) as T)
            } else if (o.code === NetGlobleStatusCode.TOKEN_EXPIRED) {
                //val baseEvent = BaseEvent()
                //baseEvent.setObject(NetGlobleStatusCode.TOKEN_EXPIRED)
                //RxBus.getDefault().post(baseEvent)
            } else {
                ToastUtils.show(o.getMsg())
                //UIUtils.showToastSafely(o.getMsg());
                //view?.onErrorCode(o)
                //非true的所有情况
                //onException(PARSE_ERROR, o.getMsg());
                onException(NOT_TRUE_OVER, o.getMsg())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            onError(e.toString())
        }
    }

    override fun onError(e: Throwable?) {
        view?.hideLoading()
        if (e is HttpException) {
            //   HTTP错误
            onException(BAD_NETWORK, "")
        } else if (e is ConnectException
                || e is UnknownHostException) {
            //   连接错误
            onException(CONNECT_ERROR, "")
        } else if (e is InterruptedIOException) {
            //  连接超时
            onException(CONNECT_TIMEOUT, "")
        } else if (e is JsonParseException
                || e is JSONException
                || e is ParseException) {
            //  解析错误
            onException(PARSE_ERROR, "")
            e.printStackTrace()
        } else {
            if (e != null) {
                onError(e.toString())
            } else {
                onError("未知错误")
            }
        }
    }


    private fun onException(unknownError: Int, message: String) {
        when (unknownError) {
            CONNECT_ERROR -> onError("连接错误")
            CONNECT_TIMEOUT -> onError("连接超时")
            BAD_NETWORK -> onError("网络超时")
            PARSE_ERROR -> onError("数据解析失败")
            NOT_TRUE_OVER -> onError(message)
            else -> {
            }
        }
    }

    //消失写到这 有一定的延迟  对dialog显示有影响
    override fun onComplete() {
        /* if (view_common_webview != null) {
            view_common_webview.hideLoading();
        }*/
    }

    abstract fun onSuccess(respond: T)

    abstract fun onError(msg: String?)
}