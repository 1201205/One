package com.hyc.one.ui.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyc.one.R;
import com.hyc.one.beans.SerialListItem;
import com.hyc.one.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/19.
 */
public class SerialListAdapter extends BaseAdapter {
    private List<SerialListItem> mItems;
    private Context mContext;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    private OnItemClickListener mOnItemClickListener;

    public SerialListAdapter(Context c) {
        mItems = new ArrayList<>();
        mContext = c;
    }

    public void refresh(List<SerialListItem> items) {
        mItems.addAll(items);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public SerialListItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder h = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_title, null);
            h = new ViewHolder();
            h.tv = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(h);
        } else {
            h = (ViewHolder) convertView.getTag();
        }
        h.tv.setText(String.format(AppUtil.getString(R.string.serial_count), mItems.get(position).getNumber()));
        if (mOnItemClickListener != null) {
            h.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClicked(mItems.get(position).getId());
                }
            });
        }

        return convertView;
    }

    static class ViewHolder {
        TextView tv;
    }

    public interface OnItemClickListener {
        void onItemClicked(String id);
    }
}

