package com.hyc.zhihu.presenter.base;

/**
 * Created by Administrator on 2016/5/18.
 */
public interface IQuestionContentPresenter {
    void getAndShowContent(String id);
    void getAndShowCommentList(String type,String id);
    void getAndShowRelate(String id);
}
