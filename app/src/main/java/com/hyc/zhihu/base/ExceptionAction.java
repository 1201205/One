package com.hyc.zhihu.base;

import android.text.TextUtils;

import com.hyc.zhihu.utils.AppUtil;
import com.hyc.zhihu.utils.S;

import rx.functions.Action1;

/**
 * Created by Administrator on 2016/7/7.
 */
public abstract class ExceptionAction implements Action1<Throwable> {

    public abstract void onNothingGet();

    @Override
    public void call(Throwable throwable) {
        if (!TextUtils.isEmpty(throwable.getMessage())&&throwable.getMessage().contains(S.NO_THING_GET)) {
            onNothingGet();
        } else {
            AppUtil.showToast("网络错误，请检查");
        }
    }
}
