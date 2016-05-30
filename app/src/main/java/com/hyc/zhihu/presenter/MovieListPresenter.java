package com.hyc.zhihu.presenter;

import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.beans.BaseBean;
import com.hyc.zhihu.beans.movie.Movie;
import com.hyc.zhihu.net.Requests;
import com.hyc.zhihu.presenter.base.IMovieListPresenter;
import com.hyc.zhihu.view.MovieListView;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/5/30.
 */
public class MovieListPresenter extends BasePresenter<MovieListView> implements IMovieListPresenter {
    private String mLastIndex;

    public MovieListPresenter(MovieListView view) {
        super(view);
    }

    @Override
    public void showContent() {
        Requests.getApi().getMovieList("0").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<BaseBean<List<Movie>>>() {
            @Override
            public void call(BaseBean<List<Movie>> listBaseBean) {
                List<Movie> movies = listBaseBean.getData();
                if (movies != null && movies.size() > 0) {
                    mLastIndex = movies.get(movies.size() - 1).getId();
                }
                mView.showList(listBaseBean.getData());
            }
        });
    }

    @Override
    public void refresh() {
        Requests.getApi().getMovieList(mLastIndex).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<BaseBean<List<Movie>>>() {
            @Override
            public void call(BaseBean<List<Movie>> listBaseBean) {
                mView.refreshList(listBaseBean.getData());
            }
        });
    }
}
