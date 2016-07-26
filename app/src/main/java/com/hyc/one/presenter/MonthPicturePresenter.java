package com.hyc.one.presenter;

import com.hyc.one.R;
import com.hyc.one.base.BasePresenter;
import com.hyc.one.beans.BaseBean;
import com.hyc.one.beans.OnePictureData;
import com.hyc.one.net.Requests;
import com.hyc.one.presenter.base.IMonthPicturePresenter;
import com.hyc.one.utils.AppUtil;
import com.hyc.one.view.MonthPictureView;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by ray on 16/5/13.
 */
public class MonthPicturePresenter extends BasePresenter<MonthPictureView> implements IMonthPicturePresenter {
    public MonthPicturePresenter(MonthPictureView view) {
        super(view);
    }

    @Override
    public void getAndShowPictures(String date) {
        mView.showLoading();
        mCompositeSubscription.add(
                Requests.getApi().getPictureByMonth(date).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<BaseBean<List<OnePictureData>>>() {
                    @Override
                    public void call(BaseBean<List<OnePictureData>> onePictureByMonth) {
                        mView.showPictures(onePictureByMonth.getData());
                        mView.dismissLoading();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        AppUtil.showToast(R.string.net_error);
                    }
                }));
    }
}
