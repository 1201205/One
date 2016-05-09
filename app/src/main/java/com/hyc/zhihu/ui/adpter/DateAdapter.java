package com.hyc.zhihu.ui.adpter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyc.zhihu.R;
import com.hyc.zhihu.beans.DateBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/9.
 */
public class DateAdapter extends BaseAdapter {
    private List<DateBean> dateBeans;
    private Context context;
    public DateAdapter(List<DateBean> dateBeans, Context context){
        this.dateBeans=dateBeans;
        this.context=context;
    }
    @Override
    public int getCount() {
        return dateBeans == null ? 0 : dateBeans.size();
    }

    @Override
    public DateBean getItem(int position) {
        return dateBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.date_item, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
        } else {
            holder= (ViewHolder) convertView.getTag();
        }
        DateBean dateBean=dateBeans.get(position);
        holder.date.setText(dateBeans.get(position).realDate);
        holder.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent());
            }
        });
        return null;
    }
   static class ViewHolder{
        TextView date;
    }
}
