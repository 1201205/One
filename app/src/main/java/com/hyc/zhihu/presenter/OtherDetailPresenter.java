package com.hyc.zhihu.presenter;

import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.base.DefaultTransformer;
import com.hyc.zhihu.base.ExceptionAction;
import com.hyc.zhihu.beans.BaseBean;
import com.hyc.zhihu.beans.Other;
import com.hyc.zhihu.beans.OtherCenter;
import com.hyc.zhihu.net.Requests;
import com.hyc.zhihu.presenter.base.IOtherDetailPresenter;
import com.hyc.zhihu.view.OtherDetailView;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/7/7.
 */
public class OtherDetailPresenter extends BasePresenter<OtherDetailView> implements IOtherDetailPresenter {
    public OtherDetailPresenter(OtherDetailView view) {
        super(view);
    }

    @Override
    public void getAndShowContent(String id) {
        mView.showLoading();
        mCompositeSubscription.add(
                Observable.just(Requests.getApi().getOtherInfoByID(id).compose(new DefaultTransformer<BaseBean<Other>, Other>()).subscribe(new Action1<Other>() {
                    @Override
                    public void call(Other other) {
                        mView.showContent(other);
                    }
                }), Requests.getApi().getOtherCenterByID(id).compose(new DefaultTransformer<BaseBean<OtherCenter>, OtherCenter>()).subscribe(new Action1<OtherCenter>() {
                    @Override
                    public void call(OtherCenter otherCenter) {
                        mView.showDairyAndMusic(otherCenter);
                    }
                })).subscribe(new Action1<Subscription>() {
                    @Override
                    public void call(Subscription subscription) {

                    }
                }, new ExceptionAction() {
                    @Override
                    public void onNothingGet() {
                        mView.onNothingGet();
                    }
                }));
    }
}
