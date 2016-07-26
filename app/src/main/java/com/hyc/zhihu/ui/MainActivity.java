package com.hyc.zhihu.ui;

import android.os.Bundle;

import com.hyc.zhihu.R;
import com.hyc.zhihu.base.BaseActivity;
import com.hyc.zhihu.presenter.MainPresenter;
import com.hyc.zhihu.ui.fragment.NavigationFragment;
import com.hyc.zhihu.ui.fragment.PictureFragment;

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
