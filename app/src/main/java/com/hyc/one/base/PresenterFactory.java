package com.hyc.one.base;

/**
 * Created by ray on 16/4/19.
 */
public interface PresenterFactory<T extends BasePresenter> {
    T create();
}
