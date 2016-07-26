package com.hyc.one.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyc.one.R;
import com.hyc.one.base.BaseActivity;
import com.hyc.one.beans.OnePictureData;
import com.hyc.one.utils.DateUtil;
import com.hyc.one.view.PictureDetailView;
import com.squareup.picasso.Picasso;

/**
 * Created by ray on 16/5/15.
 */
public class PictureDetailActivity extends BaseActivity implements PictureDetailView {
    public static String PICTURE_DATA = "picture_data";
    private OnePictureData mData;
    private ImageView mPictureIV;
    private TextView mVolTV;
    private TextView mNameTV;
    private TextView mContentTV;
    private TextView mDateTV;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void handleIntent() {
        mData = (OnePictureData) getIntent().getSerializableExtra(PICTURE_DATA);
    }

    @Override
    protected void initView() {
        mPictureIV = (ImageView) findViewById(R.id.picture_sdv);
        ViewCompat.setTransitionName(mPictureIV, "test1");
        Picasso.with(this).load(getIntent().getStringExtra("test2")).fit().into(mPictureIV);
        mVolTV = (TextView) findViewById(R.id.vol_tv);
        mNameTV = (TextView) findViewById(R.id.name_tv);
        mContentTV = (TextView) findViewById(R.id.main_tv);
        mDateTV = (TextView) findViewById(R.id.date_tv);
        showDetail();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.picture_adpter_item;
    }

    @Override
    protected String getTitleString() {
        return mData.getHp_title();
    }

    @Override
    public void showDetail() {
        mNameTV.setText(mData.getHp_author());
        mVolTV.setText(mData.getHp_title());
        mContentTV.setText(mData.getHp_content());
        mDateTV.setText(DateUtil.getOneDate(mData.getLast_update_date()));
    }

    @Override
    protected void initLoader() {
    }
}
