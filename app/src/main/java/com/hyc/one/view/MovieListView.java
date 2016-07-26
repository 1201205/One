package com.hyc.one.view;

import com.hyc.one.base.BaseView;
import com.hyc.one.beans.movie.Movie;

import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public interface MovieListView extends BaseView {
    void showList(List<Movie> movies);

    void refreshList(List<Movie> movies);
}
