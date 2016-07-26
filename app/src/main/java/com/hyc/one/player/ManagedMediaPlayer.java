package com.hyc.one.player;

import android.media.MediaPlayer;

import com.hyc.one.event.PlayCallBackEvent;

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
        mState = ManagedMediaPlayer.Status.IDLE;
        super.setOnCompletionListener(this);
    }

    @Override
    public void setDataSource(String path) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        super.setDataSource(path);
        mState = ManagedMediaPlayer.Status.INITIALIZED;
    }

    @Override
    public void start() {
        super.start();
        mState = ManagedMediaPlayer.Status.STARTED;
        EventBus.getDefault().post(new PlayCallBackEvent(mState));
    }

    @Override
    public void setOnCompletionListener(OnCompletionListener listener) {
        this.mOnCompletionListener = listener;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mState = ManagedMediaPlayer.Status.COMPLETED;
        if (mOnCompletionListener != null) {
            mOnCompletionListener.onCompletion(mp);
        }
    }

    @Override
    public void stop() throws IllegalStateException {
        super.stop();
        mState = ManagedMediaPlayer.Status.STOPPED;
        EventBus.getDefault().post(new PlayCallBackEvent(mState));
    }

    @Override
    public void pause() throws IllegalStateException {
        super.pause();
        mState = ManagedMediaPlayer.Status.PAUSED;
        EventBus.getDefault().post(new PlayCallBackEvent(mState));
    }

    public Status getState() {
        return mState;
    }

    public boolean isComplete() {
        return mState == ManagedMediaPlayer.Status.COMPLETED;
    }
}
