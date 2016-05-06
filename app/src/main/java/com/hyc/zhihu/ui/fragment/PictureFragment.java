package com.hyc.zhihu.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.hyc.zhihu.beans.OnePictureData;
import com.hyc.zhihu.beans.PictureViewBean;
import com.hyc.zhihu.presenter.PicturePresenterImp;
import com.hyc.zhihu.presenter.base.PicturePresenter;
import com.hyc.zhihu.view.PictureView;

import java.util.List;

/**
 * Created by ray on 16/5/5.
 */
public class PictureFragment extends Fragment implements PictureView,LoaderManager.LoaderCallbacks<PicturePresenter> {
    private PicturePresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(111111, null, this);
    }


    @Override
    public void showPicture(String id, OnePictureData data) {

    }

    @Override
    public void jumpToDate() {

    }

    @Override
    public void showNetWorkError() {

    }

    @Override
    public void setAdapter(List<PictureViewBean> beans) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public Loader<PicturePresenter> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<PicturePresenter> loader, PicturePresenter data) {

    }

    @Override
    public void onLoaderReset(Loader<PicturePresenter> loader) {

    }
}
