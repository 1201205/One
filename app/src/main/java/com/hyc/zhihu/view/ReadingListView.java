package com.hyc.zhihu.view;

import com.hyc.zhihu.base.BaseView;
import com.hyc.zhihu.beans.ReadingListItem;

import java.util.List;

/**
 * Created by Administrator on 2016/5/18.
 */
public interface ReadingListView extends BaseView {
    void showBottom();
    void showList(List<ReadingListItem> readingListItems);
}
