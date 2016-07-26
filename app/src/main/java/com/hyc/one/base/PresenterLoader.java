package com.hyc.one.base;

import android.content.Context;
import android.support.v4.content.Loader;
import android.util.Log;

/**
 * Created by ray on 16/4/19.
 */
public class PresenterLoader<T extends BasePresenter> extends Loader<T> {
    private PresenterFactory<T> mFactory;
    private T mPresenter;

    public PresenterLoader(Context context) {
        super(context);
    }

    public PresenterLoader(Context context, PresenterFactory factory) {
        super(context);
        this.mFactory = factory;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.d("loader-hyc", "onStartLoading");
        if (mPresenter != null) {
            deliverResult(mPresenter);
        } else {
            forceLoad();
        }
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        mPresenter = mFactory.create();
        deliverResult(mPresenter);
        Log.d("loader-hyc", "onForceLoad");
    }

    @Override
    protected void onReset() {
        super.onReset();
        mPresenter = null;
        Log.d("loader-hyc", "onReset");
    }
}
