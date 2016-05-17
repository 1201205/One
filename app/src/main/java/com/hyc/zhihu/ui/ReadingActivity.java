package com.hyc.zhihu.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ListView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hyc.zhihu.R;
import com.hyc.zhihu.base.BaseActivity;
import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.base.PresenterFactory;
import com.hyc.zhihu.base.PresenterLoader;
import com.hyc.zhihu.beans.HeadScrollItem;
import com.hyc.zhihu.beans.RealReading;
import com.hyc.zhihu.presenter.ReadingPresenter;
import com.hyc.zhihu.ui.adpter.LoopViewPagerAdapter;
import com.hyc.zhihu.ui.adpter.ReadingAdapter;
import com.hyc.zhihu.view.ReadingView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/5/16.
 */
public class ReadingActivity extends BaseActivity implements ReadingView, LoaderManager.LoaderCallbacks<ReadingPresenter>, OnLoadMoreListener {
    private SwipeToLoadLayout swipeToLoadLayout;

    private ListView listView;

    private ViewPager viewPager;

    private ViewGroup indicators;
    private LoopViewPagerAdapter mPagerAdapter;
    private ReadingAdapter mReadingAdapter;
    private ReadingPresenter mPresenter;
    private int mIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportLoaderManager().initLoader(121, null, this);
        mReadingAdapter = new ReadingAdapter(this);
        listView = (ListView) findViewById(R.id.swipe_target);
        listView.setAdapter(mReadingAdapter);
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        View v = LayoutInflater.from(this).inflate(R.layout.layout_viewpager, null);
        viewPager = (ViewPager) v.findViewById(R.id.viewPager);
        listView.addHeaderView(v);
        indicators = (ViewGroup) v.findViewById(R.id.indicators);
        viewPager.addOnPageChangeListener(mPagerAdapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() == view.getCount() - 1 && !ViewCompat.canScrollVertically(view, 1)) {
                        swipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {
                    if (mPagerAdapter != null) {
                        mPagerAdapter.start();
                    }

                } else {
                    if (mPagerAdapter != null) {
                        mPagerAdapter.stop();
                    }
                }
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(this);


    }

    @Override
    protected void handleIntent() {
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_reading;
    }

    @Override
    public void showHead(List<HeadScrollItem> headScrollItems) {
        mPagerAdapter = new LoopViewPagerAdapter(viewPager, indicators);
        viewPager.setAdapter(mPagerAdapter);
        viewPager.addOnPageChangeListener(mPagerAdapter);
        mPagerAdapter.setList(headScrollItems);
    }

    @Override
    public void showList(List<RealReading> realReadings, HashMap<Integer,Integer> indexer) {
        mReadingAdapter.refreshList(realReadings, indexer);
        swipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public Loader<ReadingPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<ReadingPresenter>(this, new PresenterFactory() {
            @Override
            public BasePresenter create() {
                return new ReadingPresenter(ReadingActivity.this);
            }
        });
    }

    @Override
    public void onLoadFinished(Loader<ReadingPresenter> loader, ReadingPresenter data) {
        mPresenter = data;
        mPresenter.showContent();
    }


    @Override
    public void onLoaderReset(Loader<ReadingPresenter> loader) {
        mPresenter = null;
    }

    @Override
    public void onLoadMore() {
        mPresenter.getAndShowList(mIndex++);
    }
}
