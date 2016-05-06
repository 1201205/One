package com.hyc.zhihu.view;

import com.hyc.zhihu.base.BaseView;
import com.hyc.zhihu.beans.OnePictureData;
import com.hyc.zhihu.beans.PictureViewBean;

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
