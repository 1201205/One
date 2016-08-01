package com.hyc.one.presenter;

import android.util.Log;

import com.hyc.one.R;
import com.hyc.one.base.BasePresenter;
import com.hyc.one.base.DefaultTransformer;
import com.hyc.one.base.ExceptionAction;
import com.hyc.one.beans.BaseBean;
import com.hyc.one.beans.Comment;
import com.hyc.one.beans.CommentWrapper;
import com.hyc.one.beans.Serial;
import com.hyc.one.beans.SerialContent;
import com.hyc.one.net.Requests;
import com.hyc.one.presenter.base.IReadingContentPresenter;
import com.hyc.one.utils.AppUtil;
import com.hyc.one.utils.RealmUtil;
import com.hyc.one.view.ReadingContentView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/5/19.
 */
public class SerialContentPresenter extends BasePresenter<ReadingContentView<SerialContent, Serial>> implements IReadingContentPresenter {
    private String mId;
    private String mLastIndex;

    public SerialContentPresenter(ReadingContentView<SerialContent, Serial> view) {
        super(view);
    }

    @Override
    public void getAndShowContent(final String id) {
        mView.showLoading();
        mId = id;
        mCompositeSubscription.add(
                Observable.just(Requests.getApi().getSerialContentByID(id).compose(new DefaultTransformer<BaseBean<SerialContent>, SerialContent>()).subscribe(new Action1<SerialContent>() {
                    @Override
                    public void call(SerialContent serial) {
                        RealmUtil.saveOrUpdate(serial);
                        mView.showContent(serial);
                        mView.dismissLoading();
                    }
                }, new ExceptionAction() {
                    @Override
                    protected void dismissLoading() {
                        mView.dismissLoading();
                    }

                    @Override
                    protected void onNoNetWork() {
                        getCachedData(id);
                    }
                }), Requests.getApi().getSerialRelateByID(id).compose(new DefaultTransformer<BaseBean<List<Serial>>, List<Serial>>()).subscribe(new Action1<List<Serial>>() {
                    @Override
                    public void call(List<Serial> serials) {
                        mView.showRelate(serials);
                    }
                }, new ExceptionAction()), Requests.getApi().getSerialCommentsByIndex(id, "0").compose(new DefaultTransformer<BaseBean<CommentWrapper>, CommentWrapper>()).map(new Func1<CommentWrapper, List<Comment>[]>() {
                    @Override
                    public List<Comment>[] call(CommentWrapper comments) {
                        List<Comment> hot = new ArrayList<Comment>();
                        List<Comment> normal = new ArrayList<Comment>();
                        List<Comment> all = comments.getData();
                        int count = all.size();
                        for (int i = 0; i < count; i++) {
                            Comment c = all.get(i);
                            if (all.get(i).getType() == 0) {
                                hot.add(c);
                            } else {
                                normal.add(c);
                            }
                        }
                        List<Comment>[] types = new List[2];
                        types[0] = hot;
                        types[1] = normal;
                        if (normal.size() > 0) {
                            mLastIndex = normal.get(normal.size() - 1).getId();
                        }
                        return types;
                    }
                }).subscribe(new Action1<List<Comment>[]>() {
                    @Override
                    public void call(List<Comment>[] comments) {
                        mView.showHotComments(comments[0]);
                        mView.refreshCommentList(comments[1]);
                    }
                }, new ExceptionAction())).subscribeOn(Schedulers.io()).subscribe());
    }

    private void getCachedData(String id) {
        Observable.just(RealmUtil.findByKeyOne(SerialContent.class, "id", id)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<SerialContent>() {
            @Override
            public void call(SerialContent serialContent) {
                mView.dismissLoading();
                if (serialContent != null) {
                    mView.showContent(serialContent);
                } else {
                    AppUtil.showToast(R.string.no_cache);
                }
            }
        }, new ExceptionAction() {
            @Override
            public void onNothingGet() {
            }

            @Override
            protected void dismissLoading() {
                mView.dismissLoading();
            }
        });
    }

    @Override
    public void getAndShowCommentList() {
        mCompositeSubscription.add(
                Requests.getApi().getSerialCommentsByIndex(mId, mLastIndex).compose(new DefaultTransformer<BaseBean<CommentWrapper>, CommentWrapper>()).subscribe(new Action1<CommentWrapper>() {
                    @Override
                    public void call(CommentWrapper comments) {
                        List<Comment> c = comments.getData();
                        if (c == null || c.size() == 0) {
                            mView.showNoComments();
                            return;
                        }
                        mLastIndex = c.get(c.size() - 1).getId();
                        mView.refreshCommentList(c);
                    }
                }, new ExceptionAction() {
                    @Override
                    public void onNothingGet() {
                        mView.showNoComments();
                    }
                }));
    }

    @Override
    public void getAndShowRelate(String id) {

    }
}
