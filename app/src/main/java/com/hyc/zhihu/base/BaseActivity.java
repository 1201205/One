package com.hyc.zhihu.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.hyc.zhihu.R;
import com.hyc.zhihu.helper.DelayHandle;
import com.hyc.zhihu.ui.fragment.LoadingDialogFragment;

/**
 * Created by Administrator on 2016/5/13.
 */
public abstract class BaseActivity<T> extends AppCompatActivity {
    protected TextView mTitleView;
    protected T mPresenter;
    protected LoadingDialogFragment mDalog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        if (getIntent()!=null) {
            handleIntent();
        }
        initView();
        initActionBar();

    }

    protected abstract void handleIntent();
    protected abstract void initView();

    protected abstract int getLayoutID();
    public void showLoadingView(){
        DelayHandle.delay(0, new Runnable() {
            @Override public void run() {
                LoadingDialogFragment.getInstance().show(getSupportFragmentManager(),BaseActivity.this.getClass().getSimpleName());
            }
        });
    }
    public void dissmissLoadingView(){
        LoadingDialogFragment.getInstance().dismissAllowingStateLoss();

    }
    private void initActionBar() {
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setElevation(0);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        View mCustomView = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        mTitleView= (TextView) mCustomView.findViewById(R.id.title);
        String title=getTitleString();
        if (!TextUtils.isEmpty(title)) {
            mTitleView.setText(title);
        }
        mActionBar.setCustomView(mCustomView, new
                ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT));
        ActionBar.LayoutParams mP = (ActionBar.LayoutParams) mCustomView.getLayoutParams();
        mP.gravity = mP.gravity & ~Gravity.HORIZONTAL_GRAVITY_MASK | Gravity.CENTER_HORIZONTAL;
        mActionBar.setCustomView(mCustomView, mP);
    }
    protected String getTitleString(){
        return "一个";
    }

}
