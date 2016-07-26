package com.hyc.zhihu.event;

/**
 * Created by Administrator on 2016/7/19.
 */
public class NetWorkChangeEvent {
    public static boolean hasNetWork;

    public NetWorkChangeEvent(boolean hasNetWork) {
        this.hasNetWork = hasNetWork;
    }
}
