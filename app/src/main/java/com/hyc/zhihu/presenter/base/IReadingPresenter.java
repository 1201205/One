package com.hyc.zhihu.presenter.base;

/**
 * Created by Administrator on 2016/5/16.
 */
public interface IReadingPresenter {
    void getAndShowHead();
    void showContent(boolean hasNetWork);
    void getAndShowList(int index);
}
