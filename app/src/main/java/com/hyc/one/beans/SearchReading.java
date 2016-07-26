package com.hyc.one.beans;

public class SearchReading implements java.io.Serializable {
    private static final long serialVersionUID = 5924064870408940391L;
    private int number;
    private String id;
    private String title;
    private String type;//essay question

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
