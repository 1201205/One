package com.hyc.zhihu.base;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by ray on 16/4/19.
 */
public class BasePresenter<T extends BaseView> {
    public CompositeSubscription mCompositeSubscription;
    protected T mView;

    public BasePresenter(T view) {
        this.mView = view;
    }

    public void attachView() {
        mCompositeSubscription = new CompositeSubscription();
    }

    public void detachView() {
        mCompositeSubscription.unsubscribe();
        mCompositeSubscription = null;
        mView = null;
    }
}
