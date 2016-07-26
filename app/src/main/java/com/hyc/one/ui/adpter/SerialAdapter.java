package com.hyc.one.ui.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyc.one.R;
import com.hyc.one.beans.Serial;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/16.
 */
public class SerialAdapter extends BaseAdapter {
    private Context mContext;
    private List<Serial> mSerials;

    public void setItemClickListener(OnReadingItemClickListener clickListener) {
        mClickListener = clickListener;
    }

    private OnReadingItemClickListener mClickListener;

    public SerialAdapter(Context context, List<Serial> serials) {
        this.mContext = context;
        mSerials = serials;
    }

    public SerialAdapter(Context context) {
        this.mContext = context;
        mSerials = new ArrayList<>();
    }

    public void refreshList(List<Serial> realReadings) {
        mSerials.addAll(realReadings);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mSerials.size();
    }

    @Override
    public Serial getItem(int position) {
        return mSerials.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.reading_item, null);
            holder = new ViewHolder();
            holder.item = (LinearLayout) convertView.findViewById(R.id.item);
            holder.authorTV = (TextView) convertView.findViewById(R.id.author_tv);
            holder.contentTV = (TextView) convertView.findViewById(R.id.content_tv);
            holder.tag = (ImageView) convertView.findViewById(R.id.tag);
            holder.titleTV = (TextView) convertView.findViewById(R.id.title_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Serial s = mSerials.get(position);
        holder.tag.setImageResource(R.drawable.serial_image);
        holder.titleTV.setText(s.getTitle());
        if (mClickListener != null) {
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClicked(s);
                }
            });
        }
        holder.contentTV.setText(s.getExcerpt());
        holder.authorTV.setText(s.getAuthor().getUser_name());
        return convertView;
    }


    static class ViewHolder {
        private TextView authorTV;
        private TextView titleTV;
        private TextView contentTV;
        private ImageView tag;
        private LinearLayout item;
    }

    public interface OnReadingItemClickListener {
        void onItemClicked(Serial s);
    }


}
