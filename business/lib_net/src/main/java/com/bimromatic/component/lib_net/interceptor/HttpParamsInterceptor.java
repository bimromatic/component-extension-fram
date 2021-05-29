package com.bimromatic.component.lib_net.interceptor;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 获取HTTP 添加公共参数的拦截器
 * 暂时支持get、head请求&Post put patch的表单数据请求
 *
 * @return
 */
public class HttpParamsInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (request.method().equalsIgnoreCase("GET") || request.method().equalsIgnoreCase("HEAD")) {
            HttpUrl httpUrl = request.url().newBuilder()
                    .addQueryParameter("version", "1.1.0")
                    .addQueryParameter("devices", "android")
                    .build();
            request = request.newBuilder().url(httpUrl).build();
        } else {
            RequestBody originalBody = request.body();
            if (originalBody instanceof FormBody) {
                FormBody.Builder builder = new FormBody.Builder();
                FormBody formBody = (FormBody) originalBody;
                for (int i = 0; i < formBody.size(); i++) {
                    builder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                }
                FormBody newFormBody = builder
                        .addEncoded("version", "1.1.0")
                        .addEncoded("devices", "android")
                        .build();
                if (request.method().equalsIgnoreCase("POST")) {
                    request = request.newBuilder().post(newFormBody).build();
                } else if (request.method().equalsIgnoreCase("PATCH")) {
                    request = request.newBuilder().patch(newFormBody).build();
                } else if (request.method().equalsIgnoreCase("PUT")) {
                    request = request.newBuilder().put(newFormBody).build();
                }

            } else if (originalBody instanceof MultipartBody) {

            }

        }
        return chain.proceed(request);
    }
}