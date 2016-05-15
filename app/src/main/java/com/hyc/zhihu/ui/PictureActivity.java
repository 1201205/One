package com.hyc.zhihu.ui;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyc.zhihu.R;
import com.hyc.zhihu.base.BaseActivity;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Administrator on 2016/5/12.
 */
public class PictureActivity extends BaseActivity {
    public static final String EXTRA_IMAGE_URL="extra_image_url";
    public static final String SHARE_PICTURE="share_picture";
    public static final String SHARE_TITLE="share_title";
    public static final String TITLE_STRING="title_string";
    private ImageView mDraweeView;
    private String mTitle;
    private String mUrl;
    public static Intent newIntent(Context context, String url,String title) {
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putExtra(PictureActivity.EXTRA_IMAGE_URL, url);
        intent.putExtra(PictureActivity.TITLE_STRING, title);
        return intent;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDraweeView= (ImageView) findViewById(R.id.picture_sdv);
        ViewCompat.setTransitionName(mDraweeView,SHARE_PICTURE);
//        ActivityOptionsCompat.makeSceneTransitionAnimation(this,new Pair( mTitleView, SHARE_TITLE),new Pair( mDraweeView, SHARE_PICTURE));
        mTitleView.setText(mTitle);
        Picasso.with(PictureActivity.this).load(mUrl).into(mDraweeView);
        PhotoViewAttacher mPhotoViewAttacher = new PhotoViewAttacher(mDraweeView);
    }

    @Override
    protected void handleIntent() {
        Intent intent=getIntent();
        mTitle=intent.getStringExtra(TITLE_STRING);
        mUrl=intent.getStringExtra(EXTRA_IMAGE_URL);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_picture;
    }

    @Override
    protected String getTitleString() {
        return super.getTitleString();
    }
}
