package com.hyc.zhihu.base;

import com.hyc.zhihu.beans.BaseBean;

import rx.Observable;

/**
 * Created by Administrator on 2016/7/7.
 */
public class DefaultTransformer<T extends BaseBean<R>, R>
        implements Observable.Transformer<T, R> {


    public DefaultTransformer() {
    }

    @Override
    public Observable<R> call(Observable<T> observable) {
        return observable
                .compose(new SchedulerTransformer<T>())
                .compose(new ErrorCheckerTransformer<T, R>());
    }
}
