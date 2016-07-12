package com.hyc.zhihu.presenter;

import android.text.TextUtils;

import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.base.DefaultTransformer;
import com.hyc.zhihu.base.ExceptionAction;
import com.hyc.zhihu.base.ListPresenter;
import com.hyc.zhihu.beans.BaseBean;
import com.hyc.zhihu.beans.movie.Movie;
import com.hyc.zhihu.net.Requests;
import com.hyc.zhihu.view.OtherPictureView;

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
