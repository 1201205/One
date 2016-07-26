package com.hyc.one.presenter;

import android.text.TextUtils;
import android.util.Log;
import com.hyc.one.R;
import com.hyc.one.base.BasePresenter;
import com.hyc.one.base.DefaultTransformer;
import com.hyc.one.base.ExceptionAction;
import com.hyc.one.beans.BaseBean;
import com.hyc.one.beans.OnePictureData;
import com.hyc.one.beans.PictureViewBean;
import com.hyc.one.net.Requests;
import com.hyc.one.presenter.base.IPicturePresenter;
import com.hyc.one.utils.AppUtil;
import com.hyc.one.utils.RealmUtil;
import com.hyc.one.utils.S;
import com.hyc.one.utils.SPUtil;
import com.hyc.one.view.PictureView;
import io.realm.Realm;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    ArrayList<PictureViewBean> viewBeans;
    private List<String> mIds;
    private int mCurrentPage;
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
        mCompositeSubscription.add(

                Requests.getApi().getPictureIds("0").compose(new DefaultTransformer<BaseBean<List<String>>, List<String>>()).map(new Func1<List<String>, String>() {
                    @Override
                    public String call(List<String> ids) {
                        Log.e("test12", Thread.currentThread().getName());
                        mIds = ids;
//                        RealmResults<OnePictureData> d = RealmUtil.findByKey(OnePictureData.class, "hpcontent_id", mIds.get(0));
//                        if (d.size() > 0) {
//                            Log.e("test1", "获取到保存的数据");
//                        }
                        StringBuilder builder=new StringBuilder();
                        for (String s:mIds) {
                            builder.append(s).append("-");
                        }
                        builder.substring(0,builder.length()-1);
                        SPUtil.put(S.PICTURE_ID,builder.toString());
                        return mIds.get(0);
                    }
                }).map(new Func1<String, Observable<OnePictureData>>() {
                    @Override
                    public Observable<OnePictureData> call(final String s) {
                       return getOnePictureData(s);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Observable<OnePictureData>>() {
                    @Override
                    public void call(Observable<OnePictureData> onePictureDataObservable) {
                        showFirstPicture(onePictureDataObservable);
                    }
                }, new ExceptionAction() {
                    @Override
                    public void onNothingGet() {
                        AppUtil.showToast(R.string.do_not_get_id);
                    }
                    @Override
                    public void onNoNetWork(){
                        AppUtil.showToast(R.string.show_cache);
                        showCachedInfo();
                    }
                }));
    }

    private void showCachedInfo() {
        String s=SPUtil.get(S.PICTURE_ID,"");
        if (TextUtils.isEmpty(s)) {
            return;
        }
       String[] ids= s.split("-");
        mIds=Arrays.asList(ids);
        if (mIds.size()==0) {
            return;
        }
        Observable.just(Observable.just(getOnePictureDataByRealm(mIds.get(0)))).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
            new Action1<Observable<OnePictureData>>() {
                @Override
                public void call(Observable<OnePictureData> onePictureDataObservable) {
                    showFirstPicture(onePictureDataObservable);
                }
            }, new ExceptionAction() {
                @Override public void onNothingGet() {

                }
            });
    }

    private Observable<OnePictureData> getOnePictureData(String s){
        Observable<OnePictureData> orm = Observable.just(getOnePictureDataByRealm(s)).subscribeOn(Schedulers.io());
        Observable<OnePictureData> net = Requests.getApi().getPictureById(s).map(new Func1<BaseBean<OnePictureData>, OnePictureData>() {
            @Override
            public OnePictureData call(BaseBean<OnePictureData> onePicture) {
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
    private void showFirstPicture(Observable<OnePictureData> data){
        data.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<OnePictureData>() {
            @Override
            public void call(OnePictureData onePictureData) {
                if (onePictureData==null) {
                    return;
                }
                //mView.dismissLoading();
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
        }, mThrowableAction);
    }
    private OnePictureData getOnePictureDataByRealm(String s) {

        OnePictureData data = RealmUtil.findByKeyOne(OnePictureData.class, "hpcontent_id", s);
        if (data != null && data.isValid()) {
            data = Realm.getDefaultInstance().copyFromRealm(data);
        }
        return data;
    }

    @Override
    public OnePictureData getPictureById(final String id) {
//        mView.showLoading();
        Observable<OnePictureData> orm = Observable.just(getOnePictureDataByRealm(id)).subscribeOn(Schedulers.io());
        Observable<OnePictureData> net = Requests.getApi().getPictureById(id).subscribeOn(Schedulers.io()).map(new Func1<BaseBean<OnePictureData>, OnePictureData>() {
            @Override
            public OnePictureData call(BaseBean<OnePictureData> onePicture) {
                RealmUtil.save(onePicture.getData());
                return onePicture.getData();
            }
        });
        mCompositeSubscription.add(

                Observable.concat(orm, net).takeFirst(new Func1<OnePictureData, Boolean>() {
                    @Override
                    public Boolean call(OnePictureData onePictureData) {
                        return onePictureData != null;
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
                        mView.showPicture(id, onePictureData);
                    }
                }));
        return null;
    }

    @Override
    public void checkAndGetPictureIds() {
        if (mIds == null && mIds.size() == 0) {
            return;
        }
        int pageCount = mIds.size();
        if (mCurrentPage >= pageCount - 4) {
            mCompositeSubscription.add(
                    Requests.getApi().getPictureIds(mIds.get(pageCount - 1)).subscribeOn(Schedulers.io()).subscribe(new Action1<BaseBean<List<String>>>() {
                        @Override
                        public void call(BaseBean<List<String>> ids) {
                            if (ids != null && ids.getData() != null) {
                                mIds.addAll(ids.getData());
                            }
                        }
                    }, mThrowableAction));
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
