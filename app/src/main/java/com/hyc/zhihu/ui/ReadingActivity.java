package com.hyc.zhihu.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.hyc.zhihu.R;
import com.hyc.zhihu.base.BaseActivity;
import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.base.PresenterFactory;
import com.hyc.zhihu.base.PresenterLoader;
import com.hyc.zhihu.beans.HeadScrollItem;
import com.hyc.zhihu.beans.RealReading;
import com.hyc.zhihu.presenter.ReadingPresenter;
import com.hyc.zhihu.ui.adpter.ReadingAdapter;
import com.hyc.zhihu.view.ReadingView;

import java.util.List;

/**
 * Created by Administrator on 2016/5/16.
 */
public class ReadingActivity extends BaseActivity implements ReadingView,LoaderManager.LoaderCallbacks<ReadingPresenter>{
    private ListView mListView;
    private ReadingAdapter mReadingAdapter;
    View v;
    private ReadingPresenter mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListView = (ListView) findViewById(R.id.picture_lv);
        getSupportLoaderManager().initLoader(121,null, this);
        v=LayoutInflater.from(this).inflate(R.layout.reading_header,null);
        mListView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.reading_header,null));
        mListView.getHeaderViewsCount();

        Log.e("test", mListView.getHeaderViewsCount()+"");
    }

    @Override
    protected void handleIntent() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_reading;
    }

    @Override
    public void showHead(List<HeadScrollItem> headScrollItems) {

    }

    @Override
    public void showList(List<RealReading> realReadings, List<String> date) {
        if (mReadingAdapter == null) {
            mReadingAdapter = new ReadingAdapter(this, realReadings, date);
            mListView.setAdapter(mReadingAdapter);
        } else {
            mReadingAdapter.refreshList(realReadings,date);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public Loader<ReadingPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<ReadingPresenter>(this, new PresenterFactory() {
            @Override
            public BasePresenter create() {
                return new ReadingPresenter(ReadingActivity.this);
            }
        });
    }

    @Override
    public void onLoadFinished(Loader<ReadingPresenter> loader, ReadingPresenter data) {
        mPresenter=data;
        mPresenter.showContent();
    }

    @Override
    public void onLoaderReset(Loader<ReadingPresenter> loader) {
        mPresenter=null;
    }
}
