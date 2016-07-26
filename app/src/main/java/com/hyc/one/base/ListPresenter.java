package com.hyc.one.base;

/**
 * Created by Administrator on 2016/7/12.
 */
public abstract class ListPresenter<T extends BaseView> extends BasePresenter {
    protected T mRealView;

    public ListPresenter(BaseView view) {
        super(view);
    }

    public abstract void refresh();

    public abstract void showList(String id);

    public abstract void getAndShow();
}
