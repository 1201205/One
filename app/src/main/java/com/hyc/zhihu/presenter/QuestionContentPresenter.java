package com.hyc.zhihu.presenter;

import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.beans.QuestionContent;
import com.hyc.zhihu.net.Requests;
import com.hyc.zhihu.presenter.base.IQuestionContentPresenter;
import com.hyc.zhihu.view.QuestionContentView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/5/18.
 */
public class QuestionContentPresenter extends BasePresenter<QuestionContentView> implements IQuestionContentPresenter {

    public QuestionContentPresenter(QuestionContentView view) {
        super(view);
    }

    @Override
    public void getAndShowContent(String id) {
        Requests.getApi().getQuestionContenByID(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<QuestionContent>() {
            @Override
            public void call(QuestionContent content) {
                mView.showContent(content);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
            }
        });
    }

    @Override
    public void getAndShowCommentList(String type, String id) {

    }

    @Override
    public void getAndShowRelate(String id) {

    }
}
