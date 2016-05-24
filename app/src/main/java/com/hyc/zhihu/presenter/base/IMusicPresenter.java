package com.hyc.zhihu.presenter.base;

/**
 * Created by ray on 16/5/13.
 */
public interface IMusicPresenter {
    void getAndShowContent();
    void showCurrentRelate(int page);
    void showCurrentComment(int page);
    void showRefreshComment(int page,String index);
    void showCurrentCommentAndRelate(int page);
}
