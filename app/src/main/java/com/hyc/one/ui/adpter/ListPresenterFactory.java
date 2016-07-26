package com.hyc.one.ui.adpter;

import com.hyc.one.base.ListPresenter;
import com.hyc.one.presenter.MovieListPresenter;
import com.hyc.one.presenter.OtherDairyPresenter;
import com.hyc.one.presenter.OtherMoviePresenter;
import com.hyc.one.presenter.OtherMusicPresenter;
import com.hyc.one.presenter.OtherWorkPresenter;
import com.hyc.one.utils.S;
import com.hyc.one.view.OtherPictureView;

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
