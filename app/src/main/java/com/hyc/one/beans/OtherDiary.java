package com.hyc.one.beans;

public class OtherDiary implements java.io.Serializable {
    private static final long serialVersionUID = -1515020542840579019L;
    private String weather;
    private String id;
    private String input_date;
    private String content;
    private String picture;


    public String getWeather() {return this.weather;}


    public void setWeather(String weather) {this.weather = weather;}


    public String getId() {return this.id;}


    public void setId(String id) {this.id = id;}


    public String getInput_date() {return this.input_date;}


    public void setInput_date(String input_date) {this.input_date = input_date;}


    public String getContent() {return this.content;}


    public void setContent(String content) {this.content = content;}


    public String getPicture() {return this.picture;}


    public void setPicture(String picture) {this.picture = picture;}
}
