package com.hyc.zhihu.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hyc.zhihu.R;
import com.hyc.zhihu.base.BaseActivity;
import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.base.PresenterFactory;
import com.hyc.zhihu.base.PresenterLoader;
import com.hyc.zhihu.beans.HeadScrollItem;
import com.hyc.zhihu.beans.ReadingListItem;
import com.hyc.zhihu.presenter.ReadingListPresenter;
import com.hyc.zhihu.ui.adpter.ReadingListAdapter;
import com.hyc.zhihu.ui.fragment.LoadingDialogFragment;
import com.hyc.zhihu.utils.S;
import com.hyc.zhihu.view.ReadingListView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/5/18.
 */
public class ReadingListActivity extends BaseActivity<ReadingListPresenter> implements ReadingListView, LoaderManager.LoaderCallbacks<ReadingListPresenter> {
    public static final String HEAD_ITEM = "head_item";
    public static final int LOADER_ID = 1003;
    private HeadScrollItem mItem;
    private ListView mReadingLV;
    private TextView mDesTV;
    private ImageView mPicIV;
    private int mColor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    protected void handleIntent() {
        mItem = (HeadScrollItem) getIntent().getSerializableExtra(HEAD_ITEM);
    }

    @Override
    protected void initView() {
        mReadingLV = (ListView) findViewById(R.id.reading_lv);
        View footer = LayoutInflater.from(this).inflate(R.layout.layout_readinglist_footer, null);
        mPicIV = (ImageView) footer.findViewById(R.id.pic_iv);
        mDesTV = (TextView) footer.findViewById(R.id.des_tv);
        mColor = Color.parseColor(mItem.getBgcolor());
        mReadingLV.setBackgroundColor(mColor);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(mColor));
        Picasso.with(this).load(mItem.getCover()).fit().into(mPicIV);
        mDesTV.setText(mItem.getBottom_text());
        mReadingLV.addFooterView(footer);

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_reading_list;
    }

    @Override
    protected String getTitleString() {
        return mItem.getTitle();
    }

    @Override
    public void showBottom() {

    }

    @Override
    public void showList(List<ReadingListItem> readingListItems) {
        ReadingListAdapter adapter = new ReadingListAdapter(this, readingListItems);
        mReadingLV.setAdapter(adapter);
        adapter.setOnItemClickListener(new ReadingListAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(ReadingListItem item) {
                jumpToContent(item);
            }


        });
    }

    private void jumpToContent(ReadingListItem item) {
        switch (item.getType()){
            case "1":
                Intent essay=new Intent(this,EssayActivity.class);
                essay.putExtra(EssayActivity.ID,item.getItem_id());
                startActivity(essay);
                break;
            case "2":
                Intent serial=new Intent(this,SerialActivity.class);
                serial.putExtra(SerialActivity.ID,item.getItem_id());
                startActivity(serial);
                break;
            case "3":
                Intent question=new Intent(this,QuestionActivity.class);
                question.putExtra(QuestionActivity.ID,item.getItem_id());
                startActivity(question);
                break;
        }
    }


    @Override
    public void showLoading() {
        LoadingDialogFragment.getInstance().startLoading(getSupportFragmentManager());
    }

    @Override
    public void dismissLoading() {
        LoadingDialogFragment.getInstance().dismiss();

    }

    @Override
    public Loader<ReadingListPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<ReadingListPresenter>(this, new PresenterFactory() {
            @Override
            public BasePresenter create() {
                return new ReadingListPresenter(ReadingListActivity.this);
            }
        });
    }

    @Override
    public void onLoadFinished(Loader<ReadingListPresenter> loader, ReadingListPresenter data) {
        mPresenter = data;
        mPresenter.getAndShowEssayList(mItem.getId());
    }

    @Override
    public void onLoaderReset(Loader<ReadingListPresenter> loader) {
        mPresenter = null;
    }
}
