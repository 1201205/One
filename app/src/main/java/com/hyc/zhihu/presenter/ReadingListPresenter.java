package com.hyc.zhihu.presenter;

import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.beans.ReadingListItems;
import com.hyc.zhihu.net.Requests;
import com.hyc.zhihu.presenter.base.IReadingListPresenter;
import com.hyc.zhihu.view.ReadingListView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/5/18.
 */
public class ReadingListPresenter extends BasePresenter<ReadingListView> implements IReadingListPresenter {
    public ReadingListPresenter(ReadingListView view) {
        super(view);
    }

    @Override
    public void getAndShowEssayList(String id) {
        mView.showLoading();

        Requests.getApi().getEssayListByID(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ReadingListItems>() {
            @Override
            public void call(ReadingListItems readingListItems) {
                mView.showList(readingListItems.getData());
                mView.dismissLoading();

            }
        });
    }
}
