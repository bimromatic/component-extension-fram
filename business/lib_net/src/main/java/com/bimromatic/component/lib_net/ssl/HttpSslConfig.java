package com.bimromatic.component.lib_net.ssl;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/12/21
 * desc   : Https 配置类
 * version: 1.0
 */
public class HttpSslConfig {

    private final SSLSocketFactory sSLSocketFactory;
    private final X509TrustManager trustManager;

    HttpSslConfig(SSLSocketFactory sSLSocketFactory, X509TrustManager trustManager) {
        this.sSLSocketFactory = sSLSocketFactory;
        this.trustManager = trustManager;
    }

    public SSLSocketFactory getsSLSocketFactory() {
        return sSLSocketFactory;
    }

    public X509TrustManager getTrustManager() {
        return trustManager;
    }
}
