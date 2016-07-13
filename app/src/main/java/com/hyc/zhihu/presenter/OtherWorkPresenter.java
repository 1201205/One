package com.hyc.zhihu.presenter;

import android.text.TextUtils;
import com.hyc.zhihu.base.DefaultTransformer;
import com.hyc.zhihu.base.ExceptionAction;
import com.hyc.zhihu.base.ListPresenter;
import com.hyc.zhihu.base.NoThingGetException;
import com.hyc.zhihu.beans.BaseBean;
import com.hyc.zhihu.beans.Essay;
import com.hyc.zhihu.beans.music.MusicMonthItem;
import com.hyc.zhihu.net.Requests;
import com.hyc.zhihu.view.OtherPictureView;
import java.util.List;
import rx.Observable;
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
