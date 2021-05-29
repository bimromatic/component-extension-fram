package com.bimromatic.component.lib_net.interceptor;

import com.bimromatic.component.lib_net.interfaces.ApiServer;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 2019-12-04
 * desc   :
 * version: 1.0
 */
public class MockInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Gson gson = new Gson();
        Response response = null;
        Response.Builder responseBuilder = new Response.Builder()
                .code(200)
                .message("")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .addHeader("content-type", "application/json");
        Request request = chain.request();
        if(request.url().toString().contains(ApiServer.HOST)) { //拦截指定地址
            //            String responseString = "{\n" +
            //                    "\t\"success\": false,\n" +
            //                    "\t\"message\": \"用户未认证或token过期，请重新登录后继续\",\n" +
            //                    "\t\"request_time\": \"2019-06-10T10:15:09.132+08:00\",\n" +
            //                    "\t\"error\": \"UNAUTHENTICATED\",\n" +
            //                    "\t\"path\": \"/error\"\n" +
            //                    "}";
            //responseBuilder.body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes()));//将数据设置到body中
            //response = responseBuilder.build(); //builder模式构建response
            response = chain.proceed(request);
        }else{
            response = chain.proceed(request);
        }
        return response;
    }
}
