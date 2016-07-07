package com.hyc.zhihu.view;

import com.hyc.zhihu.base.BaseView;
import com.hyc.zhihu.beans.OnePictureData;
import com.hyc.zhihu.beans.Other;
import com.hyc.zhihu.beans.OtherCenter;

import java.util.List;

/**
 * Created by ray on 16/4/19.
 */
public interface OtherDetailView extends BaseView{
    void showContent(Other other);
    void showDairyAndMusic(OtherCenter otherCenter);
    void onNothingGet();
}
