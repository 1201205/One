package com.hyc.one.presenter;

import com.hyc.one.base.BasePresenter;
import com.hyc.one.beans.BaseBean;
import com.hyc.one.beans.SerialList;
import com.hyc.one.net.Requests;
import com.hyc.one.presenter.base.ISerialListPresenter;
import com.hyc.one.view.SerialListView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/5/19.
 */
public class SerialListPresenter extends BasePresenter<SerialListView> implements ISerialListPresenter {
    public SerialListPresenter(SerialListView view) {
        super(view);
    }

    @Override
    public void getAndShowList(String id) {
        mView.showLoading();
        mCompositeSubscription.add(
                Requests.getApi().getSerialListByID(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<BaseBean<SerialList>>() {
                    @Override
                    public void call(BaseBean<SerialList> serialListWrapper) {
                        SerialList list = serialListWrapper.getData();
                        String title = list.getTitle();
                        if ("0".equals(list.getFinished())) {
                            title = title + "(未完结)";
                        }
                        mView.showList(list.getList(), title);
                        mView.dismissLoading();
                    }
                }));
    }
}
