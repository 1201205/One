package com.hyc.one.view;

import com.hyc.one.base.BaseView;
import com.hyc.one.beans.OnePictureData;

import java.util.List;

/**
 * Created by ray on 16/4/19.
 */
public interface MonthPictureView extends BaseView{
    void showPictures(List<OnePictureData> datas);
}
