package com.hyc.zhihu.presenter;

import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.base.DefaultTransformer;
import com.hyc.zhihu.base.ExceptionAction;
import com.hyc.zhihu.beans.BaseBean;
import com.hyc.zhihu.beans.Comment;
import com.hyc.zhihu.beans.CommentWrapper;
import com.hyc.zhihu.beans.Question;
import com.hyc.zhihu.beans.QuestionContent;
import com.hyc.zhihu.net.Requests;
import com.hyc.zhihu.presenter.base.IReadingContentPresenter;
import com.hyc.zhihu.view.ReadingContentView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/5/18.
 */
public class QuestionContentPresenter extends BasePresenter<ReadingContentView<QuestionContent, Question>> implements IReadingContentPresenter {
    private String mId;

    public QuestionContentPresenter(ReadingContentView view) {
        super(view);
    }

    private String mLastIndex;

    @Override
    public void getAndShowContent(String id) {
//        Requests.getApi().getQuestionContentByID(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<QuestionWrapper>() {
//            @Override
//            public void call(QuestionWrapper content) {
//                mView.showContent(content.getData());
//            }
//        }, new Action1<Throwable>() {
//            @Override
//            public void call(Throwable throwable) {
//            }
//        });
        mView.showLoading();
        mId = id;
        mCompositeSubscription.add(

                Observable.just(Requests.getApi().getQuestionContentByID(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<BaseBean<QuestionContent>>() {
                    @Override
                    public void call(BaseBean<QuestionContent> questionWrapper) {
                        mView.showContent(questionWrapper.getData());
                        mView.dismissLoading();
                    }
                }), Requests.getApi().getQuestionRelateByID(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<BaseBean<List<Question>>>() {
                    @Override
                    public void call(BaseBean<List<Question>> questions) {
                        mView.showRelate(questions.getData());
                    }
                }), Requests.getApi().getQuestionCommentsByIndex(id, "0").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map(new Func1<BaseBean<CommentWrapper>, List<Comment>[]>() {
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
        mCompositeSubscription.add(

                Requests.getApi().getQuestionCommentsByIndex(mId, mLastIndex).compose(new DefaultTransformer<BaseBean<CommentWrapper>, CommentWrapper>()).subscribe(new Action1<CommentWrapper>() {
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
