package com.bimromatic.component.lib_base.impl;

import android.view.View;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/12/21
 * desc   :
 * version: 1.0
 */
public interface INetView {

    void showLoading();

    void showLoading(View view);

    void showSuccess();

    void showEmpty();

    void showTimeOut();


    void onRetryBtnClick();

}
