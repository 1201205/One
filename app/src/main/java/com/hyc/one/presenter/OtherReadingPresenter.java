package com.hyc.one.presenter;

import com.hyc.one.base.BasePresenter;
import com.hyc.one.base.DefaultTransformer;
import com.hyc.one.base.ExceptionAction;
import com.hyc.one.beans.BaseBean;
import com.hyc.one.beans.Essay;
import com.hyc.one.beans.Question;
import com.hyc.one.beans.Serial;
import com.hyc.one.net.Requests;
import com.hyc.one.utils.S;
import com.hyc.one.view.OtherReadingView;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by Administrator on 2016/7/13.
 */
public class OtherReadingPresenter extends BasePresenter<OtherReadingView> {
    private String mID;
    private String mEssayIndex = "0";
    private String mQuestionIndex = "0";
    private String mSerialIndex = "0";
    private int mReturnCount;
    private boolean mHasDismissed;

    public OtherReadingPresenter(OtherReadingView view) {
        super(view);
    }

    public void getReadings(String id) {
        mID = id;
        mView.showLoading();
        getOtherEssay();
        getOtherQuestion();
        getOtherSerial();
//        Observable.zip(Requests.getApi().getOtherEssayByID(mID, "0").compose(new DefaultTransformer<BaseBean<List<Essay>>, List<Essay>>()), Requests.getApi().getOtherSerialByID(mID, "0").compose(new DefaultTransformer<BaseBean<List<Serial>>, List<Serial>>()), Requests.getApi().getOtherQuestionByID(mID, "0").compose(new DefaultTransformer<BaseBean<List<QUESTION>>, List<QUESTION>>()), new Func3<List<Essay>, List<Serial>, List<QUESTION>, Void>() {
//            @Override
//            public Void call(List<Essay> listBaseBean, List<Serial> listBaseBean2, List<QUESTION> listBaseBean3) {
//                return null;
//            }
//        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Void>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Void aVoid) {
//
//            }
//        });
    }

    public void refresh(int type) {
        switch (type) {
            case S.ESSAY:
                getOtherEssay();
                break;
            case S.QUESTION:
                getOtherQuestion();
                break;
            case S.SERIAL:
                getOtherSerial();
                break;
            default:
                break;
        }
    }

    private void getOtherEssay() {
        Requests.getApi().getOtherEssayByID(mID, mEssayIndex).compose(new DefaultTransformer<BaseBean<List<Essay>>, List<Essay>>()).subscribe(new Action1<List<Essay>>() {
            @Override
            public void call(List<Essay> essays) {
                mView.showEssays(essays);
                mEssayIndex = essays.get(essays.size() - 1).getContent_id();
                checkDismissLoading();
            }
        }, new ExceptionAction() {
            @Override
            public void onNothingGet() {
                mView.noMore(S.ESSAY);
                checkDismissLoading();
            }
        });
    }

    private void getOtherQuestion() {
        Requests.getApi().getOtherQuestionByID(mID, mQuestionIndex).compose(new DefaultTransformer<BaseBean<List<Question>>, List<Question>>()).subscribe(new Action1<List<Question>>() {
            @Override
            public void call(List<Question> questions) {
                mView.showQuestions(questions);
                mQuestionIndex = questions.get(questions.size() - 1).getQuestion_id();
                checkDismissLoading();
            }
        }, new ExceptionAction() {
            @Override
            public void onNothingGet() {
                mView.noMore(S.QUESTION);
                checkDismissLoading();
            }
        });
    }

    private void getOtherSerial() {
        Requests.getApi().getOtherSerialByID(mID, mSerialIndex).compose(new DefaultTransformer<BaseBean<List<Serial>>, List<Serial>>()).subscribe(new Action1<List<Serial>>() {
            @Override
            public void call(List<Serial> serials) {
                mView.showSerials(serials);
                mSerialIndex = serials.get(serials.size() - 1).getId();
                checkDismissLoading();
            }
        }, new ExceptionAction() {
            @Override
            public void onNothingGet() {
                mView.noMore(S.SERIAL);
                checkDismissLoading();
            }
        });
    }

    private void checkDismissLoading() {
        if (!mHasDismissed) {
            mReturnCount++;
            if (mReturnCount >= 3) {
                mView.dismissLoading();
                mHasDismissed = true;
            }
        }

    }
}
