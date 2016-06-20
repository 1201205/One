package com.hyc.zhihu.base;

import android.app.Activity;

/**
 * Created by ray on 16/4/19.
 */
public class BasePresenter<T extends BaseView> {
    protected T mView;
    public BasePresenter(T view){
        this.mView=view;
    }
}
