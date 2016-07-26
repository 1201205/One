package com.hyc.zhihu.ui.adpter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hyc.zhihu.R;
import com.hyc.zhihu.beans.HeadScrollItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/5/16.
 */
public class ScrollHeadAdapter extends PagerAdapter {
    private List<HeadScrollItem> mItems;
    private Context mContext;

    public ScrollHeadAdapter(Context context, List<HeadScrollItem> items) {
        super();
        mContext = context;
        mItems = items;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        ImageView v = (ImageView) LayoutInflater.from(mContext).inflate(R.layout.item_picture, container, false);
        Picasso.with(mContext).load(mItems.get(position).getPv_url()).into(v);
        container.addView(v);
        return v;
    }

    @Override
    public int getCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
