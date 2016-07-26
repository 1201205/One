package com.hyc.one.presenter;

import com.hyc.one.base.DefaultTransformer;
import com.hyc.one.base.ExceptionAction;
import com.hyc.one.base.ListPresenter;
import com.hyc.one.beans.BaseBean;
import com.hyc.one.beans.movie.Movie;
import com.hyc.one.net.Requests;
import com.hyc.one.view.OtherPictureView;
import java.util.List;

import rx.functions.Action1;

/**
 * Created by Administrator on 2016/5/30.
 */
public class MovieListPresenter extends ListPresenter<OtherPictureView> {
    private String mLastIndex="0";

    public MovieListPresenter(OtherPictureView view) {
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
