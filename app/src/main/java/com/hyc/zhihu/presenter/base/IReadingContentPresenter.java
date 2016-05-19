package com.hyc.zhihu.presenter.base;

/**
 * Created by Administrator on 2016/5/18.
 */
public interface IReadingContentPresenter {
    void getAndShowContent(String id);
    void getAndShowCommentList();
    void getAndShowRelate(String id);
}
