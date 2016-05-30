package com.hyc.zhihu.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hyc.zhihu.R;
import com.hyc.zhihu.base.BaseActivity;
import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.base.PresenterFactory;
import com.hyc.zhihu.base.PresenterLoader;
import com.hyc.zhihu.beans.Comment;
import com.hyc.zhihu.beans.movie.MovieContent;
import com.hyc.zhihu.beans.movie.MovieStory;
import com.hyc.zhihu.beans.movie.MovieStoryWrapper;
import com.hyc.zhihu.helper.FrescoHelper;
import com.hyc.zhihu.presenter.MovieContentPresenter;
import com.hyc.zhihu.ui.adpter.CommentAdapter;
import com.hyc.zhihu.ui.adpter.MovieStoryPictureAdapter;
import com.hyc.zhihu.utils.S;
import com.hyc.zhihu.view.MovieContentView;
import com.hyc.zhihu.widget.ListViewForScrollView;
import com.hyc.zhihu.widget.RectGridView;

import java.util.List;

/**
 * Created by ray on 16/5/30.
 */
public class MovieContentActivity extends BaseActivity<MovieContentPresenter> implements MovieContentView, LoaderManager.LoaderCallbacks<MovieContentPresenter>,OnLoadMoreListener {
    private String mID;
    private SwipeToLoadLayout swipeToLoadLayout;
    private View mHeader;
    private ListViewForScrollView mHotCommentsLV;
    private CommentAdapter mCommentAdapter;
    private ListView listView;
    private SimpleDraweeView mCoverSDV;
    private SimpleDraweeView mHeadIV;
    private TextView mNameTV;
    private TextView mDateTV;
    private TextView mNumTV;
    private TextView mTitleTV;
    private TextView mContentTV;
    private TextView mCountTV;
    private TextView mAllTV;
    private RectGridView mTagGV;
    private ImageView mTagIV;
    private ImageView mPhotoIV;
    private ImageView mInfoIV;
    private RecyclerView mPhotoRV;
    private TextView mInfoTV;
    private TextView mScoreTV;
    private boolean mHasMoreComments = true;

    @Override
    protected void handleIntent() {
        mID = getIntent().getStringExtra(S.ID);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportLoaderManager().initLoader(132,null,this);
    }

    @Override
    protected void initView() {
        mCommentAdapter = new CommentAdapter(true);
        listView = (ListView) findViewById(R.id.swipe_target);
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (mHasMoreComments && view.getLastVisiblePosition() == view.getCount() - 1 && !ViewCompat.canScrollVertically(view, 1)) {
                        swipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        mHeader = LayoutInflater.from(this).inflate(R.layout.header_movie, null);
        mCoverSDV = (SimpleDraweeView) mHeader.findViewById(R.id.cover_sdv);
        mScoreTV = (TextView) mHeader.findViewById(R.id.score_tv);
        mHeadIV = (SimpleDraweeView) mHeader.findViewById(R.id.head_iv);
        mNameTV = (TextView) mHeader.findViewById(R.id.name_tv);
        mDateTV = (TextView) mHeader.findViewById(R.id.date_tv);
        mNumTV = (TextView) mHeader.findViewById(R.id.num_tv);
        mTitleTV = (TextView) mHeader.findViewById(R.id.title_tv);
        mContentTV = (TextView) mHeader.findViewById(R.id.content_tv);
        mCountTV = (TextView) mHeader.findViewById(R.id.count_tv);
        mAllTV = (TextView) mHeader.findViewById(R.id.all_tv);
        mTagGV = (RectGridView) mHeader.findViewById(R.id.tag_gv);
        mTagIV = (ImageView) mHeader.findViewById(R.id.tag_iv);
        mPhotoIV = (ImageView) mHeader.findViewById(R.id.photo_iv);
        mInfoIV = (ImageView) mHeader.findViewById(R.id.info_iv);
        mPhotoRV = (RecyclerView) mHeader.findViewById(R.id.photo_rv);
        mInfoTV = (TextView) mHeader.findViewById(R.id.info_tv);
        mHotCommentsLV = (ListViewForScrollView) mHeader.findViewById(R.id.hot_lv);
        mPhotoIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhotoIV.setSelected(true);
                mPhotoRV.setVisibility(View.VISIBLE);
                mInfoIV.setSelected(false);
                mInfoTV.setVisibility(View.GONE);
                mTagIV.setSelected(false);
                mTagGV.setVisibility(View.GONE);
            }
        });
        mTagIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTagIV.setSelected(true);
                mTagGV.setVisibility(View.VISIBLE);
                mPhotoIV.setSelected(false);
                mPhotoRV.setVisibility(View.GONE);
                mInfoIV.setSelected(false);
                mInfoTV.setVisibility(View.GONE);
            }
        });
        mInfoIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInfoIV.setSelected(true);
                mInfoTV.setVisibility(View.VISIBLE);
                mPhotoIV.setSelected(false);
                mPhotoRV.setVisibility(View.GONE);
                mTagIV.setSelected(false);
                mTagGV.setVisibility(View.GONE);
            }
        });
        listView.addHeaderView(mHeader);
        listView.setAdapter(mCommentAdapter);
        swipeToLoadLayout.setOnLoadMoreListener(this);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_question;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public Loader<MovieContentPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<MovieContentPresenter>(this, new PresenterFactory() {
            @Override
            public BasePresenter create() {
                return new MovieContentPresenter(MovieContentActivity.this);
            }
        });
    }

    @Override
    public void onLoadFinished(Loader<MovieContentPresenter> loader, MovieContentPresenter data) {
        mPresenter = data;
        mPresenter.getAndShowContent(mID);
    }

    @Override
    public void onLoaderReset(Loader<MovieContentPresenter> loader) {
        mPresenter = null;
    }

    @Override
    public void showContent(MovieContent data) {
        FrescoHelper.loadImage(mCoverSDV, data.getDetailcover());
        mScoreTV.setText(data.getScore());
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mPhotoRV.setLayoutManager(manager);
        MovieStoryPictureAdapter adapter=new MovieStoryPictureAdapter(data.getPhoto());
        mPhotoRV.setAdapter(adapter);
        mInfoTV.setText(data.getInfo());
        mTagGV.setTags(data.getKeywords().split(";"));
    }

    @Override
    public void refreshComment(List<Comment> comments) {
        mCommentAdapter.refreshComments(comments);
        swipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void showHotComment(List<Comment> comments) {
        CommentAdapter adapter = new CommentAdapter();
        adapter.refreshComments(comments);
        mHotCommentsLV.setAdapter(adapter);
    }

    @Override
    public void showNoComments() {
        mHasMoreComments=false;
        swipeToLoadLayout.setLoadMoreEnabled(false);
        swipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void showStory(MovieStoryWrapper wrapper) {
        MovieStory story = wrapper.getData().get(0);
        FrescoHelper.loadImage(mHeadIV, story.getUser().getWeb_url());
        mDateTV.setText(story.getInput_date());
        mNumTV.setText(story.getPraisenum()+"");
        mTitleTV.setText(story.getTitle());
        mContentTV.setText(story.getContent());
        mCountTV.setText(wrapper.getCount() + "条电影故事");
    }

    @Override
    public void onLoadMore() {
        mPresenter.refreshComments();
    }
}
