package com.hyc.one.view;

import com.hyc.one.base.BaseView;
import com.hyc.one.beans.Other;
import com.hyc.one.beans.OtherCenter;

/**
 * Created by ray on 16/4/19.
 */
public interface OtherDetailView extends BaseView{
    void showContent(Other other);
    void showDairyAndMusic(OtherCenter otherCenter);
    void onNothingGet();
}
