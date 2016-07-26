package com.hyc.one.ui;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewCompat;
import android.widget.AbsListView;
import android.widget.ListView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hyc.one.R;
import com.hyc.one.base.BaseActivity;
import com.hyc.one.base.BasePresenter;
import com.hyc.one.base.PresenterFactory;
import com.hyc.one.base.PresenterLoader;
import com.hyc.one.beans.movie.MovieStory;
import com.hyc.one.presenter.MovieStoryPresenter;
import com.hyc.one.ui.adpter.MovieStoryAdapter;
import com.hyc.one.utils.AppUtil;
import com.hyc.one.utils.S;
import com.hyc.one.view.MovieStoryView;

import java.util.List;

/**
 * Created by ray on 16/6/6.
 */
public class MovieStoryActivity extends BaseActivity<MovieStoryPresenter> implements MovieStoryView,
        LoaderManager.LoaderCallbacks<MovieStoryPresenter>,
        OnLoadMoreListener {
    private String mID;
    private SwipeToLoadLayout swipeToLoadLayout;
    private ListView listView;
    private MovieStoryAdapter mAdapter;
    private boolean mHasMoreComments = true;


    @Override
    protected void handleIntent() {
        mID = getIntent().getStringExtra(S.ID);
    }


    @Override
    protected void initView() {
        mAdapter = new MovieStoryAdapter();
        listView = (ListView) findViewById(R.id.swipe_target);
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (mHasMoreComments &&
                        scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() == view.getCount() - 1 &&
                            !ViewCompat.canScrollVertically(view, 1)) {
                        swipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }


            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        listView.setAdapter(mAdapter);
        swipeToLoadLayout.setOnLoadMoreListener(this);
    }


    @Override
    protected int getLayoutID() {
        return R.layout.activity_question;
    }


    @Override
    public Loader<MovieStoryPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<MovieStoryPresenter>(this, new PresenterFactory() {
            @Override
            public BasePresenter create() {
                return new MovieStoryPresenter(MovieStoryActivity.this);
            }
        });
    }


    @Override
    public void onLoadFinished(Loader<MovieStoryPresenter> loader, MovieStoryPresenter data) {
        mPresenter = data;
        mPresenter.attachView();
        mPresenter.getAndShowList(mID);
    }


    @Override
    public void onLoaderReset(Loader<MovieStoryPresenter> loader) {
        mPresenter = null;

    }


    @Override
    protected String getTitleString() {
        return AppUtil.getString(R.string.all_movie_story);
    }


    @Override
    public void onLoadMore() {
        mPresenter.refreshList();
    }


    @Override
    public void showList(List<MovieStory> stories) {
        mAdapter.refreshComments(stories);
    }


    @Override
    public void refreshList(List<MovieStory> stories) {
        if (stories == null || stories.size() == 0) {
            AppUtil.showToast(R.string.no_more);
            mHasMoreComments = false;
            swipeToLoadLayout.setLoadingMore(false);
            swipeToLoadLayout.setLoadMoreEnabled(false);
        } else {
            mAdapter.refreshComments(stories);
            swipeToLoadLayout.setLoadingMore(false);
        }

    }

    @Override
    protected void initLoader() {
        getSupportLoaderManager().initLoader(AppUtil.getID(), null, this);
    }
}
