package com.hyc.one.beans;

/**
 * Created by ray on 16/5/16.
 */
public class ReadingWrapper {
    private int type;
    private String time;
    private NewReading content;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public NewReading getContent() {
        return content;
    }

    public void setContent(NewReading content) {
        this.content = content;
    }
}
