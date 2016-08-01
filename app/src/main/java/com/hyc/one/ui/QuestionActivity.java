package com.hyc.one.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hyc.one.R;
import com.hyc.one.base.BaseActivity;
import com.hyc.one.base.BasePresenter;
import com.hyc.one.base.PresenterFactory;
import com.hyc.one.base.PresenterLoader;
import com.hyc.one.beans.Comment;
import com.hyc.one.beans.Question;
import com.hyc.one.beans.QuestionContent;
import com.hyc.one.event.NetWorkChangeEvent;
import com.hyc.one.presenter.QuestionContentPresenter;
import com.hyc.one.ui.adpter.CommentAdapter;
import com.hyc.one.ui.adpter.QuestionAdapter;
import com.hyc.one.utils.AppUtil;
import com.hyc.one.view.ReadingContentView;
import com.hyc.one.widget.ListViewForScrollView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by ray on 16/5/18.
 */
public class QuestionActivity extends BaseActivity<QuestionContentPresenter> implements
        ReadingContentView<QuestionContent, Question>,
        OnLoadMoreListener,
        LoaderManager.LoaderCallbacks<QuestionContentPresenter> {
    private SwipeToLoadLayout swipeToLoadLayout;
    private View mHeader;
    private TextView mTitleTV;
    private TextView mDesTV;
    private TextView mAuthorTV;
    private TextView mDateTV;
    private TextView mContentTV;
    private TextView mEditorTV;
    private ListView listView;
    private ListViewForScrollView mRelateLV;
    private ListViewForScrollView mHotCommentsLV;
    private CommentAdapter mCommentAdapter;
    private LinearLayout mRelateLL;
    private LinearLayout mHotLL;
    private LinearLayout mCommentLL;
    private String mID;
    public static final String ID = "id";
    private boolean mHasMoreComments = true;


    @Override
    protected void handleIntent() {
        mID = getIntent().getStringExtra(ID);
    }


    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
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
        mHeader = LayoutInflater.from(this).inflate(R.layout.qustion_header, null);
        mTitleTV = (TextView) mHeader.findViewById(R.id.title_tv);
        mAuthorTV = (TextView) mHeader.findViewById(R.id.author_tv);
        mContentTV = (TextView) mHeader.findViewById(R.id.content_tv);
        mDesTV = (TextView) mHeader.findViewById(R.id.des_tv);
        mDateTV = (TextView) mHeader.findViewById(R.id.date_tv);
        mEditorTV = (TextView) mHeader.findViewById(R.id.editor_tv);
        mRelateLV = (ListViewForScrollView) mHeader.findViewById(R.id.relate_lv);
        mRelateLL = (LinearLayout) mHeader.findViewById(R.id.relate_ll);
        mHotLL = (LinearLayout) mHeader.findViewById(R.id.hot_ll);
        mCommentLL = (LinearLayout) mHeader.findViewById(R.id.comment_ll);
        mHotCommentsLV = (ListViewForScrollView) mHeader.findViewById(R.id.hot_lv);
        listView.addHeaderView(mHeader);
        mCommentAdapter = new CommentAdapter();
        listView.setAdapter(mCommentAdapter);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        if (!NetWorkChangeEvent.hasNetWork) {
            changeVisibility(false);
        }
    }


    @Override
    protected int getLayoutID() {
        return R.layout.activity_question;
    }


    @Override
    public void showContent(QuestionContent content) {
        mTitleTV.setText(content.getQuestion_title());
        mDesTV.setText(content.getQuestion_content());
        mContentTV.setText(Html.fromHtml(content.getAnswer_content()));
        mEditorTV.setText(content.getCharge_edt());
        mAuthorTV.setText(content.getAnswer_title());
    }


    @Override
    public void showRelate(List<Question> questions) {
        if (questions == null || questions.size() == 0) {
            mRelateLL.setVisibility(View.GONE);
        } else {
            QuestionAdapter adapter = new QuestionAdapter(this, questions);
            mRelateLV.setAdapter(adapter);
            adapter.setItemClickListener(new QuestionAdapter.OnReadingItemClickListener() {
                @Override
                public void onItemClicked(Question question) {
                    jumpToNewQuestion(question);
                }
            });
        }
    }


    @Override
    protected String getTitleString() {
        return AppUtil.getString(R.string.question);
    }


    private void jumpToNewQuestion(Question s) {
        Intent i = new Intent(this, QuestionActivity.class);
        i.putExtra(QuestionActivity.ID, s.getQuestion_id());
        startActivity(i);
    }


    @Override
    public void refreshCommentList(List<Comment> comments) {
        mCommentAdapter.refreshComments(comments);
        swipeToLoadLayout.setLoadingMore(false);

    }


    @Override
    public void showHotComments(List<Comment> comments) {
        if (comments == null || comments.size() == 0) {
            mHotLL.setVisibility(View.GONE);
        } else {
            CommentAdapter adapter = new CommentAdapter();
            mHotCommentsLV.setAdapter(adapter);
            adapter.refreshComments(comments);
        }
    }


    @Override
    public void showNoComments() {
        AppUtil.showToast(R.string.no_more);
        mHasMoreComments = false;
        swipeToLoadLayout.setLoadingMore(false);
        swipeToLoadLayout.setLoadMoreEnabled(false);
    }

    @Override
    protected void initLoader() {
        getSupportLoaderManager().initLoader(AppUtil.getID(), null, this);
    }

    @Override
    public Loader<QuestionContentPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<QuestionContentPresenter>(this, new PresenterFactory() {
            @Override
            public BasePresenter create() {
                return new QuestionContentPresenter(QuestionActivity.this);
            }
        });
    }


    @Override
    public void onLoadFinished(Loader<QuestionContentPresenter> loader, QuestionContentPresenter data) {
        mPresenter = data;
        mPresenter.attachView();
        mPresenter.getAndShowContent(mID);
    }


    @Override
    public void onLoaderReset(Loader<QuestionContentPresenter> loader) {
        mPresenter = null;
    }


    @Override
    public void onLoadMore() {
        mPresenter.getAndShowCommentList();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(NetWorkChangeEvent event) {
        if (NetWorkChangeEvent.hasNetWork) {
            ListAdapter adapter=listView.getAdapter();
            if (adapter==null||adapter.getCount()==0) {
                mPresenter.getAndShowContent(mID);
            }            if (mHasMoreComments) {
                swipeToLoadLayout.setLoadMoreEnabled(true);
            }
            swipeToLoadLayout.setLoadingMore(false);
            if (mCommentLL.getVisibility()!=View.VISIBLE) {
                changeVisibility(true);
            }
        } else {
            swipeToLoadLayout.setLoadMoreEnabled(false);
        }
    }

    private void changeVisibility(boolean visible) {
        if (visible) {
            mCommentLL.setVisibility(View.VISIBLE);
            mRelateLL.setVisibility(View.VISIBLE);
            mHotLL.setVisibility(View.VISIBLE);
        } else {
            mCommentLL.setVisibility(View.GONE);
            mRelateLL.setVisibility(View.GONE);
            mHotLL.setVisibility(View.GONE);
            swipeToLoadLayout.setLoadMoreEnabled(false);
        }
    }
}
