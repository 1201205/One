package com.hyc.one.presenter;

import android.text.TextUtils;

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
 * Created by Administrator on 2016/7/11.
 */
public class OtherMoviePresenter extends ListPresenter<OtherPictureView<Movie>> {
    private String mIndex;
    private String mID;

    public OtherMoviePresenter(OtherPictureView view) {
        super(view);
        mRealView=view;
    }
    @Override
    public void showList(String id) {
        mView.showLoading();
        mID = id;
        getAndShow();
    }
    @Override
    public void refresh() {
        getAndShow();
    }
    @Override
    public void getAndShow() {
        if (TextUtils.isEmpty(mIndex)) {
            mIndex = "0";
        }
        mCompositeSubscription.add(
                Requests.getApi().getOtherMovieByID(mID, mIndex).compose(new DefaultTransformer<BaseBean<List<Movie>>, List<Movie>>()).subscribe(new Action1<List<Movie>>() {
                    @Override
                    public void call(List<Movie> oneMuiscDatas) {
                        mRealView.dismissLoading();
                        if ("0".equals(mIndex)) {
                            mRealView.showList(oneMuiscDatas);
                        } else {
                            mRealView.refresh(oneMuiscDatas);
                        }

                        mIndex = oneMuiscDatas.get(oneMuiscDatas.size() - 1).getId();
                    }
                }, new ExceptionAction() {
                    @Override
                    public void onNothingGet() {
                        mRealView.dismissLoading();
                        mRealView.nothingGet();
                    }
                }));
    }
}
