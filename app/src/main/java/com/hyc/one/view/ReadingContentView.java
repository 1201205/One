package com.hyc.one.view;

import com.hyc.one.base.BaseView;
import com.hyc.one.beans.Comment;

import java.util.List;

/**
 * Created by Administrator on 2016/5/18.
 */
public interface ReadingContentView<T,V> extends BaseView{
    void showContent(T content);
    void showRelate(List<V> serials);
    void refreshCommentList(List<Comment> comments);
    void showHotComments(List<Comment> comments);
    void showNoComments();
}
