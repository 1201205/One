package com.hyc.one.view;

import com.hyc.one.base.BaseView;

import java.util.List;

/**
 * Created by ray on 16/4/19.
 */
public interface  OtherPictureView<T> extends BaseView{
    void showList(List<T> datas);
    void refresh(List<T> datas);
    void nothingGet();
}
