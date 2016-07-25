package com.hyc.zhihu.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewCompat;
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
import com.hyc.zhihu.beans.Song;
import com.hyc.zhihu.beans.music.Music;
import com.hyc.zhihu.event.PlayEvent;
import com.hyc.zhihu.helper.FrescoHelper;
import com.hyc.zhihu.player.ManagedMediaPlayer;
import com.hyc.zhihu.player.MyPlayer;
import com.hyc.zhihu.presenter.MusicRelatePresenter;
import com.hyc.zhihu.ui.adpter.CommentAdapter;
import com.hyc.zhihu.ui.fragment.LoadingDialogFragment;
import com.hyc.zhihu.utils.AppUtil;
import com.hyc.zhihu.utils.S;
import com.hyc.zhihu.view.MusicRelateView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by ray on 16/5/25.
 */
public class MusicRelateActivity extends BaseActivity<MusicRelatePresenter> implements
    MusicRelateView,
    LoaderManager.LoaderCallbacks<MusicRelatePresenter>,
    OnLoadMoreListener {
    private String mID;
    private ListView mListView;
    private SwipeToLoadLayout mSwipeToLoadLayout;
    private CommentAdapter mAdapter;


    @Override
    protected void handleIntent() {
        mID = getIntent().getStringExtra(S.ID);
    }


    private ImageView mPlayIV;
    private ImageView mMusicIV;
    private SimpleDraweeView mHeadIV;
    private TextView mAuthorTV;
    private TextView mDesTV;
    private TextView mMusicTitleTV;
    private TextView mLyricTV;
    private TextView mInfoTV;
    private TextView mEditorTV;
    private TextView mLikeNumTV;
    private TextView mCommentNumTV;
    private TextView mShareNumTV;
    private ImageView mLyricIV;
    private ImageView mInfoIV;
    private TextView mFooter;


    @Override
    protected void initView() {
        mAdapter = new CommentAdapter();
        mListView = (ListView) findViewById(R.id.swipe_target);
        mSwipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        View mHeader = LayoutInflater.from(this).inflate(R.layout.music_relate_header, null);

        mPlayIV = (ImageView) mHeader.findViewById(R.id.play_iv);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (mHasMore && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() == view.getCount() - 1 &&
                        !ViewCompat.canScrollVertically(view, 1)) {
                        mSwipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }


            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        //        mPlayIV.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                if (mPlayView != null && v != mPlayView) {
        //                    mPlayView.setImageResource(R.drawable.music_play_selector);
        //                }
        //                mPlayIndex = position;
        //                mPlayView = (ImageView) v;
        //                ManagedMediaPlayer.Status s = MyPlayer.getPlayer().getSourceStatus(music.getMusic_id());
        //                if (s == ManagedMediaPlayer.Status.IDLE || s == ManagedMediaPlayer.Status.STOPPED) {
        //                    PlayEvent e = new PlayEvent();
        //                    e.setSong(new Song(music.getTitle(), music.getMusic_id()));
        //                    e.setAction(PlayEvent.Action.PLAYITEM);
        //                    EventBus.getDefault().post(e);
        //                    Log.e("test---", "点击播放");
        //                } else if (s == ManagedMediaPlayer.Status.PAUSED) {
        //                    PlayEvent e = new PlayEvent();
        //                    e.setAction(PlayEvent.Action.RESUME);
        //                    EventBus.getDefault().post(e);
        //                    Log.e("test---", "点击恢复");
        //                } else if (s == ManagedMediaPlayer.Status.STARTED) {
        //                    PlayEvent e = new PlayEvent();
        //                    e.setAction(PlayEvent.Action.PAUSE);
        //                    EventBus.getDefault().post(e);
        //                    Log.e("test---", "点击暂停");
        //                }
        //            }
        //        });
        mMusicIV = (ImageView) mHeader.findViewById(R.id.music_iv);
        //        FrescoHelper.loadImage(mMusicIV, music.getCover());
        //            Picasso.with(mContext).load(music.getCover()).fit().into(mMusicIV);
        mHeadIV = (SimpleDraweeView) mHeader.findViewById(R.id.head_iv);
        //            Picasso.with(mContext).load(music.getAuthor().getWeb_url()).into(mHeadIV);
        //        FrescoHelper.loadImage(mHeadIV, music.getAuthor().getWeb_url());
        mAuthorTV = (TextView) mHeader.findViewById(R.id.name_tv);
        //        mAuthorTV.setText(music.getAuthor().getUser_name());
        mDesTV = (TextView) mHeader.findViewById(R.id.des_tv);
        //        mDesTV.setText(music.getAuthor().getDesc());
        mMusicTitleTV = (TextView) mHeader.findViewById(R.id.music_title_tv);
        //        mMusicTitleTV.setText(music.getTitle());
        mLyricTV = (TextView) mHeader.findViewById(R.id.lyric_tv);
        //        mLyricTV.setText(music.getLyric());
        mInfoTV = (TextView) mHeader.findViewById(R.id.info_tv);
        //        mInfoTV.setText(music.getInfo());
        mEditorTV = (TextView) mHeader.findViewById(R.id.editor_tv);
        //        mEditorTV.setText(music.getCharge_edt());
        mLikeNumTV = (TextView) mHeader.findViewById(R.id.like_num_tv);
        //        mLikeNumTV.setText(music.getPraisenum() + "");
        mCommentNumTV = (TextView) mHeader.findViewById(R.id.comment_num_tv);
        //        mCommentNumTV.setText(music.getCommentnum() + "");
        mShareNumTV = (TextView) mHeader.findViewById(R.id.share_num_tv);
        //        mShareNumTV.setText(music.getSharenum() + "");
        //        List<MusicRelate> musicRelates = mRelateLists.get(position).getMusics();
        //        List<Comment> comments = mRelateLists.get(position).getHotComment();
        //        if (musicRelates != null && musicRelates.size() > 0) {
        //            RecyclerView r = (RecyclerView) mHeader.findViewById(R.id.relate_rv);
        //            MusicRelateAdapter a = new MusicRelateAdapter(musicRelates);
        //            r.setAdapter(a);
        //            LinearLayoutManager m = new LinearLayoutManager(container.getContext());
        //            m.setOrientation(LinearLayoutManager.HORIZONTAL);
        //            r.setLayoutManager(m);
        //        } else {
        //            View v = mHeader.findViewById(R.id.relate_ll);
        //            v.setVisibility(View.GONE);
        //        }
        //        if (comments != null && comments.size() > 0) {
        //            ListViewForScrollView hotCommentsLV = (ListViewForScrollView) mHeader.findViewById(R.id.hot_lv);
        //            CommentAdapter adapter = new CommentAdapter();
        //            hotCommentsLV.setAdapter(adapter);
        //            adapter.refreshComments(comments);
        //        }
        //        if (mLoadMoreListener != null) {
        //            swipeToLoadLayout.setOnLoadMoreListener(new com.aspsine.swipetoloadlayout.OnLoadMoreListener() {
        //                @Override
        //                public void onLoadMore() {
        //                    mLoadMoreListener.loadMore(position, mRelateLists.get(position).getLastIndex());
        //                }
        //            });
        //        }

        //            mAuthorHeaderIV = (CircleImageView) mHeader.findViewById(R.id.author_head_iv);
        //            mHeaderIV = (CircleImageView) mHeader.findViewById(R.id.head_iv);
        //            mAuthorNameTV = (TextView) mHeader.findViewById(R.id.author_name_tv);
        //            mRelateLV = (ListViewForScrollView) mHeader.findViewById(R.id.relate_lv);
        //            mRelateLL = (LinearLayout) mHeader.findViewById(R.id.relate_ll);
        mListView.addHeaderView(mHeader);
        mListView.setAdapter(mAdapter);
        mLyricIV = (ImageView) mHeader.findViewById(R.id.lyric_iv);
        mInfoIV = (ImageView) mHeader.findViewById(R.id.info_iv);
        mSwipeToLoadLayout.setOnLoadMoreListener(this);
        mLyricIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLyricTV.getVisibility() != View.VISIBLE) {
                    mLyricTV.setVisibility(View.VISIBLE);
                    mInfoTV.setVisibility(View.GONE);
                }
            }
        });
        mInfoIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInfoTV.getVisibility() != View.VISIBLE) {
                    mInfoTV.setVisibility(View.VISIBLE);
                    mLyricTV.setVisibility(View.GONE);
                }
            }
        });
        mFooter = (TextView) LayoutInflater.from(this).inflate(R.layout.footer_text, null);

    }


    @Override
    protected int getLayoutID() {
        return R.layout.activity_question;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initLoader() {
        getSupportLoaderManager().initLoader(AppUtil.getID(), null, this);
    }
    @Override
    public Loader<MusicRelatePresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader(this, new PresenterFactory() {
            @Override
            public BasePresenter create() {
                return new MusicRelatePresenter(MusicRelateActivity.this);
            }
        });
    }


    @Override
    public void onLoadFinished(Loader<MusicRelatePresenter> loader, MusicRelatePresenter data) {
        mPresenter = data;
        mPresenter.attachView();
        mPresenter.setContent(mID);
    }


    @Override
    public void onLoaderReset(Loader<MusicRelatePresenter> loader) {

    }


    private boolean mHasMore = true;


    @Override
    public void showList(List<Comment> comments) {
        if (comments.size() == 0) {
            mHasMore = false;
            mListView.addFooterView(mFooter);
            mSwipeToLoadLayout.setLoadingMore(false);
            mSwipeToLoadLayout.setLoadMoreEnabled(false);
        } else {
            mSwipeToLoadLayout.setLoadingMore(false);
            mAdapter.refreshComments(comments);
        }
    }


    @Override
    public void showContent(final Music music) {
        ManagedMediaPlayer.Status s = MyPlayer.getPlayer().getSourceStatus(music.getMusic_id());
        if (s == ManagedMediaPlayer.Status.STARTED) {
            mPlayIV.setImageResource(R.drawable.music_pause_selector);
        } else {
            mPlayIV.setImageResource(R.drawable.music_play_selector);
        }
        mPlayIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManagedMediaPlayer.Status s = MyPlayer.getPlayer()
                    .getSourceStatus(music.getMusic_id());
                if (s == ManagedMediaPlayer.Status.IDLE || s == ManagedMediaPlayer.Status.STOPPED) {
                    PlayEvent e = new PlayEvent();
                    e.setSong(new Song(music.getTitle(), music.getMusic_id()));
                    e.setAction(PlayEvent.Action.PLAYITEM);
                    EventBus.getDefault().post(e);
                    Log.e("test---", "点击播放");
                } else if (s == ManagedMediaPlayer.Status.PAUSED) {
                    PlayEvent e = new PlayEvent();
                    e.setAction(PlayEvent.Action.RESUME);
                    EventBus.getDefault().post(e);
                    Log.e("test---", "点击恢复");
                } else if (s == ManagedMediaPlayer.Status.STARTED) {
                    PlayEvent e = new PlayEvent();
                    e.setAction(PlayEvent.Action.PAUSE);
                    EventBus.getDefault().post(e);
                    Log.e("test---", "点击暂停");
                }
            }
        });
        Picasso.with(this).load(music.getCover()).fit().placeholder(R.drawable.default_music_cover).into(mMusicIV);
        FrescoHelper.loadImage(mHeadIV, music.getAuthor().getWeb_url());
        mAuthorTV.setText(music.getAuthor().getUser_name());
        mDesTV.setText(music.getAuthor().getDesc());
        mMusicTitleTV.setText(music.getTitle());
        mLyricTV.setText(music.getLyric());
        mInfoTV.setText(music.getInfo());
        mEditorTV.setText(music.getCharge_edt());
        mLikeNumTV.setText(String.valueOf(music.getPraisenum()));
        mCommentNumTV.setText(String.valueOf(music.getCommentnum()));
        mShareNumTV.setText(String.valueOf(music.getSharenum()));
    }


    @Override
    public void onLoadMore() {
        mPresenter.showComment();
    }
}
