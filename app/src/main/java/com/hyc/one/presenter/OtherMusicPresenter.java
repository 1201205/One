package com.hyc.one.presenter;

import android.text.TextUtils;

import com.hyc.one.base.DefaultTransformer;
import com.hyc.one.base.ExceptionAction;
import com.hyc.one.base.ListPresenter;
import com.hyc.one.beans.BaseBean;
import com.hyc.one.beans.music.MusicMonthItem;
import com.hyc.one.net.Requests;
import com.hyc.one.view.OtherPictureView;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by Administrator on 2016/7/11.
 */
public class OtherMusicPresenter extends ListPresenter<OtherPictureView<MusicMonthItem>> {
    private String mIndex;
    private String mID;

    public OtherMusicPresenter(OtherPictureView view) {
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
    public void refresh() {
        getAndShow();
    }

    @Override

    public void getAndShow() {
        if (TextUtils.isEmpty(mIndex)) {
            mIndex = "0";
        }
        mCompositeSubscription.add(
                Requests.getApi().getOtherMusicByID(mID, mIndex).compose(new DefaultTransformer<BaseBean<List<MusicMonthItem>>, List<MusicMonthItem>>()).subscribe(new Action1<List<MusicMonthItem>>() {
                    @Override
                    public void call(List<MusicMonthItem> oneMuiscDatas) {
                        mRealView.dismissLoading();
                        if ("0".equals(mIndex)) {
                            mRealView.showList(oneMuiscDatas);
                        } else {
                            mRealView.refresh(oneMuiscDatas);
                        }

                        mIndex = oneMuiscDatas.get(oneMuiscDatas.size() - 1).getId();
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
