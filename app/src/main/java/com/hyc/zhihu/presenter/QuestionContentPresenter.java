package com.hyc.zhihu.presenter;

import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.beans.Comment;
import com.hyc.zhihu.beans.Comments;
import com.hyc.zhihu.beans.Question;
import com.hyc.zhihu.beans.QuestionContent;
import com.hyc.zhihu.beans.QuestionWrapper;
import com.hyc.zhihu.beans.Questions;
import com.hyc.zhihu.net.Requests;
import com.hyc.zhihu.presenter.base.IReadingContentPresenter;
import com.hyc.zhihu.view.QuestionContentView;
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
public class QuestionContentPresenter extends BasePresenter<ReadingContentView<QuestionContent,Question>> implements IReadingContentPresenter {
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
        mId=id;
        Observable.just(Requests.getApi().getQuestionContentByID(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<QuestionWrapper>() {
            @Override
            public void call(QuestionWrapper questionWrapper) {
                mView.showContent(questionWrapper.getData());
                mView.dismissLoading();
            }
        }),Requests.getApi().getQuestionRelateByID(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Questions>() {
            @Override
            public void call(Questions questions) {
                mView.showRelate(questions.getData());
            }
        }), Requests.getApi().getQuestionCommentsByIndex(id,"0").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map(new Func1<Comments, List<Comment>[]>() {
            @Override
            public List<Comment>[] call(Comments comments) {
                List<Comment> hot=new ArrayList<Comment>();
                List<Comment> normal=new ArrayList<Comment>();
                List<Comment> all=comments.getData().getData();
                int count = all.size();
                for (int i=0;i<count;i++) {
                    Comment c=all.get(i);
                    if (all.get(i).getType() == 0) {
                        hot.add(c);
                    } else {
                        normal.add(c);
                    }
                }
                List<Comment>[] types=new List[2];
                types[0]=hot;
                types[1]=normal;
                if (normal.size()>0) {
                   mLastIndex= normal.get(normal.size()-1).getId();
                }
                return types;
            }
        }).subscribe(new Action1<List<Comment>[]>() {
            @Override
            public void call(List<Comment>[] comments) {
                mView.showHotComments(comments[0]);
                mView.refreshCommentList(comments[1]);
            }
        })).subscribeOn(Schedulers.io()).subscribe();
    }

    @Override
    public void getAndShowCommentList() {
        Requests.getApi().getQuestionCommentsByIndex(mId,mLastIndex).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Comments>() {
            @Override
            public void call(Comments comments) {
                List<Comment> c=comments.getData().getData();
                if (c!=null&&c.size()>0) {
                    mLastIndex= c.get(c.size()-1).getId();
                }
                mView.refreshCommentList(comments.getData().getData());
            }
        });
    }

    @Override
    public void getAndShowRelate(String id) {

    }
}
