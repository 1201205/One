package com.hyc.zhihu.base;

import android.graphics.drawable.ColorDrawable;
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
import com.hyc.zhihu.utils.AppUtil;
import com.hyc.zhihu.utils.S;
import com.hyc.zhihu.utils.SPUtil;

/**
 * Created by Administrator on 2016/5/13.
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {
    protected TextView mTitleView;
    protected T mPresenter;
    private int mColor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        mColor = SPUtil.get( S.THEME, AppUtil.getColor(R.color.google_blue));
//        getWindow().setStatusBarColor(mColor);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        if (getIntent() != null) {
            handleIntent();
        }
        initView();
        initActionBar();
        initLoader();

    }

    protected abstract void initLoader();

    protected abstract void handleIntent();

    protected abstract void initView();

    protected abstract int getLayoutID();

    private void initActionBar() {
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setElevation(0);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        View mCustomView = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        mTitleView = (TextView) mCustomView.findViewById(R.id.title);
        String title = getTitleString();
        if (!TextUtils.isEmpty(title)) {
            mTitleView.setText(title);
        }
        mActionBar.setBackgroundDrawable(new ColorDrawable(mColor));
        mActionBar.setCustomView(mCustomView, new
                ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT));
        ActionBar.LayoutParams mP = (ActionBar.LayoutParams) mCustomView.getLayoutParams();
        mP.gravity = mP.gravity & ~Gravity.HORIZONTAL_GRAVITY_MASK | Gravity.CENTER_HORIZONTAL;
        mActionBar.setCustomView(mCustomView, mP);
    }

    protected String getTitleString() {
        return AppUtil.getString(R.string.app_name);
    }

    @Override
    public void showLoading() {
        DelayHandle.delay(0, new Runnable() {
            @Override
            public void run() {
                LoadingDialogFragment.getInstance().show(getSupportFragmentManager(), BaseActivity.this.getClass().getSimpleName());
            }
        });
    }


    @Override
    public void dismissLoading() {
        LoadingDialogFragment.getInstance().dismissAllowingStateLoss();

    }

//    public void changeColor() {
//        mColor = SPUtil.get( S.THEME, AppUtil.getColor(R.color.google_blue));
//        getWindow().setStatusBarColor(mColor);
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(mColor));
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null) {
            mPresenter.detachView();
        }
    }
}
