package com.hyc.zhihu.beans;

public class Other implements java.io.Serializable {
    private static final long serialVersionUID = -7972950173052551684L;
    private int isauthor;
    private String score;
    private String web_url;
    private int isdisabled;
    private String user_name;
    private String background;
    private OtherPermission permission;
    private String desc;


    public int getIsauthor() {return this.isauthor;}


    public void setIsauthor(int isauthor) {this.isauthor = isauthor;}


    public String getScore() {return this.score;}


    public void setScore(String score) {this.score = score;}


    public String getWeb_url() {return this.web_url;}


    public void setWeb_url(String web_url) {this.web_url = web_url;}


    public int getIsdisabled() {return this.isdisabled;}


    public void setIsdisabled(int isdisabled) {this.isdisabled = isdisabled;}


    public String getUser_name() {return this.user_name;}


    public void setUser_name(String user_name) {this.user_name = user_name;}


    public String getBackground() {return this.background;}


    public void setBackground(String background) {this.background = background;}


    public OtherPermission getPermission() {return this.permission;}


    public void setPermission(OtherPermission permission) {this.permission = permission;}


    public String getDesc() {return this.desc;}


    public void setDesc(String desc) {this.desc = desc;}
}
