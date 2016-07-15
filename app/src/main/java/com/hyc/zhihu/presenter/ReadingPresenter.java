package com.hyc.zhihu.presenter;

import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.base.DefaultTransformer;
import com.hyc.zhihu.base.ExceptionAction;
import com.hyc.zhihu.beans.BaseBean;
import com.hyc.zhihu.beans.DateReading;
import com.hyc.zhihu.beans.HeadScrollItem;
import com.hyc.zhihu.beans.Question;
import com.hyc.zhihu.beans.Reading;
import com.hyc.zhihu.beans.ReadingContent;
import com.hyc.zhihu.beans.RealArticle;
import com.hyc.zhihu.beans.RealReading;
import com.hyc.zhihu.beans.SerialContent;
import com.hyc.zhihu.net.Requests;
import com.hyc.zhihu.presenter.base.IReadingPresenter;
import com.hyc.zhihu.utils.JsonUtil;
import com.hyc.zhihu.utils.RealmUtil;
import com.hyc.zhihu.view.ReadingView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/5/16.
 */
public class ReadingPresenter extends BasePresenter<ReadingView> implements IReadingPresenter {
    private List<String> mTitle;
    private LinkedHashMap<Integer, String> mIndexer;
    private int mLastCount;

    public ReadingPresenter(ReadingView view) {
        super(view);

    }

    @Override
    public void showContent() {
        mView.showLoading();
        getAndShowHead();
        getAndShowList(0);
    }

    @Override
    public void getAndShowHead() {
        mCompositeSubscription.add(

                Requests.getApi().getScrollHeads().compose(new DefaultTransformer<BaseBean<List<HeadScrollItem>>, List<HeadScrollItem>>()).subscribe(new Action1<List<HeadScrollItem>>() {
                    @Override
                    public void call(List<HeadScrollItem> headItems) {
                        mView.showHead(headItems);
                        mView.dismissLoading();
                    }
                }));
    }

    @Override
    public void getAndShowList(int index) {
        mCompositeSubscription.add(

                Requests.getApi().getReadingList(index).compose(new DefaultTransformer<BaseBean<List<DateReading>>, List<DateReading>>()).map(new Func1<List<DateReading>, List<RealReading>>() {
                    @Override
                    public List<RealReading> call(List<DateReading> readings) {
                        List<RealReading> realReadings = new ArrayList<RealReading>();
                        if (mIndexer == null) {
                            mIndexer = new LinkedHashMap<Integer, String>();
                        }
                        int dateCount = readings.size();
                        int count = 0;
                        for (int i = 0; i < dateCount; i++) {
                            List<Reading> readingList = readings.get(i).getItems();
                            mIndexer.put(count + mLastCount, readings.get(i).getDate());
//                    mTitle.add(readingList.get(0).getTime());
                            int readingCount = readingList.size();
                            for (int j = 0; j < readingCount; j++) {
                                RealReading r = getRealReading(readingList.get(j));
                                realReadings.add(r);
//                        if (j==0) {
//                            mTitle.add(r.getContent().getTitle());
//                        }
                                count++;
                            }
                        }
                        RealmUtil.save(realReadings);
                        mLastCount += realReadings.size();
                        return realReadings;
                    }
                }).subscribe(new Action1<List<RealReading>>() {
                    @Override
                    public void call(List<RealReading> realReadings) {
                        mView.showList(realReadings, mIndexer);
                    }
                }, new ExceptionAction() {
                    @Override
                    public void onNothingGet() {
                        showCachedInfo();
                    }
                }));
    }

    private void showCachedInfo() {
        Observable.just(RealmUtil.getListByCount(RealReading.class,9)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<List<RealReading>>() {
            @Override
            public void call(List<RealReading> realmModels) {
                mView.showList(realmModels, mIndexer);
            }
        });
    }

    private RealReading getRealReading(Reading r) {
        ReadingContent content = new ReadingContent();
        switch (r.getType()) {
            case 1:
                RealArticle a = JsonUtil.fromJson(r.getContent(), RealArticle.class);
                content.setAuthor(a.getAuthor().get(0).getUser_name());
                content.setContent(a.getGuide_word());
                content.setHasAudio(a.getHas_audio());
                content.setId(a.getContent_id());
                content.setTitle(a.getHp_title());
                break;
            case 2:
                SerialContent s = JsonUtil.fromJson(r.getContent(), SerialContent.class);
                content.setTitle(s.getTitle());
                content.setAuthor(s.getAuthor().getUser_name());
                content.setContent(s.getExcerpt());
                content.setId(s.getId());
                break;
            case 3:
                Question q = JsonUtil.fromJson(r.getContent(), Question.class);
                content.setTitle(q.getQuestion_title());
                content.setAuthor(q.getAnswer_title());
                content.setContent(q.getAnswer_content());
                content.setId(q.getQuestion_id());
        }
        return new RealReading(r.getTime(), content, r.getType(),content.getId());
    }
}
