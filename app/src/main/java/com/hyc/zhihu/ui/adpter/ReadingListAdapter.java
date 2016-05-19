package com.hyc.zhihu.ui.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyc.zhihu.R;
import com.hyc.zhihu.beans.ReadingListItem;

import java.util.List;

/**
 * Created by Administrator on 2016/5/18.
 */
public class ReadingListAdapter extends BaseAdapter {
    private List<ReadingListItem> mReadingListItems;
    private Context mContext;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    private OnItemClickListener mOnItemClickListener;

    public ReadingListAdapter(Context context, List<ReadingListItem> items) {
        mContext = context;
        mReadingListItems = items;
    }

    @Override
    public int getCount() {
        return mReadingListItems == null ? 0 : mReadingListItems.size();
    }

    @Override
    public ReadingListItem getItem(int position) {
        return mReadingListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.reading_list_item, null);
            holder = new ViewHolder();
            holder.index = (TextView) convertView.findViewById(R.id.index_tv);
            holder.author = (TextView) convertView.findViewById(R.id.author_tv);
            holder.content = (TextView) convertView.findViewById(R.id.content_tv);
            holder.title = (TextView) convertView.findViewById(R.id.title_tv);
            holder.item= (RelativeLayout) convertView.findViewById(R.id.item_rl);
            convertView.setTag(holder);
        } else {
            holder= (ViewHolder) convertView.getTag();
        }
        final ReadingListItem item=mReadingListItems.get(position);
        holder.index.setText((position+1)+"");
        holder.title.setText(item.getTitle());
        holder.content.setText(item.getIntroduction());
        holder.author.setText(item.getAuthor());
        if (mOnItemClickListener!=null) {
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClicked(item);
                }
            });
        }
        return convertView;
    }

    static class ViewHolder {
        private RelativeLayout item;
        private TextView index;
        private TextView title;
        private TextView author;
        private TextView content;
    }
    public interface OnItemClickListener{
        void onItemClicked( ReadingListItem item);
    }
}
