package com.hyc.zhihu.presenter;

import android.util.Log;
import android.widget.Toast;

import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.beans.OnePictureByMonth;
import com.hyc.zhihu.beans.OnePictureData;
import com.hyc.zhihu.net.Requests;
import com.hyc.zhihu.presenter.base.IMonthPictruePresenter;
import com.hyc.zhihu.utils.AppUtil;
import com.hyc.zhihu.utils.RealmUtil;
import com.hyc.zhihu.view.MonthPictureView;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ray on 16/5/13.
 */
public class MonthPicturePresenter extends BasePresenter<MonthPictureView> implements IMonthPictruePresenter {
    public MonthPicturePresenter(MonthPictureView view) {
        super(view);
    }

    @Override
    public void getAndShowPictures(String date) {
        Requests.getApi().getPictureByMonth(date).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<OnePictureByMonth>() {
            @Override
            public void call(OnePictureByMonth onePictureByMonth) {
                mView.showPictures(onePictureByMonth.getData());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                AppUtil.showToast("网络错误");
        }
        });
    }
}
