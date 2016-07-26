package com.hyc.one.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hyc.one.R;
import com.hyc.one.base.BaseActivity;
import com.hyc.one.base.BasePresenter;
import com.hyc.one.base.PresenterFactory;
import com.hyc.one.base.PresenterLoader;
import com.hyc.one.beans.DateBean;
import com.hyc.one.beans.OnePictureData;
import com.hyc.one.presenter.MonthPicturePresenter;
import com.hyc.one.ui.adpter.MonthPictureAdapter;
import com.hyc.one.utils.AppUtil;
import com.hyc.one.utils.S;
import com.hyc.one.view.MonthPictureView;

import java.util.List;

/**
 * Created by hyc on 2016/5/13.
 */
public class MonthPictureActivity extends BaseActivity<MonthPicturePresenter>
        implements MonthPictureView, LoaderManager.LoaderCallbacks<MonthPicturePresenter> {
    private DateBean mDate;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView = (RecyclerView) findViewById(R.id.picture_rv);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(manager);
    }


    @Override
    protected void handleIntent() {
        mDate = getIntent().getParcelableExtra(S.DATE);
    }


    @Override
    protected void initView() {

    }


    @Override
    protected int getLayoutID() {
        return R.layout.activity_month;
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new PresenterLoader(this, new PresenterFactory() {
            @Override
            public BasePresenter create() {
                return new MonthPicturePresenter(MonthPictureActivity.this);
            }
        });
    }


    @Override
    public void onLoadFinished(Loader loader, MonthPicturePresenter data) {
        mPresenter = data;
        mPresenter.attachView();
        mPresenter.getAndShowPictures(mDate.realDate);
    }


    @Override
    public void onLoaderReset(Loader loader) {
        mPresenter = null;
    }


    @Override
    public void showPictures(List<OnePictureData> datas) {
        MonthPictureAdapter monthPictureAdapter = new MonthPictureAdapter(datas, this);
        mRecyclerView.setAdapter(monthPictureAdapter);
    }

    @Override
    protected void initLoader() {
        getSupportLoaderManager().initLoader(AppUtil.getID(), null, this);
    }


    @Override
    protected String getTitleString() {
        return mDate.date;
    }
}
