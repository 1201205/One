package com.hyc.zhihu.presenter;

import android.text.TextUtils;
import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.base.DefaultTransformer;
import com.hyc.zhihu.base.ExceptionAction;
import com.hyc.zhihu.beans.BaseBean;
import com.hyc.zhihu.beans.music.MusicMonthItem;
import com.hyc.zhihu.net.Requests;
import com.hyc.zhihu.view.OtherPictureView;
import java.util.List;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/7/11.
 */
public class OtherMusicPresenter extends BasePresenter<OtherPictureView<MusicMonthItem>> {
    private String mIndex;
    private String mID;

    public OtherMusicPresenter(OtherPictureView view) {
        super(view);
    }

    public void showList(String id) {
        mView.showLoading();
        mID = id;
        getAndShow();
    }

    public void refresh() {
        getAndShow();
    }

    private void getAndShow() {
        if (TextUtils.isEmpty(mIndex)) {
            mIndex = "0";
        }
        mCompositeSubscription.add(
                Requests.getApi().getOtherMusicByID(mID, mIndex).compose(new DefaultTransformer<BaseBean<List<MusicMonthItem>>, List<MusicMonthItem>>()).subscribe(new Action1<List<MusicMonthItem>>() {
                    @Override
                    public void call(List<MusicMonthItem> oneMuiscDatas) {
                        mView.dismissLoading();
                        if ("0".equals(mIndex)) {
                            mView.showList(oneMuiscDatas);
                        } else {
                            mView.refresh(oneMuiscDatas);
                        }

                        mIndex = oneMuiscDatas.get(oneMuiscDatas.size() - 1).getId();
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
