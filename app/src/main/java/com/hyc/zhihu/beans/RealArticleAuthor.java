package com.hyc.zhihu.beans;

public class RealArticleAuthor implements java.io.Serializable {
    private static final long serialVersionUID = 8455401811597261094L;
    private String wb_name;
    private String web_url;
    private String user_id;
    private String user_name;
    private String desc;

    public String getWb_name() {
        return this.wb_name;
    }

    public void setWb_name(String wb_name) {
        this.wb_name = wb_name;
    }

    public String getWeb_url() {
        return this.web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return this.user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
