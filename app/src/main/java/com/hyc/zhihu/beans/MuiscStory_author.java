package com.hyc.zhihu.beans;

public class MuiscStory_author implements java.io.Serializable {
    private static final long serialVersionUID = -3172783166651562483L;
    private String web_url;
    private String user_id;
    private String user_name;

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
}
