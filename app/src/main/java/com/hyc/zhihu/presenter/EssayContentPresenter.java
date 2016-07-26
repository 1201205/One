package com.hyc.zhihu.presenter;

import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.base.DefaultTransformer;
import com.hyc.zhihu.base.ExceptionAction;
import com.hyc.zhihu.beans.BaseBean;
import com.hyc.zhihu.beans.Comment;
import com.hyc.zhihu.beans.CommentWrapper;
import com.hyc.zhihu.beans.Essay;
import com.hyc.zhihu.beans.RealArticle;
import com.hyc.zhihu.net.Requests;
import com.hyc.zhihu.presenter.base.IReadingContentPresenter;
import com.hyc.zhihu.view.ReadingContentView;

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
public class EssayContentPresenter extends BasePresenter<ReadingContentView<Essay, RealArticle>> implements IReadingContentPresenter {
    private String mId;
    private String mLastIndex;

    public EssayContentPresenter(ReadingContentView<Essay, RealArticle> view) {
        super(view);
    }

    @Override
    public void getAndShowContent(String id) {
        mView.showLoading();
        mId = id;
        mCompositeSubscription.add(
                Observable.just(Requests.getApi().getEssayContentByID(id).compose(new DefaultTransformer<BaseBean<Essay>, Essay>()).subscribe(new Action1<Essay>() {
                    @Override
                    public void call(Essay essay) {
                        mView.showContent(essay);
                        mView.dismissLoading();
                    }
                }, new ExceptionAction() {
                    @Override
                    public void onNothingGet() {
                    }
                }), Requests.getApi().getEssayRelateByID(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<BaseBean<List<RealArticle>>>() {
                    @Override
                    public void call(BaseBean<List<RealArticle>> realArticles) {
                        mView.showRelate(realArticles.getData());
                    }
                }), Requests.getApi().getEssayCommentsByIndex(id, "0").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map(new Func1<BaseBean<CommentWrapper>, List<Comment>[]>() {
                    @Override
                    public List<Comment>[] call(BaseBean<CommentWrapper> comments) {
                        List<Comment> hot = new ArrayList<Comment>();
                        List<Comment> normal = new ArrayList<Comment>();
                        List<Comment> all = comments.getData().getData();
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
                })).subscribeOn(Schedulers.io()).subscribe());
    }

    @Override
    public void getAndShowCommentList() {

        mCompositeSubscription.add(Requests.getApi().getEssayCommentsByIndex(mId, mLastIndex).compose(new DefaultTransformer<BaseBean<CommentWrapper>, CommentWrapper>()).subscribe(new Action1<CommentWrapper>() {
            @Override
            public void call(CommentWrapper comments) {
                List<Comment> c = comments.getData();
                if (c == null || c.size() == 0) {
                    mView.showNoComments();
                    return;
                }
                mLastIndex = c.get(c.size() - 1).getId();
                mView.refreshCommentList(comments.getData());
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
