package com.hyc.zhihu.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hyc.zhihu.R;
import com.hyc.zhihu.widget.ListViewForScrollView;

/**
 * Created by Administrator on 2016/5/18.
 */
public class TestActivity extends AppCompatActivity implements OnLoadMoreListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.header_movie);
//        final SwipeToLoadLayout swipeToLoadLayout= (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
//        ListView listView= (ListView) findViewById(R.id.swipe_target);
//       listView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.activity_test,null));
//        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
//                    if (view.getLastVisiblePosition() == view.getCount() - 1 && !ViewCompat.canScrollVertically(view, 1)) {
//                        swipeToLoadLayout.setLoadingMore(true);
//                    }
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//            }
//        });
//        listView.setAdapter(new TestAdapter());
    }

    @Override
    public void onLoadMore() {

    }

    static class ViewHolder{
        TextView tv;
    }
    class TestAdapter extends BaseAdapter{
        TestAdapter(){

        }
        @Override
        public int getCount() {
            return 100;
        }

        @Override
        public Integer getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder h=null;
            if (convertView == null) {
                convertView = LayoutInflater.from(TestActivity.this).inflate(R.layout.layout_title, null);
                h = new ViewHolder();
                h.tv = (TextView) convertView.findViewById(R.id.title);
                convertView.setTag(h);
            } else {
                h= (ViewHolder) convertView.getTag();
            }
            h.tv.setText(position+"");
            return convertView;
        }
    }
}
