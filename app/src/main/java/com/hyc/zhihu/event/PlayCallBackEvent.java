package com.hyc.zhihu.event;

/**
 * Created by ray on 16/5/22.
 */
public class PlayCallBackEvent {
    public static final int PLAY=0;
    public static final int STOP=1;
    public static final int RESUME=2;

    public static final int PAUSE=3;
    public static final int IDLE=4;
    public int state;

    public PlayCallBackEvent(int state) {
        this.state = state;
    }
}
