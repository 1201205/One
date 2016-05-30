package com.hyc.zhihu.ui;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hyc.zhihu.R;
import com.hyc.zhihu.base.BaseActivity;
import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.base.PresenterFactory;
import com.hyc.zhihu.base.PresenterLoader;
import com.hyc.zhihu.beans.movie.Movie;
import com.hyc.zhihu.presenter.MovieListPresenter;
import com.hyc.zhihu.ui.adpter.MovieListAdapter;
import com.hyc.zhihu.view.MovieListView;

import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class MovieListActivity extends BaseActivity<MovieListPresenter> implements MovieListView, LoaderManager.LoaderCallbacks<MovieListPresenter> {
    private RecyclerView mRecyclerView;
    private MovieListAdapter mAdapter;
    private LinearLayoutManager mManager;
    private boolean mHasMore;
    private boolean mIsLoading;

    @Override
    protected void handleIntent() {

    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisiblePosition = mManager.findLastVisibleItemPosition();
                    if (!mIsLoading && mHasMore && lastVisiblePosition >= mManager.getItemCount() - 1) {
                        mIsLoading = true;
                        mPresenter.refresh();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_movie_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().initLoader(131, null, this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public Loader<MovieListPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<MovieListPresenter>(this, new PresenterFactory() {
            @Override
            public BasePresenter create() {
                return new MovieListPresenter(MovieListActivity.this);
            }
        });
    }

    @Override
    public void onLoadFinished(Loader<MovieListPresenter> loader, MovieListPresenter data) {
        mPresenter = data;
        mPresenter.showContent();
    }

    @Override
    public void onLoaderReset(Loader<MovieListPresenter> loader) {

    }

    @Override
    public void showList(List<Movie> movies) {
        mAdapter = new MovieListAdapter(movies);
        mManager = new LinearLayoutManager(this);
        mManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void refreshList(List<Movie> movies) {
        if (movies != null && movies.size() > 0) {
            mAdapter.refresh(movies);
        } else {
            mHasMore = false;
        }
        mIsLoading = false;
    }
}
