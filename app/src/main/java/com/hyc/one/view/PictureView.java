package com.hyc.one.view;

import com.hyc.one.base.BaseView;
import com.hyc.one.beans.OnePictureData;
import com.hyc.one.beans.PictureViewBean;

import java.util.List;

/**
 * Created by ray on 16/5/5.
 */
public interface PictureView extends BaseView{

    void showPicture(String id,OnePictureData data);
    void jumpToDate();
    void showNetWorkError();
    void setAdapter(List<PictureViewBean> beans);
}
