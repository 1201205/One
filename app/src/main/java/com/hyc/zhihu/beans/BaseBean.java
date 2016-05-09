package com.hyc.zhihu.beans;

import io.realm.RealmObject;

/**
 * Created by ray on 16/5/4.
 */
public class BaseBean<T>{
    protected int res;
    protected T data;

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
