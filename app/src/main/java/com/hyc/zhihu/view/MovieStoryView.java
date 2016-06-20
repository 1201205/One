package com.hyc.zhihu.view;

import com.hyc.zhihu.base.BaseView;
import com.hyc.zhihu.beans.OnePictureData;
import com.hyc.zhihu.beans.movie.MovieStory;

import java.util.List;

/**
 * Created by ray on 16/4/19.
 */
public interface MovieStoryView extends BaseView{
    void showList(List<MovieStory> stories);
    void refreshList(List<MovieStory> stories);
}
