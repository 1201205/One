package com.hyc.zhihu.event;

import com.hyc.zhihu.player.ManagedMediaPlayer;

/**
 * Created by ray on 16/5/22.
 */
public class PlayCallBackEvent {
    public ManagedMediaPlayer.Status state;
    public PlayCallBackEvent(ManagedMediaPlayer.Status state) {
        this.state = state;
    }

    public ManagedMediaPlayer.Status getState() {
        return state;
    }

    public void setState(ManagedMediaPlayer.Status state) {
        this.state = state;
    }
}
