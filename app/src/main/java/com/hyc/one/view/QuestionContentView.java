package com.hyc.one.view;

import com.hyc.one.base.BaseView;
import com.hyc.one.beans.Comment;
import com.hyc.one.beans.Question;
import com.hyc.one.beans.QuestionContent;

import java.util.List;

/**
 * Created by Administrator on 2016/5/18.
 */
public interface QuestionContentView extends BaseView{
    void showContent(QuestionContent content);
    void showRelate(List<Question> questions);
    void refreshCommentList(List<Comment> comments);
    void showHotComments(List<Comment> comments);
}
