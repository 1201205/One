package com.hyc.zhihu.view;

import com.hyc.zhihu.base.BaseView;
import com.hyc.zhihu.beans.Comment;
import com.hyc.zhihu.beans.Question;
import com.hyc.zhihu.beans.QuestionContent;

import java.util.List;

/**
 * Created by Administrator on 2016/5/18.
 */
public interface QuestionContentView extends BaseView{
    void showContent(QuestionContent content);
    void showRelate(List<Question> questions);
    void refreshCommentList(List<Comment> comments);
}
