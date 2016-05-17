package com.hyc.zhihu.ui.adpter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.hyc.zhihu.R;
import com.hyc.zhihu.beans.ReadingContent;
import com.hyc.zhihu.beans.RealReading;
import com.hyc.zhihu.utils.AppUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/5/16.
 */
public class ReadingAdapter extends BaseAdapter implements SectionIndexer {
    private Context mContext;
    private List<RealReading> mRealReadings;
    private LinkedHashMap<Integer, String> mIndexer;

    public ReadingAdapter(Context context, List<RealReading> realReadings, LinkedHashMap<Integer, String> indexer) {
        this.mContext = context;
        mRealReadings = realReadings;
        mIndexer = indexer;
    }

    public ReadingAdapter(Context context) {
        this.mContext = context;
        mRealReadings = new ArrayList<>();
    }

    public void refreshList(List<RealReading> realReadings, LinkedHashMap<Integer, String> indexer) {
        mIndexer = indexer;
        mRealReadings.addAll(realReadings);
        notifyDataSetChanged();
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
            holder.tag = (ImageView) convertView.findViewById(R.id.tag);
            holder.titleTV = (TextView) convertView.findViewById(R.id.title_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ReadingContent realReading = mRealReadings.get(position).getContent();
        String title = mRealReadings.get(position).getContent().getTitle();
        getSectionForPosition(position);
        if (mDates.contains(title)) {
            holder.dateTV.setVisibility(View.VISIBLE);
            holder.dateTV.setText(mRealReadings.get(position).getTime());
        } else {
            holder.dateTV.setVisibility(View.GONE);
        }
        int id = 0;
        switch (mRealReadings.get(position).getType()) {
            case 1:
                id = R.drawable.essay_image;
                break;
            case 2:
                id = R.drawable.serial_image;
                break;
            case 3:
                id = R.drawable.question_image;
                break;
        }
        holder.tag.setImageResource(id);
        holder.titleTV.setText(realReading.getTitle());
        if (realReading.isHasAudio()) {
            Drawable music = AppUtil.getDrawable(R.drawable.audio_image);
            music.setBounds(0, 0, music.getMinimumWidth(), music.getMinimumHeight());
            holder.titleTV.setCompoundDrawables(null, null, music, null);
            holder.titleTV.setCompoundDrawablePadding(AppUtil.dip2px(3));
        } else {
            holder.titleTV.setCompoundDrawables(null, null, null, null);

        }

        holder.contentTV.setText(realReading.getContent());
        holder.authorTV.setText(realReading.getAuthor());
        return convertView;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int section) {
        if (section < 0 || section >= mIndexer.size()) {
            return -1;
        }
        int count = 0;
        int key = 0;
        for (Integer d : mIndexer.keySet()) {
            if (section == count) {
                key = d;
                break;
            }
            count++;
        }
        return key;
    }

    @Override
    public int getSectionForPosition(int position) {
        if (position < 0) {
            return -1;
        }
        int count=0;
        for (Integer d : mIndexer.keySet()) {
            if (mIndexer.get(d).equals(position)) {
                return count;
            }
            count++;
        }
        return -1;
        // 注意这个方法的返回值，它就是index<0时，返回-index-2的原因
        // 解释Arrays.binarySearch，如果搜索结果在数组中，刚返回它在数组中的索引，如果不在，刚返回第一个比它大的索引的负数-1
        // 如果没弄明白，请自己想查看api
    }

    static class ViewHolder {
        private TextView authorTV;
        private TextView titleTV;
        private TextView dateTV;
        private TextView contentTV;
        private ImageView tag;
    }
}
