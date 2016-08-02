package com.hyc.one.presenter;

import com.hyc.one.R;
import com.hyc.one.base.BasePresenter;
import com.hyc.one.base.DefaultTransformer;
import com.hyc.one.base.ExceptionAction;
import com.hyc.one.beans.BaseBean;
import com.hyc.one.beans.Comment;
import com.hyc.one.beans.CommentWrapper;
import com.hyc.one.beans.Essay;
import com.hyc.one.beans.Question;
import com.hyc.one.beans.QuestionContent;
import com.hyc.one.net.Requests;
import com.hyc.one.presenter.base.IReadingContentPresenter;
import com.hyc.one.utils.AppUtil;
import com.hyc.one.utils.RealmUtil;
import com.hyc.one.view.ReadingContentView;

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

                Observable.just(Requests.getApi().getQuestionContentByID(id).compose(new DefaultTransformer<BaseBean<QuestionContent>, QuestionContent>()).subscribe(new Action1<QuestionContent>() {
                    @Override
                    public void call(QuestionContent question) {
                        RealmUtil.saveOrUpdate(question);
                        mView.showContent(question);
                        mView.dismissLoading();
                    }
                },new ExceptionAction(){
                    @Override
                    protected void onNoNetWork() {
                        showCachedData();
                    }

                    @Override
                    protected void onNothingGet() {
                        dismissLoading();
                    }
                }), Requests.getApi().getQuestionRelateByID(id).compose(new DefaultTransformer<BaseBean<List<Question>>, List<Question>>()).subscribe(new Action1<List<Question>>() {
                    @Override
                    public void call(List<Question> questions) {
                        mView.showRelate(questions);
                    }
                },new ExceptionAction(false)), Requests.getApi().getQuestionCommentsByIndex(id, "0").compose(new DefaultTransformer<BaseBean<CommentWrapper>, CommentWrapper>()).map(new Func1<CommentWrapper, List<Comment>[]>() {
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
                },new ExceptionAction(false))).subscribeOn(Schedulers.io()).subscribe());
    }
    private void showCachedData() {
        Observable.just(RealmUtil.findByKeyOne(QuestionContent.class, "question_id", mId)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<QuestionContent>() {
            @Override
            public void call(QuestionContent questionContent) {
                mView.dismissLoading();
                if (questionContent != null) {
                    mView.showContent(questionContent);
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
