package com.hyc.one.beans;

public class SerialContent implements java.io.Serializable {
    private static final long serialVersionUID = 5933259838190927725L;
    private String charge_edt;
    private String maketime;
    private String input_name;
    private Object push_id;
    private int praisenum;
    private String title;
    private int sharenum;
    private String content;
    private String last_update_name;
    private String number;
    private String read_num;
    private String web_url;
    private String serial_id;
    private String id;
    private String audio;
    private String excerpt;
    private String last_update_date;
    private int commentnum;

    public String getCharge_edt() {
        return this.charge_edt;
    }

    public void setCharge_edt(String charge_edt) {
        this.charge_edt = charge_edt;
    }

    public String getMaketime() {
        return this.maketime;
    }

    public void setMaketime(String maketime) {
        this.maketime = maketime;
    }

    public String getInput_name() {
        return this.input_name;
    }

    public void setInput_name(String input_name) {
        this.input_name = input_name;
    }

    public Object getPush_id() {
        return this.push_id;
    }

    public void setPush_id(Object push_id) {
        this.push_id = push_id;
    }

    public int getPraisenum() {
        return this.praisenum;
    }

    public void setPraisenum(int praisenum) {
        this.praisenum = praisenum;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSharenum() {
        return this.sharenum;
    }

    public void setSharenum(int sharenum) {
        this.sharenum = sharenum;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLast_update_name() {
        return this.last_update_name;
    }

    public void setLast_update_name(String last_update_name) {
        this.last_update_name = last_update_name;
    }

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

    public String getWeb_url() {
        return this.web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
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

    public String getAudio() {
        return this.audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getExcerpt() {
        return this.excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getLast_update_date() {
        return this.last_update_date;
    }

    public void setLast_update_date(String last_update_date) {
        this.last_update_date = last_update_date;
    }

    public int getCommentnum() {
        return this.commentnum;
    }

    public void setCommentnum(int commentnum) {
        this.commentnum = commentnum;
    }
    public RealArticleAuthor getAuthor() {
        return this.author;
    }

    public void setAuthor(RealArticleAuthor author) {
        this.author = author;
    }
    private RealArticleAuthor author;

}
