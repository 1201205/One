package com.hyc.zhihu.net;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ray on 16/5/5.
 */
public class Request {
    private static Api sApi = null;
    private static Object sObject=new Object();
    public static Api getApi(){
        synchronized (sObject){
            if (sApi==null) {
                sApi=new  Retrofit.Builder().baseUrl("http://v3.wufazhuce.com:8000")
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create()).build().create(Api.class);
            }
            return sApi;
        }
    }
}
