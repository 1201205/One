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
import com.hyc.zhihu.widget.CircleImageView;
import com.squareup.picasso.Picasso;

/**
 * Created by ray on 16/7/10.
 */
public class OtherDetailActivity extends BaseActivity<OtherDetailPresenter>
    implements OtherDetailView, LoaderManager.LoaderCallbacks<OtherDetailPresenter> {

    private String mID;
    private CircleImageView mHeadCV;
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


    public static void jumpTo(Context context, String id) {
        Intent intent = new Intent(context, OtherDetailActivity.class);
        intent.putExtra(S.ID, id);
        context.startActivity(intent);
    }


    @Override
    protected void initLoader() {
        getSupportLoaderManager().initLoader(AppUtil.getID(), null, this);
    }


    @Override
    protected void handleIntent() {
        mID = getIntent().getStringExtra(S.ID);
    }


    @Override
    protected void initView() {
        mHeadCV = (CircleImageView) findViewById(R.id.head_cv);
        mNameTV = (TextView) findViewById(R.id.name_tv);
        mDesTV = (TextView) findViewById(R.id.des_tv);
        mDesTV.setText(null);
        mDairyLL = (LinearLayout) findViewById(R.id.dairy_ll);
        mMusicLL = (LinearLayout) findViewById(R.id.music_ll);
        mDairyIV = (ImageView) findViewById(R.id.dairy_iv);
        mMusicIV = (ImageView) findViewById(R.id.music_iv);
        mArticle = findViewById(R.id.article_rl);
        mPicture = findViewById(R.id.picture_rl);
        mMovie = findViewById(R.id.movie_rl);
        mBackgroundIV = (ImageView) findViewById(R.id.background_iv);

        mPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtil.startActivityWithID(mID, OtherDetailActivity.this,
                    OtherPictureActivity.class);
            }
        });
        mMusicLL.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                AppUtil.startActivityWithIDAndType(mID,S.MUSIC, OtherDetailActivity.this,
                    OtherMusicActivity.class);
            }
        });
        mDairyLL.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                AppUtil.startActivityWithIDAndType(mID,S.DAIRY, OtherDetailActivity.this,
                        OtherMusicActivity.class);
            }
        });
        mMovie.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                AppUtil.startActivityWithIDAndType(mID,S.MOVIE, OtherDetailActivity.this,
                        OtherMusicActivity.class);
            }
        });
    }


    @Override
    protected int getLayoutID() {
        return R.layout.activity_other;
    }


    @Override
    public Loader<OtherDetailPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<OtherDetailPresenter>(this, new PresenterFactory() {
            @Override
            public BasePresenter create() {
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


    @Override
    public void onLoaderReset(Loader<OtherDetailPresenter> loader) {
        loader = null;
    }


    @Override
    public void showContent(Other other) {
        if (!TextUtils.isEmpty(other.getWeb_url())) {
            Picasso.with(this).load(other.getWeb_url()).into(mHeadCV);
        }
        if (!TextUtils.isEmpty(other.getBackground())) {
            Picasso.with(this).load(other.getBackground()).fit().into(mBackgroundIV);
        } else {
            Picasso.with(this).load(R.drawable.default_indi_bg).fit().into(mBackgroundIV);

        }
        mDesTV.setText(other.getDesc());
        mNameTV.setText(other.getUser_name());
        if (other.getIsauthor() == 1) {
            mArticle.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void showDairyAndMusic(OtherCenter otherCenter) {
        if (!TextUtils.isEmpty(otherCenter.getDiary())) {
            mDairyLL.setVisibility(View.VISIBLE);
            Picasso.with(this).load(otherCenter.getDiary()).fit().into(mDairyIV);
        }
        if (!TextUtils.isEmpty(otherCenter.getMusic())) {
            mMusicLL.setVisibility(View.VISIBLE);
            Picasso.with(this).load(otherCenter.getMusic()).fit().into(mMusicIV);
        }
    }


    @Override
    public void onNothingGet() {
        AppUtil.showToast("没有获取到数据哦。。。");
    }
}
