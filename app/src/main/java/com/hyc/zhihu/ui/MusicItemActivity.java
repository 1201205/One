package com.hyc.zhihu.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.hyc.zhihu.beans.music.MusicRelate;
import com.hyc.zhihu.event.PlayCallBackEvent;
import com.hyc.zhihu.event.PlayEvent;
import com.hyc.zhihu.helper.FrescoHelper;
import com.hyc.zhihu.player.ManagedMediaPlayer;
import com.hyc.zhihu.player.MyPlayer;
import com.hyc.zhihu.presenter.MusicItemPresenter;
import com.hyc.zhihu.ui.adpter.CommentAdapter;
import com.hyc.zhihu.ui.adpter.MusicRelateAdapter;
import com.hyc.zhihu.ui.fragment.LoadingDialogFragment;
import com.hyc.zhihu.utils.AppUtil;
import com.hyc.zhihu.utils.S;
import com.hyc.zhihu.view.MusicItemView;
import com.hyc.zhihu.widget.ListViewForScrollView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by ray on 16/5/26.
 */
public class MusicItemActivity extends BaseActivity<MusicItemPresenter> implements MusicItemView,
    LoaderManager.LoaderCallbacks<MusicItemPresenter>,
    OnLoadMoreListener {
    private String mID;


    @Override
    protected void handleIntent() {
        mID = getIntent().getStringExtra(S.ID);
    }


    ListView listView;
    SwipeToLoadLayout swipeToLoadLayout;
    private boolean mHasMoreComments = true;
    private View mHeader;
    SimpleDraweeView musicIV;
    SimpleDraweeView headIV;
    TextView mAuthorTV;
    TextView desTV;
    TextView musicTitleTV;
    TextView timeTV;
    TextView titleTV;
    TextView authorNameTV;
    LinearLayout contentLL;
    TextView contentTV;
    TextView lyricTV;
    TextView infoTV;
    TextView editorTV;
    TextView likeNumTV;
    TextView commentNumTV;
    TextView shareNumTV;
    RecyclerView r;
    View v;
    ListViewForScrollView hotCommentsLV;
    private CommentAdapter mAdapter;
    ImageView storyIV;
    ImageView lyricIV;
    ImageView infoIV;
    private TextView mFooter;
    private ImageView playIV;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected void initView() {
        mAdapter = new CommentAdapter();
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
        swipeToLoadLayout.setOnLoadMoreListener(this);
        mHeader = LayoutInflater.from(this).inflate(R.layout.music_header, null);
        musicIV = (SimpleDraweeView) mHeader.findViewById(R.id.music_iv);
        playIV = (ImageView) mHeader.findViewById(R.id.play_iv);

        headIV = (SimpleDraweeView) mHeader.findViewById(R.id.head_iv);
        mAuthorTV = (TextView) mHeader.findViewById(R.id.name_tv);
        desTV = (TextView) mHeader.findViewById(R.id.des_tv);
        musicTitleTV = (TextView) mHeader.findViewById(R.id.music_title_tv);
        timeTV = (TextView) mHeader.findViewById(R.id.time_tv);
        titleTV = (TextView) mHeader.findViewById(R.id.title_tv);
        authorNameTV = (TextView) mHeader.findViewById(R.id.author_name_tv);
        contentLL = (LinearLayout) mHeader.findViewById(R.id.content_ll);
        contentTV = (TextView) mHeader.findViewById(R.id.content_tv);
        lyricTV = (TextView) mHeader.findViewById(R.id.lyric_tv);
        infoTV = (TextView) mHeader.findViewById(R.id.info_tv);
        editorTV = (TextView) mHeader.findViewById(R.id.editor_tv);
        likeNumTV = (TextView) mHeader.findViewById(R.id.like_num_tv);
        commentNumTV = (TextView) mHeader.findViewById(R.id.comment_num_tv);
        shareNumTV = (TextView) mHeader.findViewById(R.id.share_num_tv);
        r = (RecyclerView) mHeader.findViewById(R.id.relate_rv);

        v = mHeader.findViewById(R.id.relate_ll);
        hotCommentsLV = (ListViewForScrollView) mHeader.findViewById(R.id.hot_lv);
        listView.addHeaderView(mHeader);
        listView.setAdapter(mAdapter);
        storyIV = (ImageView) mHeader.findViewById(R.id.story_iv);
        lyricIV = (ImageView) mHeader.findViewById(R.id.lyric_iv);
        infoIV = (ImageView) mHeader.findViewById(R.id.info_iv);
        storyIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contentLL.getVisibility() != View.VISIBLE) {
                    contentLL.setVisibility(View.VISIBLE);
                    lyricTV.setVisibility(View.GONE);
                    infoTV.setVisibility(View.GONE);
                }
            }
        });
        lyricIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lyricTV.getVisibility() != View.VISIBLE) {
                    lyricTV.setVisibility(View.VISIBLE);
                    contentLL.setVisibility(View.GONE);
                    infoTV.setVisibility(View.GONE);
                }
            }
        });
        infoIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (infoTV.getVisibility() != View.VISIBLE) {
                    infoTV.setVisibility(View.VISIBLE);
                    lyricTV.setVisibility(View.GONE);
                    contentLL.setVisibility(View.GONE);
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
    public Loader<MusicItemPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<MusicItemPresenter>(this, new PresenterFactory() {
            @Override
            public BasePresenter create() {
                return new MusicItemPresenter(MusicItemActivity.this);
            }
        });
    }


    @Override
    public void onLoadFinished(Loader<MusicItemPresenter> loader, MusicItemPresenter data) {
        mPresenter = data;
        mPresenter.getAndShowContent(mID);
    }


    @Override
    public void onLoaderReset(Loader<MusicItemPresenter> loader) {
        mPresenter = null;
    }


    @Override
    public void refreshComment(List<Comment> comments) {
        if (comments == null || comments.size() == 0) {
            mHasMoreComments = false;
            listView.addFooterView(mFooter);
            swipeToLoadLayout.setLoadingMore(false);
            swipeToLoadLayout.setLoadMoreEnabled(false);
        } else {
            mAdapter.refreshComments(comments);
            swipeToLoadLayout.setLoadingMore(false);
        }
    }


    @Override
    public void showHotComment(List<Comment> comments) {
        CommentAdapter adapter = new CommentAdapter();
        hotCommentsLV.setAdapter(adapter);
        adapter.refreshComments(comments);
    }


    @Override
    public void showRelate(List<MusicRelate> musicRelates) {
        if (musicRelates == null || musicRelates.size() == 0) {
            v.setVisibility(View.GONE);
        } else {
            MusicRelateAdapter a = new MusicRelateAdapter(musicRelates);
            r.setAdapter(a);
            LinearLayoutManager m = new LinearLayoutManager(this);
            m.setOrientation(LinearLayoutManager.HORIZONTAL);
            r.setLayoutManager(m);
        }
    }


    @Override
    public void showContent(final Music music) {
        ManagedMediaPlayer.Status s = MyPlayer.getPlayer().getSourceStatus(music.getMusic_id());
        if (s == ManagedMediaPlayer.Status.STARTED) {
            playIV.setImageResource(R.drawable.music_pause_selector);
        } else {
            playIV.setImageResource(R.drawable.music_play_selector);
        }
        FrescoHelper.loadImage(musicIV, music.getCover());
        FrescoHelper.loadImage(headIV, music.getAuthor().getWeb_url());
        mAuthorTV.setText(music.getAuthor().getUser_name());
        desTV.setText(music.getAuthor().getDesc());
        musicTitleTV.setText(music.getTitle());
        timeTV.setText("May 23.2016");
        titleTV.setText(music.getStory_title());
        authorNameTV.setText(music.getStory_author().getUser_name());
        contentTV.setText(Html.fromHtml(music.getStory()));
        lyricTV.setText(music.getLyric());
        infoTV.setText(music.getInfo());
        editorTV.setText(music.getCharge_edt());
        likeNumTV.setText(music.getPraisenum() + "");
        commentNumTV.setText(music.getCommentnum() + "");
        shareNumTV.setText(music.getSharenum() + "");
        playIV.setOnClickListener(new View.OnClickListener() {
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
    }


    @Subscribe
    public void onEvent(PlayCallBackEvent playEvent) {
        switch (playEvent.getState()) {
            case STARTED:
                playIV.setImageResource(R.drawable.music_pause_selector);
                break;
            case STOPPED:
            case PAUSED:
                playIV.setImageResource(R.drawable.music_play_selector);
                break;
            default:
                break;

        }
    }

    @Override
    public void onLoadMore() {
        mPresenter.refreshComments();
    }
    @Override
    protected void initLoader() {
        getSupportLoaderManager().initLoader(AppUtil.getID(), null, this);
    }
}
