package com.hyc.zhihu.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hyc.zhihu.helper.FrescoHelper;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/7/12.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    //加载图片可能会用到
    private Context mContext;

    public RecyclerViewHolder(Context ctx, View itemView) {
        super(itemView);
        mContext = ctx;
        mViews = new SparseArray<View>();
    }

    private <T extends View> T findViewById(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getView(int viewId) {
        return findViewById(viewId);
    }

    public RecyclerViewHolder setText(int viewId, String value) {
        TextView view = findViewById(viewId);
        view.setText(value);
        return this;
    }

    public RecyclerViewHolder loadImageByFresco(int viewId, String url) {
        SimpleDraweeView view = findViewById(viewId);
        FrescoHelper.loadImage(view, url);
        return this;
    }

    public RecyclerViewHolder setImageResource(int viewId, int id) {
        ImageView view = findViewById(viewId);
        view.setImageResource(id);
        return this;
    }

    public RecyclerViewHolder withItemClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
        return this;
    }

    public RecyclerViewHolder setBackground(int viewId, int resId) {
        View view = findViewById(viewId);
        view.setBackgroundResource(resId);
        return this;
    }

    public RecyclerViewHolder setVisible(int viewId, int visible) {
        View view = findViewById(viewId);
        view.setVisibility(visible);
        return this;
    }

    public RecyclerViewHolder loadImageByPicasso(int viewId, String url) {
        ImageView view = findViewById(viewId);
        Picasso.with(view.getContext()).load(url).fit().into(view);
        return this;
    }

    public RecyclerViewHolder loadImageByPicasso(int viewId, String url, int placeHolder) {
        ImageView view = findViewById(viewId);
        Picasso.with(view.getContext()).load(url).placeholder(placeHolder).fit().into(view);
        return this;
    }

    public RecyclerViewHolder setClickListener(int viewId, View.OnClickListener listener) {
        View view = findViewById(viewId);
        view.setOnClickListener(listener);
        return this;
    }
}
