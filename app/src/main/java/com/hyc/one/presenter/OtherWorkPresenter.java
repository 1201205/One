package com.hyc.one.presenter;

import com.hyc.one.base.DefaultTransformer;
import com.hyc.one.base.ExceptionAction;
import com.hyc.one.base.ListPresenter;
import com.hyc.one.beans.BaseBean;
import com.hyc.one.beans.Essay;
import com.hyc.one.net.Requests;
import com.hyc.one.view.OtherPictureView;
import java.util.List;

import rx.functions.Action1;

/**
 * Created by hyc on 2016/7/11.
 */
public class OtherWorkPresenter extends ListPresenter<OtherPictureView<Essay>> {
    private String mID;


    public OtherWorkPresenter(OtherPictureView view) {
        super(view);
        mRealView = view;
    }


    @Override
    public void showList(String id) {
        mView.showLoading();
        mID = id;
        getAndShow();
    }


    @Override
    public void refresh() {
        //Observable.error(new NoThingGetException()).subscribe(new Action1<Object>() {
        //    @Override public void call(Object aVoid) {
        //        return;
        //    }
        //}, new ExceptionAction() {
        //    @Override public void onNothingGet() {
        mRealView.dismissLoading();
        //        mRealView.nothingGet();
        //    }
        //});
    }


    @Override

    public void getAndShow() {
        mCompositeSubscription.add(
            Requests.getApi()
                .getWorkEssayByID(mID)
                .compose(new DefaultTransformer<BaseBean<List<Essay>>, List<Essay>>())
                .subscribe(new Action1<List<Essay>>() {
                    @Override
                    public void call(List<Essay> oneMuiscDatas) {
                        mRealView.dismissLoading();
                        mRealView.showList(oneMuiscDatas);
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
