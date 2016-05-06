package com.hyc.zhihu.beans;

/**
 * Created by Administrator on 2016/5/6.
 */
public class PictureViewBean {
    public static final int NORMAL = 100;
    public static final int NORESULT = 101;
    public static final int ERROR = 102;
    public String id;
    public int state;
    public OnePictureData data;

    public PictureViewBean(String id, int state, OnePictureData data) {
        this.id = id;
        this.state = state;
        this.data = data;
    }
}
