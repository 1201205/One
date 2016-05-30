package com.hyc.zhihu.view;

import com.hyc.zhihu.base.BaseView;
import com.hyc.zhihu.beans.movie.Movie;

import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public interface MovieListView extends BaseView {
    void showList(List<Movie> movies);

    void refreshList(List<Movie> movies);
}
