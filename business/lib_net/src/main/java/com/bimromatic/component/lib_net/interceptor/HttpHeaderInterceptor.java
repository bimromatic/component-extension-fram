package com.bimromatic.component.lib_net.interceptor;


import android.util.Log;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 2019-12-04
 * desc   : 头部拦截器
 * version: 1.0
 */
public class HttpHeaderInterceptor implements Interceptor {

    private String accessToken;
    public final MediaType FORM_CONTENT_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    @Override
    public Response intercept(Chain chain) throws IOException {

        Log.e("TOKEN","请求头"+accessToken);
        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8")
                //.addHeader("Content-Type", "text/html; charset=UTF-8")
                //.addHeader("Vary", "Accept-Encoding")
                //.addHeader("Server", "Apache")
                //.addHeader("Pragma", "no-cache")
                //.addHeader("Cookie", "add cookies here")
                .addHeader("token","")
                .build();
        return chain.proceed(request);
    }
}
