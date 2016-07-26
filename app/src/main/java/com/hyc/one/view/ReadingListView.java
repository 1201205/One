package com.hyc.one.view;

import com.hyc.one.base.BaseView;
import com.hyc.one.beans.ReadingListItem;

import java.util.List;

/**
 * Created by Administrator on 2016/5/18.
 */
public interface ReadingListView extends BaseView {
    void showBottom();
    void showList(List<ReadingListItem> readingListItems);
}
