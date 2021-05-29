package com.bimromatic.component.lib_net.interfaces;


import com.bimromatic.component.lib_net.BuildConfig;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/23/21
 * desc   : 定义了一些接口
 * version: 1.0
 */
public interface ApiServer {

    String HOST = BuildConfig.DEBUG ? BuildConfig.DEBUG_URL_BASE : BuildConfig.REALSE_URL_BASE;

}
