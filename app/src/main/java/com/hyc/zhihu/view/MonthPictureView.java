package com.hyc.zhihu.view;

import com.hyc.zhihu.base.BaseView;
import com.hyc.zhihu.beans.OnePictureData;

import java.util.List;

/**
 * Created by ray on 16/4/19.
 */
public interface MonthPictureView extends BaseView{
    void showPictures(List<OnePictureData> datas);
}
