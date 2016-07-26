package com.hyc.one.ui.adpter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.hyc.one.R;
import com.hyc.one.beans.ReadingContent;
import com.hyc.one.beans.RealReading;
import com.hyc.one.utils.AppUtil;

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

    public void setItemClickListener(OnReadingItemClickListener clickListener) {
        mClickListener = clickListener;
    }

    private OnReadingItemClickListener mClickListener;

    public ReadingAdapter(Context context, List<RealReading> realReadings, LinkedHashMap<Integer, String> indexer) {
        this.mContext = context;
        mRealReadings = realReadings;
        mIndexer = indexer;
    }

    public ReadingAdapter(Context context) {
        this.mContext = context;
        mRealReadings = new ArrayList<>();
    }

    public void clear() {
        mRealReadings.clear();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.reading_item, null);
            holder = new ViewHolder();
            holder.item = (LinearLayout) convertView.findViewById(R.id.item);
            holder.authorTV = (TextView) convertView.findViewById(R.id.author_tv);
            holder.contentTV = (TextView) convertView.findViewById(R.id.content_tv);
            holder.dateTV = (TextView) convertView.findViewById(R.id.date_tv);
            holder.tag = (ImageView) convertView.findViewById(R.id.tag);
            holder.titleTV = (TextView) convertView.findViewById(R.id.title_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ReadingContent realReading = mRealReadings.get(position).getContent();
        String title = mRealReadings.get(position).getContent().getTitle();
        int section = getSectionForPosition(position);
        int p = getPositionForSection(section);
        if (p == position) {
            holder.dateTV.setVisibility(View.VISIBLE);
            holder.dateTV.setText(mIndexer.get(position));
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
        if (mClickListener != null) {
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClicked(mRealReadings.get(position));
                }
            });
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
        if (mIndexer == null || section < 0 || section >= mIndexer.size()) {
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

    public String getDateBySection(int section) {

        if (mIndexer == null) {
            return "";
        }
        int count = 0;
        for (Integer d : mIndexer.keySet()) {
            if (section == count) {
                return mIndexer.get(d);
            }
            count++;
        }
        return "";
    }

    @Override
    public int getSectionForPosition(int position) {
        if (mIndexer == null || position < 0) {
            return -1;
        }
        int count = 0;
        for (Integer d : mIndexer.keySet()) {
            if (d == position) {
                return count;
            } else if (d > position) {
                return count - 1;
            }
            count++;
        }

        return -1;
    }

    static class ViewHolder {
        private TextView authorTV;
        private TextView titleTV;
        private TextView dateTV;
        private TextView contentTV;
        private ImageView tag;
        private LinearLayout item;
    }

    public interface OnReadingItemClickListener {
        void onItemClicked(RealReading reading);
    }
}
