package com.hyc.zhihu.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hyc.zhihu.R;
import com.hyc.zhihu.base.BaseActivity;
import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.base.BaseRecyclerAdapter;
import com.hyc.zhihu.base.ListPresenter;
import com.hyc.zhihu.base.PresenterFactory;
import com.hyc.zhihu.base.PresenterLoader;
import com.hyc.zhihu.beans.music.MusicMonthItem;
import com.hyc.zhihu.presenter.MusicMonthPresenter;
import com.hyc.zhihu.presenter.OtherMusicPresenter;
import com.hyc.zhihu.ui.adpter.AdapterFactory;
import com.hyc.zhihu.ui.adpter.ListPresenterFactory;
import com.hyc.zhihu.ui.adpter.MonthMusicAdapter;
import com.hyc.zhihu.ui.adpter.MonthMusicAdapter2;
import com.hyc.zhihu.ui.adpter.MonthPictureAdapter;
import com.hyc.zhihu.utils.AppUtil;
import com.hyc.zhihu.utils.S;
import com.hyc.zhihu.view.MusicMonthView;
import com.hyc.zhihu.view.OtherPictureView;
import com.hyc.zhihu.widget.DividerItemDecoration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ray on 16/5/26.
 */
public class OtherMusicActivity extends BaseActivity
    implements OtherPictureView,
    OnLoadMoreListener {
    private RecyclerView mRecyclerView;
    private SwipeToLoadLayout mSwipeToLoadLayout;
    private String mID;
    private int mType;
    private BaseRecyclerAdapter mAdapter;
    private ImageView mNoItemIV;
    private boolean mCanLoad = true;
    private LinearLayoutManager mLayoutManager;
    private String mTitle;
    private static final int PAGE_COUNT = 20;


    @Override
    protected void handleIntent() {
        mID = getIntent().getStringExtra(S.ID);
        mType = getIntent().getIntExtra(S.TYPE,1);
        mTitle=getIntent().getStringExtra(S.TITLE);
    }


    @Override protected String getTitleString() {
        return mTitle;
    }


    @Override
    protected void initView() {
        mSwipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.swipe_target);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mNoItemIV = (ImageView) findViewById(R.id.no_item_iv);
        mSwipeToLoadLayout.setOnLoadMoreListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mCanLoad && mLayoutManager.findLastCompletelyVisibleItemPosition() ==
                        mAdapter.getItemCount() - 1) {
                        mSwipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mPresenter= ListPresenterFactory.getPresenter(this,mType);
        mPresenter.attachView();
        ((ListPresenter)mPresenter).showList(mID);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,RecyclerView.VERTICAL,
            Color.GRAY));
    }


    @Override
    protected int getLayoutID() {
        return R.layout.activity_other_muisc;
    }


    @Override
    protected void initLoader() {
//        getSupportLoaderManager().initLoader(AppUtil.getID(), null, this);
    }

//
//    @Override
//    public Loader<OtherMusicPresenter> onCreateLoader(int id, Bundle args) {
//        return new PresenterLoader<OtherMusicPresenter>(this, new PresenterFactory() {
//            @Override
//            public BasePresenter create() {
//                return new OtherMusicPresenter(OtherMusicActivity.this);
//            }
//        });
//    }


//    @Override
//    public void onLoadFinished(Loader<OtherMusicPresenter> loader, OtherMusicPresenter data) {
//        mPresenter = data;
//        mPresenter.attachView();
//        mPresenter.showList(mID);
//    }


//    @Override
//    public void onLoaderReset(Loader<OtherMusicPresenter> loader) {
//        mPresenter = null;
//    }


//    @Override public void showList(List<MusicMonthItem> datas) {
//        mAdapter = new MonthMusicAdapter2(this,datas);
//        mRecyclerView.setAdapter(mAdapter);
//        if (datas.size() < PAGE_COUNT) {
//            mCanLoad = false;
//            mSwipeToLoadLayout.setLoadMoreEnabled(false);
//        }
//    }
//
//
//    @Override public void refresh(List<MusicMonthItem> datas) {
//        mSwipeToLoadLayout.setLoadingMore(false);
//        mAdapter.addItems(datas);
//    }



    @Override
    public void showList(List datas) {
        mAdapter = AdapterFactory.getAdapter(this, datas,mType);
        mRecyclerView.setAdapter(mAdapter);
        if (datas.size() < PAGE_COUNT) {
            mCanLoad = false;
            mSwipeToLoadLayout.setLoadMoreEnabled(false);
        }
    }

    @Override
    public void refresh(List datas) {
        mSwipeToLoadLayout.setLoadingMore(false);
        mAdapter.addItems(datas);
    }

    @Override public void nothingGet() {
        mCanLoad = false;
        if (mAdapter == null) {
            mNoItemIV.setVisibility(View.VISIBLE);
            mSwipeToLoadLayout.setVisibility(View.GONE);
        } else {
            mSwipeToLoadLayout.setLoadingMore(false);
            mSwipeToLoadLayout.setLoadMoreEnabled(false);
            AppUtil.showToast(R.string.no_more);
        }
    }


    @Override public void onLoadMore() {
        ((ListPresenter)mPresenter).refresh();
    }
}
