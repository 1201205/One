package com.hyc.zhihu.ui;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.hyc.zhihu.R;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Administrator on 2016/5/12.
 */
public class PictureActivity extends AppCompatActivity {
    public static final String EXTRA_IMAGE_URL="extra_image_url";
    private ImageView mDraweeView;
    public static Intent newIntent(Context context, String url) {
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putExtra(PictureActivity.EXTRA_IMAGE_URL, url);
        return intent;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        mDraweeView= (ImageView) findViewById(R.id.picture_sdv);
//        ViewCompat.setTransitionName(mDraweeView,"test");
        ActivityOptions.makeSceneTransitionAnimation(this, mDraweeView, "test");

        Picasso.with(PictureActivity.this).load(getIntent().getStringExtra(EXTRA_IMAGE_URL)).into(mDraweeView);
        PhotoViewAttacher mPhotoViewAttacher = new PhotoViewAttacher(mDraweeView);


    }
}
