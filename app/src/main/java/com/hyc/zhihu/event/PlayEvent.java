package com.hyc.zhihu.event;

import com.hyc.zhihu.beans.Song;

import java.util.List;

/**
 * Created by ray on 16/5/22.
 */
public class PlayEvent {

    public enum Action {
        PLAY, STOP, RESUME, NEXT, PREVIOES, SEEK, PAUSE,ADDLIST,PLAYITEM,PLAYPATH
    }

    private Action mAction;
    private Song mSong;
    private List<Song> mQueue;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String path;
    private int seekTo;

    public Song getSong() {
        return mSong;
    }

    public void setSong(Song song) {
        mSong = song;
    }

    public Action getAction() {
        return mAction;
    }

    public void setAction(Action action) {
        mAction = action;
    }

    public List<Song> getQueue() {
        return mQueue;
    }

    public void setQueue(List<Song> queue) {
        mQueue = queue;
    }

    public int getSeekTo() {
        return seekTo;
    }

    public void setSeekTo(int seekTo) {
        this.seekTo = seekTo;
    }
}