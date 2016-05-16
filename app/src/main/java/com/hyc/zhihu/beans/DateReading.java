package com.hyc.zhihu.beans;

import java.util.List;

/**
 * Created by Administrator on 2016/5/16.
 */
public class DateReading {
    private List<Reading> items;
    private String date;

    public List<Reading> getItems() {
        return items;
    }

    public void setItems(List<Reading> items) {
        this.items = items;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
