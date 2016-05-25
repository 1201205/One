package com.hyc.zhihu.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hyc.zhihu.R;
import com.hyc.zhihu.base.BaseActivity;
import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.base.PresenterFactory;
import com.hyc.zhihu.base.PresenterLoader;
import com.hyc.zhihu.beans.music.MusicMonthItem;
import com.hyc.zhihu.presenter.MusicMonthPresenter;
import com.hyc.zhihu.ui.adpter.MonthMusicAdapter;
import com.hyc.zhihu.view.MusicMonthView;

import java.util.List;

/**
 * Created by ray on 16/5/26.
 */
public class MusicMonthListActivity extends BaseActivity implements MusicMonthView,LoaderManager.LoaderCallbacks<MusicMonthPresenter> {
    private RecyclerView mRecyclerView;
    private MusicMonthPresenter mPresenter;
    private String mDate;
    public static final String DATE="date";
    @Override
    protected void handleIntent() {
        mDate=getIntent().getStringExtra(DATE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportLoaderManager().initLoader(129,null,this);
    }

    @Override
    protected void initView() {
        mRecyclerView= (RecyclerView) findViewById(R.id.list_rv);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_music_month_list;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public Loader<MusicMonthPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<MusicMonthPresenter>(this, new PresenterFactory() {
            @Override
            public BasePresenter create() {
                return new MusicMonthPresenter(MusicMonthListActivity.this);
            }
        });
    }

    @Override
    public void onLoadFinished(Loader<MusicMonthPresenter> loader, MusicMonthPresenter data) {
        mPresenter=data;
        mPresenter.showList(mDate);
    }

    @Override
    public void onLoaderReset(Loader<MusicMonthPresenter> loader) {

    }

    @Override
    public void showList(List<MusicMonthItem> items) {
        MonthMusicAdapter adapter=new MonthMusicAdapter(items);
        mRecyclerView.setAdapter(adapter);
    }
}
