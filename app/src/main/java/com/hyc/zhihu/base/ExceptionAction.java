package com.hyc.zhihu.base;

import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.hyc.zhihu.utils.AppUtil;
import com.hyc.zhihu.utils.S;

import java.net.SocketException;
import java.net.UnknownHostException;

import rx.exceptions.Exceptions;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/7/7.
 */
public abstract class ExceptionAction implements Action1<Throwable> {

    public abstract void onNothingGet();
    protected void onNoNetWork(){}
    @Override
    public void call(Throwable throwable) {
        throwable.printStackTrace();
        if (throwable instanceof NoThingGetException) {
            onNothingGet();
        }else if (throwable instanceof JsonParseException) {
            AppUtil.showToast("解析出错了哦。。。。");
        } else if (throwable instanceof NullPointerException) {
            AppUtil.showToast("嗨！我是空指针异常");
            Exceptions.propagate(throwable);
        }else if(throwable instanceof SocketException){
            AppUtil.showToast("网络错误，请检查");
        } else if (throwable instanceof UnknownHostException) {
            AppUtil.showToast("网络错误，请检查");
            onNoNetWork();
        } else {
            AppUtil.showToast("网络错误，请检查");
        }
    }
}
