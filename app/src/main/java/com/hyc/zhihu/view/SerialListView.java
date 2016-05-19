package com.hyc.zhihu.view;

import com.hyc.zhihu.base.BaseView;
import com.hyc.zhihu.beans.OnePictureData;
import com.hyc.zhihu.beans.SerialListItem;

import java.util.List;

/**
 * Created by ray on 16/4/19.
 */
public interface SerialListView extends BaseView{
    void showList(List<SerialListItem> datas,String title);
}
