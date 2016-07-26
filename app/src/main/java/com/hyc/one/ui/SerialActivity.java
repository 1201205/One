package com.hyc.one.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewCompat;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.hyc.one.beans.RealArticleAuthor;
import com.hyc.one.beans.Serial;
import com.hyc.one.beans.SerialContent;
import com.hyc.one.presenter.SerialContentPresenter;
import com.hyc.one.ui.adpter.CommentAdapter;
import com.hyc.one.ui.adpter.SerialAdapter;
import com.hyc.one.utils.AppUtil;
import com.hyc.one.utils.DateUtil;
import com.hyc.one.utils.S;
import com.hyc.one.view.ReadingContentView;
import com.hyc.one.widget.CircleImageView;
import com.hyc.one.widget.ListViewForScrollView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ray on 16/5/18.
 */
public class SerialActivity extends BaseActivity<SerialContentPresenter> implements
        ReadingContentView<SerialContent, Serial>,
        OnLoadMoreListener,
        LoaderManager.LoaderCallbacks<SerialContentPresenter> {
    private SwipeToLoadLayout swipeToLoadLayout;
    private View mHeader;
    private TextView mTitleTV;
    private TextView mDesTV;
    private TextView mAuthorTV;
    private TextView mDateTV;
    private TextView mContentTV;
    private TextView mEditorTV;
    private TextView mAuthorNameTV;
    private CircleImageView mHeaderIV;
    private CircleImageView mAuthorHeaderIV;
    private ImageView mSerialIV;
    private ListView listView;
    private ListViewForScrollView mRelateLV;
    private ListViewForScrollView mHotCommentsLV;
    private LinearLayout mRelateLL;
    private CommentAdapter mCommentAdapter;
    private String mID;
    private boolean mHasMoreComments = true;
    private LinearLayout mHotLL;


    @Override
    protected void handleIntent() {
        mID = getIntent().getStringExtra(S.ID);
    }


    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.swipe_target);
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (mHasMoreComments && view.getLastVisiblePosition() == view.getCount() - 1 &&
                            !ViewCompat.canScrollVertically(view, 1)) {
                        swipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }


            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        mHeader = LayoutInflater.from(this).inflate(R.layout.serial_header, null);
        mTitleTV = (TextView) mHeader.findViewById(R.id.title_tv);
        mAuthorTV = (TextView) mHeader.findViewById(R.id.name_tv);
        mContentTV = (TextView) mHeader.findViewById(R.id.content_tv);
        mDesTV = (TextView) mHeader.findViewById(R.id.author_des_tv);
        mDateTV = (TextView) mHeader.findViewById(R.id.date_tv);
        mAuthorHeaderIV = (CircleImageView) mHeader.findViewById(R.id.author_head_iv);
        mHeaderIV = (CircleImageView) mHeader.findViewById(R.id.head_iv);
        mSerialIV = (ImageView) mHeader.findViewById(R.id.serial_iv);
        mEditorTV = (TextView) mHeader.findViewById(R.id.editor_tv);
        mAuthorNameTV = (TextView) mHeader.findViewById(R.id.author_name_tv);
        mRelateLV = (ListViewForScrollView) mHeader.findViewById(R.id.relate_lv);
        mRelateLL = (LinearLayout) mHeader.findViewById(R.id.relate_ll);
        mHotCommentsLV = (ListViewForScrollView) mHeader.findViewById(R.id.hot_lv);
        listView.addHeaderView(mHeader);
        mCommentAdapter = new CommentAdapter();
        listView.setAdapter(mCommentAdapter);
        swipeToLoadLayout.setOnLoadMoreListener(this);

    }


    @Override
    protected int getLayoutID() {
        return R.layout.activity_question;
    }


    @Override
    public void showContent(final SerialContent content) {
        RealArticleAuthor author = content.getAuthor();
        if (!TextUtils.isEmpty(author.getWeb_url())) {
            Picasso.with(this)
                    .load(author.getWeb_url())
                    .placeholder(R.drawable.head)
                    .into(mHeaderIV);
            Picasso.with(this)
                    .load(author.getWeb_url())
                    .placeholder(R.drawable.head)
                    .into(mAuthorHeaderIV);
        } else {
            mHeaderIV.setImageResource(R.drawable.head);
            mAuthorHeaderIV.setImageResource(R.drawable.head);
        }
        String id = author.getUser_id();
        View.OnClickListener listener = AppUtil.getOtherClickListener(id, this);
        mAuthorTV.setOnClickListener(listener);
        mAuthorNameTV.setOnClickListener(listener);
        mHeaderIV.setOnClickListener(listener);
        mAuthorHeaderIV.setOnClickListener(listener);
        mTitleTV.setText(content.getTitle());
        mDesTV.setText(author.getDesc());
        mDateTV.setText(DateUtil.getCommentDate(content.getMaketime()));
        mContentTV.setText(Html.fromHtml(content.getContent()));
        mEditorTV.setText(content.getCharge_edt());
        mAuthorTV.setText(author.getUser_name());
        mAuthorNameTV.setText(author.getUser_name());
        mHotLL = (LinearLayout) mHeader.findViewById(R.id.hot_ll);

        mSerialIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SerialActivity.this, SerialListActivity.class);
                i.putExtra(S.ID, content.getSerial_id());
                startActivity(i);
            }
        });
    }


    @Override
    public void showRelate(List<Serial> serials) {
        if (serials == null || serials.size() == 0) {
            mRelateLL.setVisibility(View.GONE);
        } else {
            SerialAdapter adapter = new SerialAdapter(this, serials);
            mRelateLV.setAdapter(adapter);
            adapter.setItemClickListener(new SerialAdapter.OnReadingItemClickListener() {
                @Override
                public void onItemClicked(Serial s) {
                    jumpToNewSerial(s);
                }
            });
        }
    }


    @Override
    protected String getTitleString() {
        return AppUtil.getString(R.string.serial);
    }


    private void jumpToNewSerial(Serial s) {
        Intent i = new Intent(this, SerialActivity.class);
        i.putExtra(S.ID, s.getId());
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
    protected void initLoader() {
        getSupportLoaderManager().initLoader(AppUtil.getID(), null, this);
    }


    @Override
    public void showNoComments() {
        AppUtil.showToast(R.string.no_more);
        mHasMoreComments = false;
        swipeToLoadLayout.setLoadingMore(false);
        swipeToLoadLayout.setLoadMoreEnabled(false);
    }


    @Override
    public Loader<SerialContentPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<SerialContentPresenter>(this, new PresenterFactory() {
            @Override
            public BasePresenter create() {
                return new SerialContentPresenter(SerialActivity.this);
            }
        });
    }


    @Override
    public void onLoadFinished(Loader<SerialContentPresenter> loader, SerialContentPresenter data) {
        mPresenter = data;
        mPresenter.attachView();
        mPresenter.getAndShowContent(mID);
    }


    @Override
    public void onLoaderReset(Loader<SerialContentPresenter> loader) {
        mPresenter = null;
    }


    @Override
    public void onLoadMore() {
        mPresenter.getAndShowCommentList();
    }


}
