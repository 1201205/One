package com.hyc.one.view;

import com.hyc.one.base.BaseView;
import com.hyc.one.beans.HeadScrollItem;
import com.hyc.one.beans.RealReading;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/5/16.
 */
public interface ReadingView extends BaseView {
    void showHead(List<HeadScrollItem> headScrollItems);
    void showList(List<RealReading> realReadings,LinkedHashMap<Integer,String> indexer,boolean needToClear);
}
