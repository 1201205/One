package com.hyc.zhihu.presenter.base;

/**
 * Created by ray on 16/5/13.
 */
public interface IMovieContentPresenter {
    void getAndShowContent(String id);
    void refreshComments();
}
