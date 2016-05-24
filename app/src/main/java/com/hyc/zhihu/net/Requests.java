package com.hyc.zhihu.net;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hyc.zhihu.MainApplication;
import com.hyc.zhihu.utils.NetUtil;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by ray on 16/5/5.
 */
public class Requests {
    private static Api sApi = null;
    private static Object sObject = new Object();
    private static String TAG="request";
    public static Api getApi() {
        synchronized (sObject) {
            if (sApi == null) {
                Interceptor interceptor = new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
//                        boolean enable = NetUtil.isNetworkAvailable(MainApplication.getApplication());
//                        Request request = chain.request();
//                        if (!enable) {
//                            request = request.newBuilder()
//                                    .cacheControl(CacheControl.FORCE_CACHE)
//                                    .build();
//                        }
//                        Response response = chain.proceed(request);
//
//                        if (enable) {
//                            int maxAge = 0 * 60; // 有网络时 设置缓存超时时间0个小时
//                            response.newBuilder()
//                                    .header("Cache-Control", "public, max-age=" + maxAge)
//                                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
//                                    .build();
//                        } else {
//                            int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
//                            response.newBuilder()
//                                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                                    .removeHeader("Pragma")
//                                    .build();
//                        }
//                        return response;
                        Request request = chain.request();
                        Log.v(TAG, "request:" + request.toString());
                        long t1 = System.nanoTime();
                        Response response = chain.proceed(request);
                        long t2 = System.nanoTime();
                        Log.v(TAG, String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
                        String cacheControl = request.cacheControl().toString();
                        if (TextUtils.isEmpty(cacheControl)) {
                            cacheControl = "public, max-age=60";
                        }
//                        okhttp3.MediaType mediaType = response.body().contentType();
//                        String content = response.body().string();
//                        Log.i(TAG, "response body:" + content);
                        return response.newBuilder()
                                .header("Cache-Control", cacheControl)
                                .removeHeader("Pragma")
                                .build();
                    }
                };
                //设置缓存路径
                File httpCacheDirectory = new File(MainApplication.getApplication().getCacheDir(), "responses");
                //设置缓存 10M
                Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
                OkHttpClient client = new OkHttpClient.Builder()
                        .addNetworkInterceptor(interceptor)
                        .cache(cache).build();
                sApi = new Retrofit.Builder().baseUrl("http://v3.wufazhuce.com:8000").client(client)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create()).build().create(Api.class);
            }
            return sApi;
        }
    }
}
