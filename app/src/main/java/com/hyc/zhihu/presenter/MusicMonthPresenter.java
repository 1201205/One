package com.hyc.zhihu.presenter;

import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.beans.music.MusicMonthWrapper;
import com.hyc.zhihu.net.Requests;
import com.hyc.zhihu.presenter.base.IMusicMonthPresenter;
import com.hyc.zhihu.view.MusicMonthView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by ray on 16/5/25.
 */
public class MusicMonthPresenter extends BasePresenter<MusicMonthView> implements IMusicMonthPresenter{
    public MusicMonthPresenter(MusicMonthView view) {
        super(view);
    }

    @Override
    public void showList(String date) {
        Requests.getApi().getMusicByMonth(date).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<MusicMonthWrapper>() {
            @Override
            public void call(MusicMonthWrapper musicMonthWrapper) {
                mView.showList(musicMonthWrapper.getData());
            }
        });
    }
}
