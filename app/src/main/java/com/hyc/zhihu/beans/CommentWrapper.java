package com.hyc.zhihu.beans;

import java.util.List;

public class CommentWrapper implements java.io.Serializable {
    private static final long serialVersionUID = 2002454836703801066L;
    private int count;
    private List<Comment> data;

    public List<Comment> getData() {
        return data;
    }

    public void setData(List<Comment> data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
