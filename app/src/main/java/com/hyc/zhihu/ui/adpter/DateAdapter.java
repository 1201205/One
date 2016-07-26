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
import com.hyc.zhihu.ui.MonthPictureActivity;
import com.hyc.zhihu.utils.S;

import java.util.List;

/**
 * Created by Administrator on 2016/5/9.
 */
public class DateAdapter extends BaseAdapter {
    private List<DateBean> dateBeans;
    private Class mClazz;

    public DateAdapter(List<DateBean> dateBeans) {
        this.dateBeans = dateBeans;
    }

    public DateAdapter(List<DateBean> dateBeans, Class clazz) {
        this.dateBeans = dateBeans;
        this.mClazz = clazz;
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
        ViewHolder holder = null;
        final Context c = parent.getContext();
        if (convertView == null) {
            convertView = LayoutInflater.from(c).inflate(R.layout.date_item, null);
            holder = new ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.date_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final DateBean dateBean = dateBeans.get(position);
        holder.date.setText(dateBeans.get(position).date);
        holder.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, mClazz);
                intent.putExtra(S.DATE, dateBean);
                c.startActivity(intent);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        TextView date;
    }
}
