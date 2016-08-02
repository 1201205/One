package com.hyc.one.base;

import com.google.gson.JsonParseException;
import com.hyc.one.R;
import com.hyc.one.utils.AppUtil;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.exceptions.Exceptions;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/7/7.
 */
public class ExceptionAction implements Action1<Throwable> {

    private boolean toast = true;

    protected void onNothingGet() {
    }

    protected void onNoNetWork() {
    }

    protected void dismissLoading() {
    }

    public ExceptionAction(boolean toast) {
        this.toast = toast;
    }

    public ExceptionAction() {
        toast = true;
    }

    @Override
    public void call(Throwable throwable) {
        throwable.printStackTrace();
        dismissLoading();
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
            if (toast) {
                AppUtil.showToast(R.string.net_error);
            }
            onNoNetWork();
        } else if (throwable instanceof SocketTimeoutException) {
            AppUtil.showToast(R.string.time_out);
        } else {
            AppUtil.showToast(R.string.net_error);
        }
    }
}
