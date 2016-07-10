package com.hyc.zhihu.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hyc.zhihu.R;
import com.hyc.zhihu.base.BaseActivity;
import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.base.PresenterFactory;
import com.hyc.zhihu.base.PresenterLoader;
import com.hyc.zhihu.beans.Other;
import com.hyc.zhihu.beans.OtherCenter;
import com.hyc.zhihu.presenter.OtherDetailPresenter;
import com.hyc.zhihu.utils.AppUtil;
import com.hyc.zhihu.utils.S;
import com.hyc.zhihu.view.OtherDetailView;
import com.squareup.picasso.Picasso;

/**
 * Created by ray on 16/7/10.
 */
public class OtherDetailActivity extends BaseActivity<OtherDetailPresenter>
    implements OtherDetailView, LoaderManager.LoaderCallbacks<OtherDetailPresenter> {

    private String mID;
    private ImageView mHeadIV;
    private TextView mNameTV;
    private TextView mDesTV;
    private LinearLayout mDairyLL;
    private LinearLayout mMusicLL;
    private ImageView mDairyIV;
    private ImageView mMusicIV;
    private View mArticle;
    private View mPicture;
    private View mReading;
    private View mMovie;
    private ImageView mBackgroundIV;


    public static void jumpTo(Context context,String id){
        Intent intent=new Intent(context,OtherDetailActivity.class);
        intent.putExtra(S.ID,id);
        context.startActivity(intent);
    }

    @Override protected void initLoader() {
        getSupportLoaderManager().initLoader(AppUtil.getID(), null, this);
    }


    @Override protected void handleIntent() {
        mID = getIntent().getStringExtra(S.ID);
    }


    @Override protected void initView() {
        mHeadIV = (ImageView) findViewById(R.id.head_iv);
        mNameTV = (TextView) findViewById(R.id.name_tv);
        mDesTV = (TextView) findViewById(R.id.des_tv);
        mDairyLL = (LinearLayout) findViewById(R.id.dairy_ll);
        mMusicLL = (LinearLayout) findViewById(R.id.music_ll);
        mDairyIV = (ImageView) findViewById(R.id.dairy_iv);
        mMusicIV = (ImageView) findViewById(R.id.music_iv);
        mArticle = findViewById(R.id.article_rl);
        mPicture = findViewById(R.id.picture_rl);
        mMovie = findViewById(R.id.movie_rl);
        mBackgroundIV = (ImageView) findViewById(R.id.background_iv);
    }


    @Override protected int getLayoutID() {
        return R.layout.activity_other;
    }


    @Override public Loader<OtherDetailPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<OtherDetailPresenter>(this, new PresenterFactory() {
            @Override public BasePresenter create() {
                return new OtherDetailPresenter(OtherDetailActivity.this);
            }
        });
    }


    @Override
    public void onLoadFinished(Loader<OtherDetailPresenter> loader, OtherDetailPresenter data) {
        mPresenter = data;
        mPresenter.attachView();
        mPresenter.getAndShowContent(mID);
    }


    @Override public void onLoaderReset(Loader<OtherDetailPresenter> loader) {
        loader = null;
    }


    @Override public void showContent(Other other) {
        if (!TextUtils.isEmpty(other.getWeb_url())) {
            Picasso.with(this).load(other.getWeb_url()).into(mHeadIV);
        }
        if (!TextUtils.isEmpty(other.getBackground())) {
            Picasso.with(this).load(other.getWeb_url()).into(mBackgroundIV);
        }
        mDesTV.setText(other.getDesc());
        mNameTV.setText(other.getUser_name());
        if (other.getIsauthor() == 1) {
            mArticle.setVisibility(View.VISIBLE);
        }
    }


    @Override public void showDairyAndMusic(OtherCenter otherCenter) {
        if (otherCenter.getHas_essay() == 1) {
            mDairyLL.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(otherCenter.getDiary())) {
                Picasso.with(this).load(otherCenter.getDiary()).into(mDairyIV);
            }
        }
        if (otherCenter.getHas_music() == 1) {
            mMusicLL.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(otherCenter.getMusic())) {
                Picasso.with(this).load(otherCenter.getMusic()).into(mMusicIV);
            }
        }
    }


    @Override public void onNothingGet() {

    }
}
