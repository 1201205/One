package com.hyc.one.view;

import com.hyc.one.base.BaseView;
import com.hyc.one.beans.movie.MovieStory;

import java.util.List;

/**
 * Created by ray on 16/4/19.
 */
public interface MovieStoryView extends BaseView{
    void showList(List<MovieStory> stories);
    void refreshList(List<MovieStory> stories);
}
