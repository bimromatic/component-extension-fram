//package com.bimromatic.component.lib_net.interceptor;
//
//
//import com.android.remote_app.http.body.ProgressResponseBody;
//import com.android.remote_app.interfaces.IBaseView;
//import com.android.remote_app.utils.log.KLog;
//
//import java.io.IOException;
//
//import okhttp3.Interceptor;
//import okhttp3.Request;
//import okhttp3.Response;
//
///**
// * author : bimromatic
// * e-mail : xxx@xx
// * time   : 2019-12-04
// * desc   : 文件下载进度拦截
// * version: 1.0
// */
//public class ProgressInterceptor implements Interceptor {
//
//    private static IBaseView mBaseView = null;
//
//    public ProgressInterceptor(IBaseView baseView) {
//        mBaseView = baseView;
//    }
//
//
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        Request request = chain.request();
//        if (mBaseView != null) {
//            Response response = chain.proceed(request);
//            return response.newBuilder().body(new ProgressResponseBody(response.body(),
//                    new ProgressResponseBody.ProgressListener() {
//                        @Override
//                        public void onProgress(long totalSize, long downSize) {
//                            int progress = (int) (downSize * 100 / totalSize);
//                            if (mBaseView != null) {
//                                mBaseView.onProgress(progress);
//                                KLog.e("文件下载速度 === " + progress);
//                            }
//                        }
//                    })).build();
//        } else {
//            return chain.proceed(request);
//        }
//    }
//}
