package com.hyc.zhihu.ui.adpter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hyc.zhihu.R;
import com.hyc.zhihu.beans.DateBean;
import com.hyc.zhihu.beans.OnePictureData;
import com.hyc.zhihu.beans.PictureViewBean;
import com.hyc.zhihu.view.TestView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Administrator on 2016/5/6.
 */
public class PictureAdapter extends PagerAdapter {
    private String mCurrentId;
    private List<PictureViewBean> viewBeans;
    private Context mContext;

    @Override
    public int getCount() {
        return viewBeans == null ? 0 : viewBeans.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public PictureAdapter(List<PictureViewBean> viewBean, Context context) {
        super();
        this.viewBeans = viewBean;
        this.mContext = context;
    }

    @Override
    public int getItemPosition(Object object) {
        View v= (View) object;
        if (mCurrentId.equals(v.getTag()) ){
            return POSITION_NONE;
        } else {
            return POSITION_UNCHANGED;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        Log.e("test1","删除item--"+position);
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View view;
        if (position == viewBeans.size() - 1) {
            view=LayoutInflater.from(mContext).inflate(R.layout.date_list,null);
            ListView listView= (ListView) view.findViewById(R.id.date_list);
            listView.setAdapter(new DateAdapter(getDateBeans(),mContext));
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.picture_adpter_item, null);
            SimpleDraweeView picture =  (SimpleDraweeView)view.findViewById(R.id.picture_sdv);
            TextView vol = (TextView) view.findViewById(R.id.vol_tv);
            TextView name = (TextView) view.findViewById(R.id.name_tv);
            TextView content = (TextView) view.findViewById(R.id.main_tv);
            TextView date = (TextView) view.findViewById(R.id.date_tv);
            PictureViewBean viewBean=viewBeans.get(position);
            Log.e("test1","重新刷新view"+position);
            if (viewBean.state==PictureViewBean.NORMAL) {
                OnePictureData bean=viewBeans.get(position).data;
                picture.setImageURI(Uri.parse(bean.getHp_img_original_url()));
                name.setText(bean.getHp_author());
                vol.setText(bean.getHp_title());
                content.setText(bean.getHp_content());
                date.setText(bean.getLast_update_date());
            }
            view.setTag(viewBean.id);
        }

        ((ViewPager)container).addView(view);
        return view;

    }
    public void setCurrentItem(String id,OnePictureData data){
        Log.e("test1","显示信息");
        mCurrentId=id;
        for (int i=0;i<viewBeans.size();i++) {
            if (viewBeans.get(i).id.equals(id)) {
                viewBeans.get(i).data=data;
                viewBeans.get(i).state=PictureViewBean.NORMAL;
                Log.e("test1","notifyDataSetChanged");
                notifyDataSetChanged();
                break;
            }
        }

    }
    private int mCurrentPage;
    public void setCurrentPage(int page){
        mCurrentPage=page;
    }
    private List<DateBean> getDateBeans() {
        List<DateBean> dateBeans=new ArrayList<>();
        GregorianCalendar calendar=new GregorianCalendar();
        GregorianCalendar temp=new GregorianCalendar(2012,9,1);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        while (temp.before(calendar)){
            String s=format.format(temp.getTime());
            dateBeans.add(new DateBean(s,s+"%2000:00:00"));
            temp.add(GregorianCalendar.MONTH,1);
        }
        Collections.reverse(dateBeans);
        dateBeans.get(0).date="本月";
        return dateBeans;
    }
    public static void main(String[] args){
        GregorianCalendar calendar=new GregorianCalendar();
        GregorianCalendar temp=new GregorianCalendar(2012,9,1);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        while (temp.before(calendar)){
            System.out.println(format.format(temp.getTime()));
            temp.add(GregorianCalendar.MONTH,1);
        }

    }

}