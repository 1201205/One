package com.hyc.zhihu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

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

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/5/16.
 */
public class ReadingActivity extends BaseActivity<ReadingPresenter> implements ReadingView, LoaderManager.LoaderCallbacks<ReadingPresenter>, OnLoadMoreListener {
    private SwipeToLoadLayout swipeToLoadLayout;

    private ListView listView;
    private TextView titleLayout;

    private ViewPager viewPager;

    private ViewGroup indicators;
    private LoopViewPagerAdapter mPagerAdapter;
    private ReadingAdapter mReadingAdapter;
    private int mIndex;
    private int lastFirstVisibleItem = -1;

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
        titleLayout = (TextView) findViewById(R.id.title);
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
                int section = mReadingAdapter.getSectionForPosition(firstVisibleItem - 1);
                int nextSecPosition = mReadingAdapter.getPositionForSection(section + 1) + 1;

                if (firstVisibleItem != lastFirstVisibleItem) {

                    ViewGroup.MarginLayoutParams params =
                            (ViewGroup.MarginLayoutParams) titleLayout
                                    .getLayoutParams();
                    params.topMargin = 0;
                    titleLayout.setLayoutParams(params);
                    String date = mReadingAdapter.getDateBySection(section);
                    if (TextUtils.isEmpty(date)) {
                        titleLayout.setVisibility(View.GONE);
                    } else {
                        titleLayout.setText(date);
                        titleLayout.setVisibility(View.VISIBLE);

                    }

                }
                if (nextSecPosition == firstVisibleItem + 1) {

                    int titleHeight = titleLayout.getHeight();
                    View childView = view.getChildAt(0);
                    if (childView == null) {
                        return;
                    }
                    int bottom = childView.getBottom();
                    ViewGroup.MarginLayoutParams params =
                            (ViewGroup.MarginLayoutParams) titleLayout
                                    .getLayoutParams();
                    if (bottom < titleHeight) {
                        float pushedDistance = bottom - titleHeight;
                        params.topMargin = (int) pushedDistance;
                        titleLayout.setLayoutParams(params);
                    } else {

                        if (params.topMargin != 0) {
                            params.topMargin = 0;
                            titleLayout.setLayoutParams(params);
                        }
                    }
                }
                lastFirstVisibleItem = firstVisibleItem;
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(this);
        mReadingAdapter.setItemClickListener(new ReadingAdapter.OnReadingItemClickListener() {
            @Override
            public void onItemClicked(RealReading reading) {
                jumpToContent(reading);
            }
        });
    }

    private void jumpToContent(RealReading reading) {
        switch (reading.getType()){
            case 1:
                Intent essay=new Intent(this,EssayActivity.class);
                essay.putExtra(EssayActivity.ID,reading.getContent().getId());
                startActivity(essay);
                break;
            case 2:
                Intent serial=new Intent(this,SerialActivity.class);
                serial.putExtra(SerialActivity.ID,reading.getContent().getId());
                startActivity(serial);
                break;
            case 3:
                Intent question=new Intent(this,QuestionActivity.class);
                question.putExtra(QuestionActivity.ID,reading.getContent().getId());
                startActivity(question);
                break;
        }
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
        mPagerAdapter.setItemClickListener(new LoopViewPagerAdapter.ItemClickListener() {
            @Override
            public void itemClicked(HeadScrollItem data) {
                Intent intent = new Intent(ReadingActivity.this, ReadingListActivity.class);
                intent.putExtra(ReadingListActivity.HEAD_ITEM, data);
                startActivity(intent);
            }
        });

    }

    @Override
    public void showList(List<RealReading> realReadings, LinkedHashMap<Integer, String> indexer) {
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
        mPresenter.getAndShowList(++mIndex);
    }
}
