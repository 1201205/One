package com.hyc.one.view;

import com.hyc.one.base.BaseView;
import com.hyc.one.beans.Comment;
import com.hyc.one.beans.movie.MovieContent;
import com.hyc.one.beans.movie.MovieStoryWrapper;

import java.util.List;

/**
 * Created by ray on 16/4/19.
 */
public interface MovieContentView extends BaseView{
    void showContent(MovieContent data);
    void refreshComment(List<Comment> comments);
    void showHotComment(List<Comment> comments);
    void showNoComments();
    void showStory(MovieStoryWrapper story);
}
