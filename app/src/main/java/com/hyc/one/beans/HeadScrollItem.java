package com.hyc.one.beans;

public class HeadScrollItem implements java.io.Serializable {
    private static final long serialVersionUID = 2642117774314424318L;
    private String cover;
    private String bgcolor;
    private String bottom_text;
    private String id;
    private String title;
    private String pv_url;

    public String getCover() {
        return this.cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getBgcolor() {
        return this.bgcolor;
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }

    public String getBottom_text() {
        return this.bottom_text;
    }

    public void setBottom_text(String bottom_text) {
        this.bottom_text = bottom_text;
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

    public String getPv_url() {
        return this.pv_url;
    }

    public void setPv_url(String pv_url) {
        this.pv_url = pv_url;
    }
}
