package com.hyc.one.ui;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.hyc.one.R;
import com.hyc.one.base.BaseActivity;
import com.hyc.one.base.BasePresenter;
import com.hyc.one.base.PresenterFactory;
import com.hyc.one.base.PresenterLoader;
import com.hyc.one.beans.Essay;
import com.hyc.one.beans.Question;
import com.hyc.one.beans.Serial;
import com.hyc.one.presenter.OtherReadingPresenter;
import com.hyc.one.ui.adpter.ListFragmentAdapter;
import com.hyc.one.ui.fragment.ListFragment;
import com.hyc.one.utils.AppUtil;
import com.hyc.one.utils.S;
import com.hyc.one.view.OtherReadingView;

import java.util.List;

/**
 * Created by Administrator on 2016/7/13.
 */
public class OtherReadingActivity extends BaseActivity<OtherReadingPresenter> implements OtherReadingView, LoaderManager.LoaderCallbacks<OtherReadingPresenter> {
    private String mID;
    private ListFragment mEssay;
    private ListFragment mQuestion;
    private ListFragment mSerial;
    private ListFragmentAdapter mAdapter;

    @Override
    protected void initLoader() {
        getSupportLoaderManager().initLoader(AppUtil.getID(), null, this);
    }

    @Override
    protected void handleIntent() {
        mID = getIntent().getStringExtra(S.ID);
    }

    @Override
    protected void initView() {
        mEssay = ListFragment.getInstance(S.ESSAY);
        mQuestion = ListFragment.getInstance(S.QUESTION);
        mSerial = ListFragment.getInstance(S.SERIAL);
        ListFragment.LoadMoreListener loadMoreListener = new ListFragment.LoadMoreListener() {
            @Override
            public void loadMore(int type) {
                mPresenter.refresh(type);
            }
        };
        mEssay.setListener(loadMoreListener);
        mQuestion.setListener(loadMoreListener);
        mSerial.setListener(loadMoreListener);
        mAdapter = new ListFragmentAdapter(getSupportFragmentManager());
        mAdapter.add(mEssay);
        mAdapter.add(mSerial);
        mAdapter.add(mQuestion);
        final ViewPager pager = (ViewPager) findViewById(R.id.reading_vpg);
        pager.setAdapter(mAdapter);
        pager.setOffscreenPageLimit(3);
        findViewById(R.id.essay_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(0);
            }
        });
        findViewById(R.id.serial_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(1);
            }
        });
        findViewById(R.id.question_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(2);
            }
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_other_reading;
    }

    @Override
    public Loader<OtherReadingPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<OtherReadingPresenter>(this, new PresenterFactory() {
            @Override
            public BasePresenter create() {
                return new OtherReadingPresenter(OtherReadingActivity.this);
            }
        });
    }

    @Override
    public void onLoadFinished(Loader<OtherReadingPresenter> loader, OtherReadingPresenter data) {
        mPresenter = data;
        mPresenter.attachView();
        mPresenter.getReadings(mID);
    }

    @Override
    public void onLoaderReset(Loader<OtherReadingPresenter> loader) {
        mPresenter = null;
    }

    @Override
    public void showEssays(List<Essay> essays) {
        mEssay.showList(essays);
    }

    @Override
    public void showQuestions(List<Question> questions) {
        mQuestion.showList(questions);
    }

    @Override
    public void showSerials(List<Serial> serials) {
        mSerial.showList(serials);
    }

    @Override
    public void noMore(int type) {
        switch (type) {
            case S.ESSAY:
                mEssay.noMore();
                break;
            case S.QUESTION:
                mQuestion.noMore();
                break;
            case S.SERIAL:
                mSerial.noMore();
                break;
            default:
                break;
        }
    }


    @Override
    protected String getTitleString() {
        return AppUtil.getString(R.string.other_essay);
    }
}
