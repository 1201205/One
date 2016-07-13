package com.hyc.zhihu.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hyc.zhihu.R;
import com.hyc.zhihu.base.BaseRecyclerAdapter;
import com.hyc.zhihu.ui.adpter.AdapterFactory;
import com.hyc.zhihu.utils.AppUtil;
import com.hyc.zhihu.utils.S;

import java.util.List;

/**
 * Created by Administrator on 2016/7/13.
 */
public class ListFragment extends Fragment implements OnLoadMoreListener {
    private static final int PAGE_COUNT = 20;
    private RecyclerView mRecyclerView;
    private SwipeToLoadLayout mSwipeToLoadLayout;
    private String mType;
    private BaseRecyclerAdapter mAdapter;
    private ImageView mNoItemIV;
    private boolean mCanLoad = true;
    private LinearLayoutManager mLayoutManager;

    public void setListener(LoadMoreListener mListener) {
        this.mListener = mListener;
    }
    public static ListFragment getInstance(String type){
        ListFragment fragment=new ListFragment();
        Bundle bundle=new Bundle();
        bundle.putString(S.TYPE,type);
        fragment.setArguments(bundle);
        return fragment;
    }
    private LoadMoreListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_other_muisc, null);
        mSwipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.swipe_target);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mNoItemIV = (ImageView) view.findViewById(R.id.no_item_iv);
        mSwipeToLoadLayout.setOnLoadMoreListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mCanLoad && mLayoutManager.findLastCompletelyVisibleItemPosition() ==
                            mAdapter.getItemCount() - 1) {
                        mSwipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            mType = savedInstanceState.getString(S.TYPE);
        }
    }

    public void showList(List list) {
        if (mAdapter == null) {
            mAdapter = AdapterFactory.getAdapter(getContext(), list, mType);
            //第一次加入
            if (list.size() < PAGE_COUNT) {
                mCanLoad = false;
                mSwipeToLoadLayout.setLoadMoreEnabled(false);
            }
        } else {
            mSwipeToLoadLayout.setLoadingMore(false);
            mAdapter.addItems(list);
        }
    }

    public void noMore() {
        mCanLoad = false;
        if (mAdapter == null) {
            mNoItemIV.setVisibility(View.VISIBLE);
            mSwipeToLoadLayout.setVisibility(View.GONE);
        } else {
            mSwipeToLoadLayout.setLoadingMore(false);
            mSwipeToLoadLayout.setLoadMoreEnabled(false);
            AppUtil.showToast(R.string.no_more);
        }
    }

    @Override
    public void onLoadMore() {
        if (mListener != null) {
            mListener.loadMore(mType);
        }
    }

    public interface LoadMoreListener {
        void loadMore(String type);
    }
}
