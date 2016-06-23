package com.hyc.zhihu.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyc.zhihu.R;
import com.hyc.zhihu.base.BaseActivity;
import com.hyc.zhihu.beans.OnePictureData;
import com.hyc.zhihu.utils.AppUtil;
import com.hyc.zhihu.view.PictureDetailView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

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
//        showDetail();
    }

    @Override
    protected void initView() {
        mPictureIV = (ImageView) findViewById(R.id.picture_sdv);
        ViewCompat.setTransitionName(mPictureIV,"test1");
        Picasso.with(this).load(getIntent().getStringExtra("test2")).fit().into(mPictureIV);
//        mVolTV = (TextView) findViewById(R.id.vol_tv);
//        mNameTV = (TextView) findViewById(R.id.name_tv);
//        mContentTV = (TextView) findViewById(R.id.main_tv);
//        mDateTV = (TextView) findViewById(R.id.date_tv);

//        postponeEnterTransition();
//        setEnterSharedElementCallback(mCallback);


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
        mDateTV.setText(mData.getLast_update_date());
    }

    @Override
    protected void initLoader() {
    }
//    private final SharedElementCallback mCallback = new SharedElementCallback() {
//        @Override
//        public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
//       names.add("test1");
//            sharedElements.put("test1", mPictureIV);
//
//        }
//    };
}
