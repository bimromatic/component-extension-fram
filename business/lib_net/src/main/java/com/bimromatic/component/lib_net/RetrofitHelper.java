package com.bimromatic.component.lib_net;

import android.text.TextUtils;

import com.bimromatic.component.lib_net.dns.ApiDns;
import com.bimromatic.component.lib_net.impl.IBaseView;
import com.bimromatic.component.lib_net.analysis.DoubleDefaultAdapter;
import com.bimromatic.component.lib_net.analysis.IntegerDefaultAdapter;
import com.bimromatic.component.lib_net.analysis.LongDefaultAdapter;
import com.bimromatic.component.lib_net.analysis.StringNullAdapter;
import com.bimromatic.component.lib_net.interceptor.HttpHeaderInterceptor;
import com.bimromatic.component.lib_net.interceptor.JournalInterceptor;
import com.bimromatic.component.lib_net.interfaces.ApiServer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static retrofit2.converter.gson.GsonConverterFactory.create;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/23/21
 * desc   :
 * version: 1.0
 */
public final class RetrofitHelper {

    private String TAG = "RetrofitUtils %s";

    private static RetrofitHelper mRetrofitHelper;
    private Retrofit retrofit;
    private ApiServer apiServer;

    private Gson gson;
    private static final int DEFAULT_TIMEOUT = 15;

    private static List<Retrofit> mRetrofitList = new ArrayList<>();
    private static List<RetrofitHelper> mRetrofitHelperList = new ArrayList<>();
    public static String mBaseUrl = ApiServer.HOST;

    private static IBaseView mBaseView = null;
    private static volatile RetrofitHelper.Type mType = RetrofitHelper.Type.BASE;

    public ApiServer getApiService() {
        return apiServer;
    }

    public enum Type {
        FILE,
        BASE,
        BASE_URL,
    }

    public RetrofitHelper.Type getType() {
        return mType;
    }

    public static void setType(RetrofitHelper.Type type) {
        mType = type;
    }

//    /**
//     * 文件处理
//     *
//     * @param httpClientBuilder
//     */
//    public void initFileClient(OkHttpClient.Builder httpClientBuilder) {
//        /**
//         * 处理文件下载进度展示所需
//         */
//        httpClientBuilder.addNetworkInterceptor(new ProgressInterceptor(mBaseView));
//    }

    /**
     * 默认所需
     *
     * @param httpClientBuilder
     */
    public void initDefaultClient(OkHttpClient.Builder httpClientBuilder) {
        /**
         * 处理一些识别识别不了 ipv6手机，如小米  实现方案  将ipv6与ipv4置换位置，首先用ipv4解析
         */
//        httpClientBuilder.dns(new ApiDns());

        /**
         * 添加cookie管理
         * 方法1：第三方框架
         */
//        PersistentCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(),
//                new SharedPrefsCookiePersistor(App.getInstance()));
//        httpClientBuilder.cookieJar(cookieJar);

        /**
         * 添加cookie管理
         * 方法2：手动封装cookie管理
         */
        //httpClientBuilder.cookieJar(new CookieManger(App.getContext()));

        /**
         * 添加日志拦截
         */
        httpClientBuilder.addInterceptor(new JournalInterceptor());

        /**
         * 添加日志拦截  第三方框架方案
         */
        //HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        //logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //httpClientBuilder.addInterceptor(logInterceptor);
        /**
         * 添加请求头
         */
        httpClientBuilder.addInterceptor(new HttpHeaderInterceptor());
    }


    public RetrofitHelper() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder
                //.networkInterceptors().add(new StethoInterceptor())
                //.cookieJar(new CookieManger(App.getInstance()))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);//错误重联

        switch (getType()) {
            case FILE:
                //initFileClient(httpClientBuilder);
                break;
            case BASE:
            case BASE_URL:
                initDefaultClient(httpClientBuilder);
                break;
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(create(buildGson()))//添加json转换框架(正常转换框架)
                //                .addConverterFactory(MyGsonConverterFactory.create(buildGson()))//添加json自定义（根据需求，此种方法是拦截gson解析所做操作）
                //支持RxJava2
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClientBuilder.build())
                .build();

        apiServer = retrofit.create(ApiServer.class);
        mRetrofitList.add(retrofit);
    }


    /**
     * 增加后台返回""和"null"的处理,如果后台返回格式正常
     * 1.int=>0
     * 2.double=>0.00
     * 3.long=>0L
     * 4.String=>""
     *
     * @return
     */
    public Gson buildGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .registerTypeAdapter(Integer.class, new IntegerDefaultAdapter())
                    .registerTypeAdapter(int.class, new IntegerDefaultAdapter())
                    .registerTypeAdapter(Double.class, new DoubleDefaultAdapter())
                    .registerTypeAdapter(double.class, new DoubleDefaultAdapter())
                    .registerTypeAdapter(Long.class, new LongDefaultAdapter())
                    .registerTypeAdapter(long.class, new LongDefaultAdapter())
                    .registerTypeAdapter(String.class, new StringNullAdapter())
                    .create();
        }
        return gson;
    }

    /**
     * 默认使用方式
     *
     * @return
     */
    public static RetrofitHelper getInstance() {
        setType(RetrofitHelper.Type.BASE);
        mBaseView = null;
        mBaseUrl = ApiServer.HOST;

        return initRetrofit();
    }

    /**
     * 文件下载使用方式
     *
     * @param baseView
     * @return
     */
    public static RetrofitHelper getFileInstance(IBaseView baseView) {
        setType(RetrofitHelper.Type.FILE);
        mBaseView = baseView;
        mBaseUrl = ApiServer.HOST + "file/";

        return initRetrofit();
    }

    /**
     * 动态改变baseUrl使用方式
     *
     * @param baseUrl
     * @return
     */
    public static RetrofitHelper getBaseUrlInstance(String baseUrl) {
        setType(RetrofitHelper.Type.BASE_URL);
        mBaseView = null;
        if (!TextUtils.isEmpty(baseUrl)) {
            mBaseUrl = baseUrl;
        } else {
            mBaseUrl = ApiServer.HOST;
        }
        return initRetrofit();
    }

    private static RetrofitHelper initRetrofit() {
        int mIndex = -1;
        for (int i = 0; i < mRetrofitList.size(); i++) {
            if (mBaseUrl.equals(mRetrofitList.get(i).baseUrl().toString())) {
                mIndex = i;
                break;
            }
        }

        //新的baseUrl
        if (mIndex == -1) {
            synchronized (Object.class) {
                mRetrofitHelper = new RetrofitHelper();
                mRetrofitHelperList.add(mRetrofitHelper);
                return mRetrofitHelper;
            }
        } else {
            //以前已经创建过的baseUrl
            return mRetrofitHelperList.get(mIndex);
        }
    }

}
