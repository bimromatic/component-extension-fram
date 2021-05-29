package com.bimromatic.component.lib_net.interceptor;


import android.util.Log;


import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 2019-12-04
 * desc   :请求访问quest  打印日志 response拦截器
 * version: 1.0
 */
public class JournalInterceptor implements Interceptor {

    private String TAG = "JournalInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        try {
            long startTime = System.currentTimeMillis();
            Response response = chain.proceed(request);
            if (response == null) {
                return chain.proceed(request);
            }
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            MediaType mediaType = response.body().contentType();
            Log.e("MediaType",""+mediaType.toString());
            String content = response.body().string();

            Logger.d(TAG, "----------Request Start----------------");
            Logger.d(TAG, "| " + request.toString() + "===========" + request.headers().toString());
            Logger.d(TAG, "| " + response.code());
            Logger.json(content);
            Logger.d(content);
            Logger.d(TAG, "----------Request End:" + duration + "毫秒----------");

            return response.newBuilder()
                    .body(ResponseBody.create(mediaType, content))
                    .build();
            //return  chain.proceed(chain.request().newBuilder().addHeader())
        } catch (Exception e) {
            Log.e(TAG,""+e.getMessage());
            e.printStackTrace();
            return chain.proceed(request);
        }
    }
}
