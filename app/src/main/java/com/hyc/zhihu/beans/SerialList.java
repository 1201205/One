package com.hyc.zhihu.beans;

import java.util.List;

public class SerialList implements java.io.Serializable {
    private static final long serialVersionUID = 6751825881762667557L;
    private String finished;//0未完结
    private String id;//连载的id
    private String title;//连载的标题
    private List<SerialListItem> list;

    public String getFinished() {
        return this.finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SerialListItem> getList() {
        return this.list;
    }

    public void setList(List<SerialListItem> list) {
        this.list = list;
    }
}
