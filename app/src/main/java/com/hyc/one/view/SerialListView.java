package com.hyc.one.view;

import com.hyc.one.base.BaseView;
import com.hyc.one.beans.SerialListItem;

import java.util.List;

/**
 * Created by ray on 16/4/19.
 */
public interface SerialListView extends BaseView{
    void showList(List<SerialListItem> datas,String title);
}
