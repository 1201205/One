package com.hyc.one.base;

import com.google.gson.JsonParseException;
import com.hyc.one.R;
import com.hyc.one.utils.AppUtil;

import java.net.SocketException;
import java.net.UnknownHostException;

import rx.exceptions.Exceptions;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/7/7.
 */
public abstract class ExceptionAction implements Action1<Throwable> {

    public abstract void onNothingGet();

    protected void onNoNetWork() {
    }

    @Override
    public void call(Throwable throwable) {
        throwable.printStackTrace();
        if (throwable instanceof NoThingGetException) {
            onNothingGet();
        } else if (throwable instanceof JsonParseException) {
            AppUtil.showToast(R.string.parse_error);
        } else if (throwable instanceof NullPointerException) {
            AppUtil.showToast(R.string.none_point_error);
            Exceptions.propagate(throwable);
        } else if (throwable instanceof SocketException) {
            AppUtil.showToast(R.string.net_error);
        } else if (throwable instanceof UnknownHostException) {
            AppUtil.showToast(R.string.net_error);
            onNoNetWork();
        } else {
            AppUtil.showToast(R.string.net_error);
        }
    }
}
