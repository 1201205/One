package com.hyc.zhihu.view;

import com.hyc.zhihu.base.BaseView;
import com.hyc.zhihu.beans.Comment;
import com.hyc.zhihu.beans.OnePictureData;
import com.hyc.zhihu.beans.movie.Movie;
import com.hyc.zhihu.beans.movie.MovieContent;
import com.hyc.zhihu.beans.movie.MovieStory;
import com.hyc.zhihu.beans.movie.MovieStoryWrapper;

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
