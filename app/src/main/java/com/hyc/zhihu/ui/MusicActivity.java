package com.hyc.zhihu.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.hyc.zhihu.R;
import com.hyc.zhihu.base.BaseActivity;
import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.base.PresenterFactory;
import com.hyc.zhihu.base.PresenterLoader;
import com.hyc.zhihu.beans.Comment;
import com.hyc.zhihu.beans.Song;
import com.hyc.zhihu.beans.music.Music;
import com.hyc.zhihu.beans.music.MusicRelate;
import com.hyc.zhihu.beans.music.MusicRelateListBean;
import com.hyc.zhihu.event.PlayEvent;
import com.hyc.zhihu.helper.DelayHandle;
import com.hyc.zhihu.presenter.MusicPresenter;
import com.hyc.zhihu.ui.adpter.MusicAdapter;
import com.hyc.zhihu.view.MusicView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
public class MusicActivity extends BaseActivity implements MusicView, LoaderManager.LoaderCallbacks<MusicPresenter> {
    private ViewPager mPager;
    private MusicPresenter mPresenter;
    private MusicAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportLoaderManager().initLoader(126, null, this);
    }

    @Override
    protected void handleIntent() {

    }
    @Override
    protected void initView() {
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                if (position == mAdapter.getCount() - 1) {
                    return;
                }
                if (mAdapter.neeRequest(position)) {
                    mPresenter.showCurrentCommentAndRelate(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.picture_fragment;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public Loader<MusicPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<MusicPresenter>(this, new PresenterFactory() {
            @Override
            public BasePresenter create() {
                return new MusicPresenter(MusicActivity.this);
            }
        });
    }

    @Override
    public void onLoadFinished(Loader<MusicPresenter> loader, MusicPresenter data) {
        mPresenter = data;
        mPresenter.getAndShowContent();
    }

    @Override
    public void onLoaderReset(Loader<MusicPresenter> loader) {
        mPresenter = null;
    }

    @Override
    public void showMusic(String id, Music data) {

    }

    @Override
    public void jumpToDate() {

    }

    @Override
    public void showNetWorkError() {

    }

    @Override
    public void setAdapter(List<Music> beans, List<MusicRelateListBean> listBeans) {
        mAdapter = new MusicAdapter(listBeans, beans);
        mPager.setAdapter(mAdapter);
        mAdapter.setLoadMoreListener(new MusicAdapter.OnLoadMoreListener() {
            @Override
            public void loadMore(int page, String lastIndex) {
                mPresenter.showRefreshComment(page, lastIndex);
            }
        });
    }

    @Override
    public void setRelate(int position, List<MusicRelate> musicRelates) {
        mAdapter.setRelate(position, musicRelates);
    }

    @Override
    protected void onDestroy() {
        mAdapter.clear();
        mPager.setAdapter(null);
        Log.e("test1","ondestory");
        super.onDestroy();
    }

    @Override
    public void setComment(int position, List<Comment> hot, List<Comment> normal) {
        mAdapter.setComment(position, hot, normal);
    }

    @Override
    public void refreshCommentList(int page, List<Comment> comments) {
        mAdapter.refreshComment(page, comments);
    }

    @Override
    public void setSongList(List<Song> songs) {
        PlayEvent playEvent = new PlayEvent();
        playEvent.setAction(PlayEvent.Action.ADDLIST);
        playEvent.setQueue(songs);
        EventBus.getDefault().post(playEvent);
    }
}
