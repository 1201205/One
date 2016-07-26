package com.hyc.one.presenter;

import android.text.TextUtils;

import com.hyc.one.base.DefaultTransformer;
import com.hyc.one.base.ExceptionAction;
import com.hyc.one.base.ListPresenter;
import com.hyc.one.beans.BaseBean;
import com.hyc.one.beans.OtherDiary;
import com.hyc.one.net.Requests;
import com.hyc.one.view.OtherPictureView;
import java.util.List;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/7/11.
 */
public class OtherDairyPresenter extends ListPresenter<OtherPictureView<OtherDiary>> {
    private String mIndex;
    private String mID;


    public OtherDairyPresenter(OtherPictureView view) {
        super(view);
        mRealView=view;
    }

    @Override
    public void showList(String id) {
        mView.showLoading();
        mID = id;
        getAndShow();
    }
    @Override
    public void getAndShow() {
        if (TextUtils.isEmpty(mIndex)) {
            mIndex = "0";
        }
        mCompositeSubscription.add(
            Requests.getApi()
                .getOtherDiaryByID(mID, mIndex)
                .compose(new DefaultTransformer<BaseBean<List<OtherDiary>>, List<OtherDiary>>())
                .subscribe(new Action1<List<OtherDiary>>() {
                    @Override
                    public void call(List<OtherDiary> oneDairyDatas) {
                        mRealView.dismissLoading();
                        if ("0".equals(mIndex)) {
                            mRealView.showList(oneDairyDatas);
                        } else {
                            mRealView.refresh(oneDairyDatas);
                        }

                        mIndex = oneDairyDatas.get(oneDairyDatas.size() - 1).getId();
                    }
                }, new ExceptionAction() {
                    @Override
                    public void onNothingGet() {
                        mRealView.dismissLoading();
                        mRealView.nothingGet();
                    }
                }));
    }

    @Override
    public void refresh() {
        getAndShow();
    }
}
