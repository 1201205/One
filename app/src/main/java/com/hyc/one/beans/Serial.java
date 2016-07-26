package com.hyc.one.beans;

public class Serial implements java.io.Serializable {
    private static final long serialVersionUID = -563646877436713083L;
    private String number;
    private String read_num;
    private String maketime;
    private boolean has_audio;
    private RealArticleAuthor author;
    private String serial_id;
    private String id;
    private String title;
    private String excerpt;

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRead_num() {
        return this.read_num;
    }

    public void setRead_num(String read_num) {
        this.read_num = read_num;
    }

    public String getMaketime() {
        return this.maketime;
    }

    public void setMaketime(String maketime) {
        this.maketime = maketime;
    }

    public boolean getHas_audio() {
        return this.has_audio;
    }

    public void setHas_audio(boolean has_audio) {
        this.has_audio = has_audio;
    }

    public RealArticleAuthor getAuthor() {
        return this.author;
    }

    public void setAuthor(RealArticleAuthor author) {
        this.author = author;
    }

    public String getSerial_id() {
        return this.serial_id;
    }

    public void setSerial_id(String serial_id) {
        this.serial_id = serial_id;
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

    public String getExcerpt() {
        return this.excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }
}
