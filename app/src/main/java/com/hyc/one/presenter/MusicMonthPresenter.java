package com.hyc.one.presenter;

import com.hyc.one.base.BasePresenter;
import com.hyc.one.base.DefaultTransformer;
import com.hyc.one.base.ExceptionAction;
import com.hyc.one.beans.BaseBean;
import com.hyc.one.beans.music.MusicMonthItem;
import com.hyc.one.net.Requests;
import com.hyc.one.presenter.base.IMusicMonthPresenter;
import com.hyc.one.view.MusicMonthView;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by ray on 16/5/25.
 */
public class MusicMonthPresenter extends BasePresenter<MusicMonthView> implements IMusicMonthPresenter {
    public MusicMonthPresenter(MusicMonthView view) {
        super(view);
    }

    @Override
    public void showList(String date) {
        mView.showLoading();
        mCompositeSubscription.add(
                Requests.getApi().getMusicByMonth(date).compose(new DefaultTransformer<BaseBean<List<MusicMonthItem>>, List<MusicMonthItem>>()).subscribe(new Action1<List<MusicMonthItem>>() {
                    @Override
                    public void call(List<MusicMonthItem> musicMonthWrapper) {
                        mView.showList(musicMonthWrapper);
                        mView.dismissLoading();
                    }
                }, new ExceptionAction() {
                    @Override
                    public void onNothingGet() {
                        mView.dismissLoading();
                    }
                }));
    }
}
