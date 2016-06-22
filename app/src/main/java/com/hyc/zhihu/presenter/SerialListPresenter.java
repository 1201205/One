package com.hyc.zhihu.presenter;

import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.beans.SerialList;
import com.hyc.zhihu.beans.SerialListWrapper;
import com.hyc.zhihu.net.Requests;
import com.hyc.zhihu.presenter.base.ISerialListPresenter;
import com.hyc.zhihu.view.SerialListView;

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
        Requests.getApi().getSerialListByID(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<SerialListWrapper>() {
            @Override
            public void call(SerialListWrapper serialListWrapper) {
                SerialList list=serialListWrapper.getData();
                String title=list.getTitle();
                if ("0".equals(list.getFinished())) {
                    title=title+"(未完结)";
                }
                mView.showList(list.getList(),title);
                mView.dismissLoading();
            }
        });
    }
}
