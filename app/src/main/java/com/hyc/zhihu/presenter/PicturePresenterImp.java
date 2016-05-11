package com.hyc.zhihu.presenter;

import android.util.Log;

import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.beans.OnePicture;
import com.hyc.zhihu.beans.OnePictureData;
import com.hyc.zhihu.beans.OnePictureList;
import com.hyc.zhihu.beans.PictureViewBean;
import com.hyc.zhihu.net.Request;
import com.hyc.zhihu.presenter.base.PicturePresenter;
import com.hyc.zhihu.utils.RealmUtil;
import com.hyc.zhihu.view.PictureView;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;
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
        Request.getApi().getPictureIds("0").observeOn(Schedulers.io()).map(new Func1<OnePictureList, String>() {
            @Override
            public String call(OnePictureList onePictureList) {
                mIds = onePictureList.getData();
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
                Observable<OnePictureData> orm = Observable.just(RealmUtil.findByKeyOne(OnePictureData.class, "hpcontent_id", s));
                Observable<OnePictureData> net = Request.getApi().getPictureById(s).map(new Func1<OnePicture, OnePictureData>() {
                    @Override
                    public OnePictureData call(OnePicture onePicture) {
//                        RealmUtil.save(onePicture.getData());
                        return onePicture.getData();
                    }
                }).observeOn(Schedulers.io());
                return Observable.concat(orm,net ).takeFirst(new Func1<OnePictureData, Boolean>() {
                    @Override
                    public Boolean call(OnePictureData onePictureData) {
                        Log.e("test--", "data" + onePictureData);
                        return onePictureData != null;
                    }
                });
            }
        }).observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Observable<OnePictureData>>() {
            @Override
            public void call(Observable<OnePictureData> onePictureDataObservable) {
                onePictureDataObservable.observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<OnePictureData>() {
                    @Override
                    public void call(OnePictureData onePictureData) {
                        mView.dismissLoading();
//                                Log.e("tes1",onePicture.getData().getHp_content());
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
                    }
                });
            }
        });
//        Request.getApi().getPictureIds("0").map(new Func1<OnePictureList, String>() {
//            @Override
//            public String call(OnePictureList onePictureList) {
//                mIds = onePictureList.getData();
//                if (mIds == null || mIds.size() == 0) {
//                    return null;
//                }
//                RealmResults<OnePictureData> d=RealmUtil.findByKey(OnePictureData.class,"hpcontent_id",mIds.get(0));
//                if (d.size()>0) {
//                    Log.e("test1","获取到保存的数据");
//                }
//                return mIds.get(0);
//            }
//        }).map(new Func1<String, Object>() {
//        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
//                subscribe(new Action1<Observable<OnePicture>>() {
//                    @Override
//                    public void call(Observable<OnePicture> onePictureObservable) {
//
//                        onePictureObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<OnePicture>() {
//                            @Override
//                            public void call(OnePicture onePicture) {
//                                mView.dismissLoading();
////                                Log.e("tes1",onePicture.getData().getHp_content());
//                                viewBeans = new ArrayList<PictureViewBean>();
//                                for (int i = 0; i < mIds.size(); i++) {
//                                    PictureViewBean bean = new PictureViewBean(mIds.get(i), PictureViewBean.NORESULT, null);
//                                    if (i==mIds.size()) {
//                                        bean.state=PictureViewBean.LIST;
//                                    }
//                                    viewBeans.add(bean);
//                                }
//                                PictureViewBean bean = new PictureViewBean("list", PictureViewBean.LIST, null);
//                                viewBeans.add(bean);
//                                mView.setAdapter(viewBeans);
//                                for (int i=0;i<100000;i++) {
//                                    RealmUtil.save(onePicture.getData());
//                                }
//
//                                mView.showPicture(mIds.get(0), onePicture.getData());
//                            }
//                        });
//                    }
//                });
    }

    @Override
    public OnePictureData getPictureById(final String id) {
        Log.e("test1", "获取信息--" + id);
        mView.showLoading();
        Request.getApi().getPictureById(id).subscribeOn(Schedulers.io()).subscribe(new Action1<OnePicture>() {
            @Override
            public void call(OnePicture onePicture) {
                //按道理应该先存数据库
                Log.e("test1", "获取完毕");
                mView.showPicture(id, onePicture.getData());
                mView.dismissLoading();
            }
        }, mThrowableAction);
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
