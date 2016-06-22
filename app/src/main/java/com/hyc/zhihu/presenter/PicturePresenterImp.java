package com.hyc.zhihu.presenter;

import android.util.Log;

import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.beans.IDList;
import com.hyc.zhihu.beans.OnePicture;
import com.hyc.zhihu.beans.OnePictureData;
import com.hyc.zhihu.beans.PictureViewBean;
import com.hyc.zhihu.net.Requests;
import com.hyc.zhihu.presenter.base.IPicturePresenter;
import com.hyc.zhihu.utils.RealmUtil;
import com.hyc.zhihu.view.PictureView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ray on 16/5/5.
 */
public class PicturePresenterImp extends BasePresenter<PictureView> implements IPicturePresenter {
    private List<String> mIds;
    private int mCurrentPage;
    ArrayList<PictureViewBean> viewBeans;
    private Action1 mThrowableAction = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            mView.showNetWorkError();
        }
    };

    //目前与view一一对应
    public PicturePresenterImp(PictureView view) {
        super(view);
    }

    @Override
    public void getPictureIdsAndFirstItem() {
        mView.showLoading();
        Requests.getApi().getPictureIds("0").map(new Func1<IDList, String>() {
            @Override
            public String call(IDList IDList) {
                Log.e("test12", Thread.currentThread().getName());
                mIds = IDList.getData();
                if (mIds == null || mIds.size() == 0) {
                    return null;
                }
                RealmResults<OnePictureData> d = RealmUtil.findByKey(OnePictureData.class, "hpcontent_id", mIds.get(0));
                if (d.size() > 0) {
                    Log.e("test1", "获取到保存的数据");
                }
                return mIds.get(0);
            }
        }).map(new Func1<String, Observable<OnePictureData>>() {
            @Override
            public Observable<OnePictureData> call(final String s) {
                Observable<OnePictureData> orm = Observable.just(getOnePictureDataByRealm(s)).subscribeOn(Schedulers.io());
                Observable<OnePictureData> net = Requests.getApi().getPictureById(s).map(new Func1<OnePicture, OnePictureData>() {
                    @Override
                    public OnePictureData call(OnePicture onePicture) {
                        RealmUtil.save(onePicture.getData());
                        return onePicture.getData();
                    }
                }).subscribeOn(Schedulers.io());
                return Observable.concat(orm, net).takeFirst(new Func1<OnePictureData, Boolean>() {
                    @Override
                    public Boolean call(OnePictureData onePictureData) {
                        return onePictureData != null;
                    }
                });
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Observable<OnePictureData>>() {
            @Override
            public void call(Observable<OnePictureData> onePictureDataObservable) {
                onePictureDataObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<OnePictureData>() {
                    @Override
                    public void call(OnePictureData onePictureData) {
                        mView.dismissLoading();
                        viewBeans = new ArrayList<PictureViewBean>();
                        for (int i = 0; i < mIds.size(); i++) {
                            PictureViewBean bean = new PictureViewBean(mIds.get(i), PictureViewBean.NORESULT, null);
                            if (i == mIds.size()) {
                                bean.state = PictureViewBean.LIST;
                            }
                            viewBeans.add(bean);
                        }
                        PictureViewBean bean = new PictureViewBean("list", PictureViewBean.LIST, null);
                        viewBeans.add(bean);
                        mView.setAdapter(viewBeans);
                        mView.showPicture(mIds.get(0), onePictureData);
                        mView.dismissLoading();
                    }
                },mThrowableAction);
            }
        }, mThrowableAction);
    }
    private OnePictureData getOnePictureDataByRealm(String s){
       OnePictureData data= RealmUtil.findByKeyOne(OnePictureData.class, "hpcontent_id", s);
        if (data!=null&&data.isValid()) {
            Log.e("哈哈哈","从数据库查找成功");
            data=Realm.getDefaultInstance().copyFromRealm(data);
        }
        return data;
    }
    @Override
    public OnePictureData getPictureById(final String id) {
        Log.e("test1", "获取信息--" + id);
        mView.showLoading();
        Observable<OnePictureData> orm=Observable.just(getOnePictureDataByRealm(id)).subscribeOn(Schedulers.io());
        Observable<OnePictureData> net= Requests.getApi().getPictureById(id).subscribeOn(Schedulers.io()).map(new Func1<OnePicture, OnePictureData>() {
            @Override
            public OnePictureData call(OnePicture onePicture) {
                RealmUtil.save(onePicture.getData());
                return onePicture.getData();
            }
        });
        Observable.concat(orm,net).takeFirst(new Func1<OnePictureData, Boolean>() {
            @Override
            public Boolean call(OnePictureData onePictureData) {
                return onePictureData!=null;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<OnePictureData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(OnePictureData onePictureData) {
                mView.showPicture(id,onePictureData);
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
            Requests.getApi().getPictureIds(mIds.get(pageCount - 1)).subscribeOn(Schedulers.io()).subscribe(new Action1<IDList>() {
                @Override
                public void call(IDList IDList) {
                    if (IDList != null && IDList.getData() != null) {
                        mIds.addAll(IDList.getData());
                    }
                }
            }, mThrowableAction);
        }
    }

    @Override
    public void gotoPosition(int position) {
        if (position == mIds.size()) {
            return;
        }
        if (viewBeans.get(position).state != PictureViewBean.NORMAL) {
            getPictureById(mIds.get(position));
        }
    }
}
