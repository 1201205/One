package com.hyc.zhihu.presenter;

import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.beans.BaseBean;
import com.hyc.zhihu.beans.OnePictureData;
import com.hyc.zhihu.net.Requests;
import com.hyc.zhihu.presenter.base.IMonthPicturePresenter;
import com.hyc.zhihu.utils.AppUtil;
import com.hyc.zhihu.view.MonthPictureView;

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
                        AppUtil.showToast("网络错误");
                    }
                }));
    }
}
