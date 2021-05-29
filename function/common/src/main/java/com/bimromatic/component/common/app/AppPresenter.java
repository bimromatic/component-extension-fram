package com.bimromatic.component.common.app;

import android.content.Context;

import com.bimromatic.component.common.observer.BaseObserver;
import com.bimromatic.component.common.parms.ParamsMap;
import com.bimromatic.component.lib_net.impl.IBaseView;
import com.bimromatic.component.common.impl.PresenterImpl;
import com.bimromatic.component.lib_net.RetrofitHelper;
import com.bimromatic.component.lib_net.interfaces.ApiServer;

import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/23/21
 * desc   :
 * version: 1.0
 */
public abstract class AppPresenter <V extends IBaseView>{

    private static final String TAG = "AppPresenter";

    private CompositeDisposable compositeDisposable;
    public V baseView;
    protected ApiServer apiServer = RetrofitHelper.getInstance().getApiService();
    protected Context mContext;
    protected Map<String,Object> mParams = ParamsMap.INSTANCE.getmParams();


    public AppPresenter() {

    }

    public void attachView(V view) {
        this.baseView = view;
    }

    /**
     * 解除绑定
     */
    public void detachView() {
        baseView = null;
        removeDisposable();
    }
    /**
     * 返回 view_common_webview
     *
     * @return
     */
    public V getBaseView() {
        return baseView;
    }

    public void addDisposable(Observable<?> observable, BaseObserver observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }

//    public void addFileDisposable(Observable<?> observable, FileObserver observer) {
//        if (compositeDisposable == null) {
//            compositeDisposable = new io.reactivex.disposables.CompositeDisposable();
//        }
//        compositeDisposable.add(observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(observer));
//    }

    public void removeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}

