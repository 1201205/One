package com.hyc.one.ui;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.hyc.one.R;
import com.hyc.one.base.BaseActivity;
import com.hyc.one.base.BasePresenter;
import com.hyc.one.base.PresenterFactory;
import com.hyc.one.base.PresenterLoader;
import com.hyc.one.beans.Comment;
import com.hyc.one.beans.Song;
import com.hyc.one.beans.music.Music;
import com.hyc.one.beans.music.MusicRelate;
import com.hyc.one.beans.music.MusicRelateListBean;
import com.hyc.one.event.PlayEvent;
import com.hyc.one.presenter.MusicPresenter;
import com.hyc.one.ui.adpter.MusicAdapter;
import com.hyc.one.utils.AppUtil;
import com.hyc.one.view.MusicView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
public class MusicActivity extends BaseActivity<MusicPresenter>
        implements MusicView, LoaderManager.LoaderCallbacks<MusicPresenter> {
    private ViewPager mPager;
    private MusicAdapter mAdapter;


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
    protected void initLoader() {
        getSupportLoaderManager().initLoader(AppUtil.getID(), null, this);
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
        mPresenter.attachView();
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
        if (isFinishing()) {
            return;
        }
        mAdapter.setRelate(position, musicRelates);
    }


    @Override
    protected String getTitleString() {
        return AppUtil.getString(R.string.music);
    }


    @Override
    protected void onDestroy() {
        if (mAdapter != null) {
            mAdapter.clear();
        }
        mPager.setAdapter(null);
        Log.e("test1", "ondestory");
        super.onDestroy();
    }


    @Override
    public void setComment(int position, List<Comment> hot, List<Comment> normal) {
        mAdapter.setComment(position, hot, normal);
    }


    @Override
    public void refreshCommentList(int page, List<Comment> comments) {
        if (isFinishing()) {
            return;
        }
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
