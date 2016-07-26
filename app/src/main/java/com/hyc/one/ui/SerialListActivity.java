package com.hyc.one.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.widget.ListView;

import com.hyc.one.R;
import com.hyc.one.base.BaseActivity;
import com.hyc.one.base.BasePresenter;
import com.hyc.one.base.PresenterFactory;
import com.hyc.one.base.PresenterLoader;
import com.hyc.one.beans.SerialListItem;
import com.hyc.one.presenter.SerialListPresenter;
import com.hyc.one.ui.adpter.SerialListAdapter;
import com.hyc.one.utils.AppUtil;
import com.hyc.one.utils.S;
import com.hyc.one.view.SerialListView;

import java.util.List;

/**
 * Created by Administrator on 2016/5/19.
 */
public class SerialListActivity extends BaseActivity<SerialListPresenter>
        implements SerialListView, LoaderManager.LoaderCallbacks<SerialListPresenter> {
    private ListView mListView;
    private String mID;
    SerialListAdapter mAdapter;


    @Override
    protected void initLoader() {
        getSupportLoaderManager().initLoader(AppUtil.getID(), null, this);
    }

    @Override
    protected void handleIntent() {
        mID = getIntent().getStringExtra(S.ID);
    }


    @Override
    protected void initView() {
        mListView = (ListView) findViewById(R.id.list);
        mAdapter = new SerialListAdapter(this);
        mListView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SerialListAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(String id) {
                jumpToSerial(id);
            }
        });

    }


    @Override
    protected int getLayoutID() {
        return R.layout.activity_serial_list;
    }

    @Override
    public Loader<SerialListPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<SerialListPresenter>(this, new PresenterFactory() {
            @Override
            public BasePresenter create() {
                return new SerialListPresenter(SerialListActivity.this);
            }
        });
    }


    @Override
    public void onLoadFinished(Loader<SerialListPresenter> loader, SerialListPresenter data) {
        mPresenter = data;
        mPresenter.attachView();
        mPresenter.getAndShowList(mID);
    }


    @Override
    public void onLoaderReset(Loader<SerialListPresenter> loader) {
        mPresenter = null;
    }


    @Override
    public void showList(List<SerialListItem> datas, String title) {
        mAdapter.refresh(datas);

        mTitleView.setText(title);

    }


    private void jumpToSerial(String id) {
        Intent i = new Intent(this, SerialActivity.class);
        i.putExtra(S.ID, id);
        startActivity(i);
    }

}
