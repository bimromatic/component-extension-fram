package com.bimromatic.component.common.impl;

import android.content.Context;

import com.bimromatic.component.lib_net.impl.IBaseView;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/19/21
 * desc   :
 * version: 1.0
 */
public interface PresenterImpl {

    void setContext(Context context);

    void onStart(IBaseView view);

    void onDestroy();
}
