package com.hyc.zhihu.presenter;

import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.base.DefaultTransformer;
import com.hyc.zhihu.base.ExceptionAction;
import com.hyc.zhihu.base.ListPresenter;
import com.hyc.zhihu.beans.BaseBean;
import com.hyc.zhihu.beans.movie.Movie;
import com.hyc.zhihu.net.Requests;
import com.hyc.zhihu.presenter.base.IMovieListPresenter;
import com.hyc.zhihu.view.MovieListView;
import com.hyc.zhihu.view.OtherPictureView;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/5/30.
 */
public class MovieListPresenter2 extends ListPresenter<OtherPictureView> {
    private String mLastIndex="0";

    public MovieListPresenter2(OtherPictureView view) {
        super(view);
        mRealView=view;
    }

    @Override
    public void refresh() {
        getAndShow();
    }


    @Override public void showList(String id) {
        mRealView.showLoading();
        getAndShow();
    }


    @Override public void getAndShow() {
        mCompositeSubscription.add(
            Requests.getApi().getMovieList(mLastIndex).compose(new DefaultTransformer<BaseBean<List<Movie>>, List<Movie>>()).subscribe(
                new Action1<List<Movie>>() {
                    @Override
                    public void call(List<Movie> listBaseBean) {
                        if ("0".equals(mLastIndex)) {
                            mRealView.showList(listBaseBean);
                        } else {
                            mRealView.refresh(listBaseBean);
                        }
                        mLastIndex = listBaseBean.get(listBaseBean.size() - 1).getId();
                        mRealView.dismissLoading();
                    }
                }, new ExceptionAction() {
                    @Override public void onNothingGet() {
                        mRealView.nothingGet();
                        mRealView.dismissLoading();
                    }
                }));
    }
}
