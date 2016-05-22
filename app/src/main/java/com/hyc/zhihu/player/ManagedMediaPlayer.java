package com.hyc.zhihu.player;

import android.media.MediaPlayer;

import com.hyc.zhihu.event.PlayCallBackEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;


/**
 * Created by ray on 16/5/22.
 */
public class ManagedMediaPlayer extends MediaPlayer implements MediaPlayer.OnCompletionListener {

    public enum Status {
        IDLE, INITIALIZED, STARTED, PAUSED, STOPPED, COMPLETED
    }

    private Status mState;

    private OnCompletionListener mOnCompletionListener;

    public ManagedMediaPlayer() {
        super();
        mState = Status.IDLE;
        super.setOnCompletionListener(this);
    }

    @Override
    public void setDataSource(String path) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        super.setDataSource(path);
        mState = Status.INITIALIZED;
    }

    @Override
    public void start() {
        super.start();
        mState = Status.STARTED;
        EventBus.getDefault().post(new PlayCallBackEvent(PlayCallBackEvent.PLAY));
    }

    @Override
    public void setOnCompletionListener(OnCompletionListener listener) {
        this.mOnCompletionListener = listener;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mState = Status.COMPLETED;
        if (mOnCompletionListener != null) {
            mOnCompletionListener.onCompletion(mp);
        }
    }

    @Override
    public void stop() throws IllegalStateException {
        super.stop();
        mState = Status.STOPPED;
        EventBus.getDefault().post(new PlayCallBackEvent(PlayCallBackEvent.STOP));
    }

    @Override
    public void pause() throws IllegalStateException {
        super.pause();
        mState = Status.PAUSED;
        EventBus.getDefault().post(new PlayCallBackEvent(PlayCallBackEvent.PAUSE));
    }

    public Status getState() {
        return mState;
    }

    public boolean isComplete() {
        return mState == Status.COMPLETED;
    }
}
