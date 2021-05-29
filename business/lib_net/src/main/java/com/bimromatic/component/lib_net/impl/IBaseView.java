package com.bimromatic.component.lib_net.impl;

import com.bimromatic.component.lib_net.entiy.BaseEntity;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/21/21
 * desc   : 所有View接口必须实现，这个接口可以什么都不做，只是用于约束类型
 * version: 1.0
 */
public interface IBaseView {
    /**
     * 显示加载
     */
    void showLoading();

    /**
     * 完成加载
     */
    void hideLoading();

//    /**
//     * 显示错误信息
//     *
//     * @param msg
//     */
//    void showError(String msg);
//
//    /**
//     * 错误码
//     */
//    void onErrorCode(BaseEntity model);
//
//    /**
//     * 进度条显示
//     */
//    void showProgress();
//
//    /**
//     * 进度条隐藏
//     */
//    void hideProgress();
//
//    /**
//     * 文件下载进度监听
//     *
//     * @param progress
//     */
//    void onProgress(int progress);
}
