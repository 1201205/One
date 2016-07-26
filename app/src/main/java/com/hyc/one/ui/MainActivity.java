package com.hyc.one.ui;

import android.os.Bundle;

import com.hyc.one.R;
import com.hyc.one.base.BaseActivity;
import com.hyc.one.presenter.MainPresenter;
import com.hyc.one.ui.fragment.NavigationFragment;
import com.hyc.one.ui.fragment.PictureFragment;

public class MainActivity extends BaseActivity<MainPresenter> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initLoader() {
    }


    @Override
    protected void handleIntent() {
    }


    @Override
    protected void initView() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_content, new PictureFragment())
                .commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.navigation_fl, new NavigationFragment())
                .commit();
    }


    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }
}
