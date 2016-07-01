package com.hyc.zhihu.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyc.zhihu.R;
import com.hyc.zhihu.base.BaseActivity;
import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.base.PresenterFactory;
import com.hyc.zhihu.base.PresenterLoader;
import com.hyc.zhihu.beans.OnePictureData;
import com.hyc.zhihu.beans.PictureViewBean;
import com.hyc.zhihu.presenter.PicturePresenterImp;
import com.hyc.zhihu.ui.adpter.PictureAdapter;
import com.hyc.zhihu.view.PictureView;

import java.util.List;

/**
 * Created by ray on 16/5/5.
 */
public class PictureFragment extends Fragment implements PictureView, LoaderManager.LoaderCallbacks<PicturePresenterImp> {
    private PicturePresenterImp mPresenter;
    private ViewPager mViewPager;
    private PictureAdapter mPictureAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(111111, null, this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.picture_fragment, null);
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPictureAdapter.setCurrentPage(position);
                mPresenter.gotoPosition(position);
                Log.e("test1", "选中位置--" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("test10", Thread.currentThread().getName());
//        mPresenter.getPictureIdsAndFirstItem();
//        mViewPager.setOffscreenPageLimit(3);

    }

    @Override
    public void showPicture(final String id, final OnePictureData data) {
        mViewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPictureAdapter.setCurrentItem(id, data);
            }
        }, 0);

    }

    @Override
    public void jumpToDate() {

    }

    @Override
    public void showNetWorkError() {
        //Toast.makeText(getActivity(), "网络错误，请检查", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setAdapter(List<PictureViewBean> beans) {
        mPictureAdapter = new PictureAdapter(beans, getActivity());

        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                mViewPager.setAdapter(mPictureAdapter);

            }
        });
    }

    @Override
    public void showLoading() {
        ((BaseActivity) getActivity()).showLoading();
    }

    @Override
    public void dismissLoading() {
        ((BaseActivity) getActivity()).dismissLoading();

    }

    @Override
    public Loader<PicturePresenterImp> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader(getContext(), new PresenterFactory() {
            @Override
            public BasePresenter create() {
                return new PicturePresenterImp(PictureFragment.this);
            }
        });
    }

    @Override
    public void onLoadFinished(Loader<PicturePresenterImp> loader, PicturePresenterImp data) {
        mPresenter = data;
        mPresenter.attachView();
        mPresenter.getPictureIdsAndFirstItem();
    }

    @Override
    public void onLoaderReset(Loader<PicturePresenterImp> loader) {
        mPresenter = null;
    }
}
