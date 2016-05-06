package com.hyc.zhihu.ui;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.hyc.zhihu.beans.PictureViewBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/6.
 */
public class PictureAdapter extends PagerAdapter {
    private List<PictureViewBean> viewBean;

    @Override
    public int getCount() {
        return viewBean == null ? 0 : viewBean.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    public PictureAdapter(List<PictureViewBean> viewBean) {
        super();
        this.viewBean = viewBean;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        container.removeView((View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}
