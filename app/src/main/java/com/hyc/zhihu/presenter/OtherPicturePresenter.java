package com.hyc.zhihu.presenter;

import android.text.TextUtils;

import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.base.DefaultTransformer;
import com.hyc.zhihu.base.ExceptionAction;
import com.hyc.zhihu.beans.BaseBean;
import com.hyc.zhihu.beans.OnePictureData;
import com.hyc.zhihu.net.Requests;
import com.hyc.zhihu.view.OtherPictureView;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by Administrator on 2016/7/11.
 */
public class OtherPicturePresenter extends BasePresenter<OtherPictureView> {
    private String mIndex;
    private String mID;

    public OtherPicturePresenter(OtherPictureView view) {
        super(view);
    }

    public void showPicture(String id) {
        mView.showLoading();
        mID = id;
        getAndShowPicture();
    }

    public void refresh() {
        getAndShowPicture();
    }

    private void getAndShowPicture() {
        if (TextUtils.isEmpty(mIndex)) {
            mIndex = "0";
        }
        mCompositeSubscription.add(
                Requests.getApi().getOtherPictureByID(mID, mIndex).compose(new DefaultTransformer<BaseBean<List<OnePictureData>>, List<OnePictureData>>()).subscribe(new Action1<List<OnePictureData>>() {
                    @Override
                    public void call(List<OnePictureData> onePictureDatas) {
                        mView.dismissLoading();
                        if ("0".equals(mIndex)) {
                            mView.showPictures(onePictureDatas);
                        } else {
                            mView.refresh(onePictureDatas);
                        }

                        mIndex = onePictureDatas.get(onePictureDatas.size() - 1).getHpcontent_id();
                    }
                }, new ExceptionAction() {
                    @Override
                    public void onNothingGet() {
                        mView.dismissLoading();
                        mView.nothingGet();
                    }
                }));
    }
}
