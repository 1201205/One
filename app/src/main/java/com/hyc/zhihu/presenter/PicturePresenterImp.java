package com.hyc.zhihu.presenter;

import android.graphics.Picture;
import android.util.Log;

import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.beans.OnePicture;
import com.hyc.zhihu.beans.OnePictureList;
import com.hyc.zhihu.net.Api;
import com.hyc.zhihu.net.Request;
import com.hyc.zhihu.presenter.base.PicturePresenter;
import com.hyc.zhihu.view.PictureView;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ray on 16/5/5.
 */
public class PicturePresenterImp extends BasePresenter<PictureView> implements PicturePresenter {
    private List<String> mIds;
    private int mCurrentPage;

    //目前与view一一对应
    PicturePresenterImp(PictureView view) {
        super(view);
    }

    @Override
    public void getPictureIdsAndFirstItem() {
        Request.getApi().getPictureIds("0").map(new Func1<OnePictureList, Observable<OnePicture>>() {
            @Override
            public Observable<OnePicture> call(OnePictureList onePictureList) {
                mIds = onePictureList.getData();
                return Request.getApi().getPictureById(mIds.get(0));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Action1<Observable<OnePicture>>() {
                    @Override
                    public void call(Observable<OnePicture> onePictureObservable) {
                        onePictureObservable.subscribeOn(Schedulers.io()).subscribe(new Action1<OnePicture>() {
                            @Override
                            public void call(OnePicture onePicture) {
//                                Log.e("tes1",onePicture.getData().getHp_content());
                                mView.showPicture(onePicture.getData());
                            }
                        });
                    }
                });
    }

    @Override
    public Picture getPictureById(String id) {
        Request.getApi().getPictureById(id).subscribeOn(Schedulers.io()).subscribe(new Action1<OnePicture>() {
            @Override
            public void call(OnePicture onePicture) {
                //按道理应该先存数据库
                mView.showPicture(onePicture.getData());
            }
        });
        return null;
    }

    @Override
    public void checkAndGetPictureIds() {
        if (mIds == null && mIds.size() == 0) {
            return;
        }
        int pageCount = mIds.size();
        if (mCurrentPage >= pageCount - 4) {
            Request.getApi().getPictureIds(mIds.get(pageCount - 1)).subscribeOn(Schedulers.io()).subscribe(new Action1<OnePictureList>() {
                @Override
                public void call(OnePictureList onePictureList) {
                    if (onePictureList != null && onePictureList.getData() != null) {
                        mIds.addAll(onePictureList.getData());
                    }
                }
            });
        }
    }
}
