package com.hyc.one.beans.movie;

public class Movie implements java.io.Serializable {
    private static final long serialVersionUID = 4321978985986255763L;
    private String revisedscore;
    private String cover;
    private String score;
    private String releasetime;
    private int servertime;
    private String id;
    private String verse;
    private String title;
    private String scoretime;
    private String verse_en;

    public String getRevisedscore() {
        return this.revisedscore;
    }

    public void setRevisedscore(String revisedscore) {
        this.revisedscore = revisedscore;
    }

    public String getCover() {
        return this.cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getScore() {
        return this.score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getReleasetime() {
        return this.releasetime;
    }

    public void setReleasetime(String releasetime) {
        this.releasetime = releasetime;
    }

    public int getServertime() {
        return this.servertime;
    }

    public void setServertime(int servertime) {
        this.servertime = servertime;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVerse() {
        return this.verse;
    }

    public void setVerse(String verse) {
        this.verse = verse;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScoretime() {
        return this.scoretime;
    }

    public void setScoretime(String scoretime) {
        this.scoretime = scoretime;
    }

    public String getVerse_en() {
        return this.verse_en;
    }

    public void setVerse_en(String verse_en) {
        this.verse_en = verse_en;
    }
}
