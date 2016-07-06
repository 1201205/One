package com.hyc.zhihu.view;

import com.hyc.zhihu.base.BaseView;
import com.hyc.zhihu.beans.Comment;
import com.hyc.zhihu.beans.Question;
import com.hyc.zhihu.beans.QuestionContent;
import com.hyc.zhihu.beans.Serial;
import com.hyc.zhihu.beans.SerialContent;

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
