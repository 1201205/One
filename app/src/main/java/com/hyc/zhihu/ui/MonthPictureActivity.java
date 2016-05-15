package com.hyc.zhihu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hyc.zhihu.R;
import com.hyc.zhihu.base.BaseActivity;
import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.base.PresenterFactory;
import com.hyc.zhihu.base.PresenterLoader;
import com.hyc.zhihu.beans.OnePictureData;
import com.hyc.zhihu.presenter.MonthPicturePresenter;
import com.hyc.zhihu.ui.adpter.MonthPictureAdapter;
import com.hyc.zhihu.view.MonthPictureView;

import java.util.List;

/**
 * Created by hyc on 2016/5/13.
 */
public class MonthPictureActivity extends BaseActivity implements MonthPictureView, LoaderManager.LoaderCallbacks<MonthPicturePresenter>{
    private MonthPicturePresenter mPresenter;
    private String mDate;
    private RecyclerView mRecyclerView;
    public static final String DATE="date";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportLoaderManager().initLoader(10000,null,this);
        mRecyclerView= (RecyclerView) findViewById(R.id.picture_rv);
        GridLayoutManager manager=new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(manager);
    }

    @Override
    protected void handleIntent() {
        mDate=getIntent().getStringExtra(DATE);
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
        mPresenter=data;
        mPresenter.getAndShowPictures(mDate);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    @Override
    public void showPictures(List<OnePictureData> datas) {
        MonthPictureAdapter monthPictureAdapter=new MonthPictureAdapter(datas,this);
        mRecyclerView.setAdapter(monthPictureAdapter);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }
}
