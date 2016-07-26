package com.hyc.zhihu.ui.adpter;

import com.hyc.zhihu.base.ListPresenter;
import com.hyc.zhihu.presenter.MovieListPresenter;
import com.hyc.zhihu.presenter.OtherDairyPresenter;
import com.hyc.zhihu.presenter.OtherMoviePresenter;
import com.hyc.zhihu.presenter.OtherMusicPresenter;
import com.hyc.zhihu.presenter.OtherWorkPresenter;
import com.hyc.zhihu.utils.S;
import com.hyc.zhihu.view.OtherPictureView;

/**
 * Created by Administrator on 2016/7/12.
 */
public class ListPresenterFactory {
    public static ListPresenter getPresenter(OtherPictureView view, int tag) {
        switch (tag) {
            case S.DAIRY:
                return new OtherDairyPresenter(view);
            case S.MOVIE:
                return new OtherMoviePresenter(view);
            case S.MUSIC:
                return new OtherMusicPresenter(view);
            case S.MOVIE_LIST:
                return new MovieListPresenter(view);
            case S.WORK:
                return new OtherWorkPresenter(view);
        }
        return null;
    }
}
