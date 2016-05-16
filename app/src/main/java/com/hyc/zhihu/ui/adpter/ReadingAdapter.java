package com.hyc.zhihu.ui.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyc.zhihu.R;
import com.hyc.zhihu.beans.ReadingContent;
import com.hyc.zhihu.beans.RealReading;

import java.util.List;

/**
 * Created by Administrator on 2016/5/16.
 */
public class ReadingAdapter extends BaseAdapter {
    private Context mContext;
    private List<RealReading> mRealReadings;
    private List<String> mDates;

    public ReadingAdapter(Context context, List<RealReading> realReadings, List<String> dates) {
        this.mContext = context;
        mRealReadings = realReadings;
        mDates = dates;
    }
    public void refreshList(List<RealReading> realReadings, List<String> dates){
        mDates = dates;
        mRealReadings.addAll(realReadings);
    }
    @Override
    public int getCount() {
        return mRealReadings.size();
    }

    @Override
    public RealReading getItem(int position) {
        return mRealReadings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.reading_item, null);
            holder = new ViewHolder();
            holder.authorTV = (TextView) convertView.findViewById(R.id.author_tv);
            holder.contentTV = (TextView) convertView.findViewById(R.id.content_tv);
            holder.dateTV = (TextView) convertView.findViewById(R.id.date_tv);
            holder.tag = convertView.findViewById(R.id.tag);
            holder.titleTV = (TextView) convertView.findViewById(R.id.title_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ReadingContent realReading = mRealReadings.get(position).getContent();
        String time = mRealReadings.get(position).getTime();
        if (mDates.contains(time)) {
            holder.dateTV.setVisibility(View.VISIBLE);
            holder.dateTV.setText(time);
        } else {
            holder.dateTV.setVisibility(View.GONE);
        }
        holder.titleTV.setText(realReading.getTitle());
        holder.contentTV.setText(realReading.getContent());
        holder.authorTV.setText(realReading.getAuthor());
        return convertView;
    }

    static class ViewHolder {
        private TextView authorTV;
        private TextView titleTV;
        private TextView dateTV;
        private TextView contentTV;
        private View tag;
    }
}
