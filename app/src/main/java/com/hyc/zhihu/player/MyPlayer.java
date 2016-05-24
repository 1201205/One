package com.hyc.zhihu.player;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import com.hyc.zhihu.MainApplication;
import com.hyc.zhihu.beans.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by ray on 16/5/22.
 */
public class MyPlayer implements MediaPlayer.OnCompletionListener {
    private static MyPlayer player = new MyPlayer();

    private ManagedMediaPlayer mMediaPlayer;
    private Context mContext;
    private List<Song> mQueue;
    private int mQueueIndex;
    private PlayMode mPlayMode;


    private enum PlayMode {
        LOOP, RANDOM, REPEAT, ORDER
    }

    public static MyPlayer getPlayer() {
        return player;
    }

    public static void setPlayer(MyPlayer player) {
        MyPlayer.player = player;
    }

    public MyPlayer() {

        mMediaPlayer = new ManagedMediaPlayer();
        mMediaPlayer.setOnCompletionListener(this);

        mQueue = new ArrayList<>();
        mQueueIndex = 0;

        mPlayMode = PlayMode.LOOP;
    }
    public boolean isPlaying(){
        return mMediaPlayer.getState()== ManagedMediaPlayer.Status.STARTED;
    }
    public boolean isPaused(){
        return mMediaPlayer.getState()==ManagedMediaPlayer.Status.PAUSED;
    }
    public boolean isStoped(){
        return mMediaPlayer.getState()==ManagedMediaPlayer.Status.STOPPED;
    }

    public ManagedMediaPlayer.Status getSourceStatus(String path){
        if (mQueue != null && mQueue.size() > 0) {
            if (mQueue.get(mQueueIndex).getPath().equals(path)) {
                return mMediaPlayer.getState();
            }
        }
        return ManagedMediaPlayer.Status.IDLE;
    }
    public void setQueue(List<Song> queue, int index) {
        mQueue = queue;
        mQueueIndex = index;
        play(getNowPlaying());
    }

    public void play(Song song) {
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(MainApplication.getApplication(), Uri.parse(song.getPath()));
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mMediaPlayer.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        mMediaPlayer.pause();
    }

    public void resume() {
        mMediaPlayer.start();
    }

    public void next() {
        Song song=getNextSong();
        if (song!=null) {
            play(song);
        }
    }

    public void previous() {
        play(getPreviousSong());
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        next();
    }

    private Song getNowPlaying() {
        if (mQueue.isEmpty()) {
            return null;
        }
        return mQueue.get(mQueueIndex);
    }

    private Song getNextSong() {
        if (mQueue.isEmpty()) {
            return null;
        }
        switch (mPlayMode) {
            case LOOP:
                return mQueue.get(getNextIndex());
            case RANDOM:
                return mQueue.get(getRandomIndex());
            case REPEAT:
                return mQueue.get(mQueueIndex);
            case ORDER:
                int index=getNextIndexInOrder();
                if (index==-1) {
                    return null;
                }
                return mQueue.get(index);
        }
        return null;
    }

    private Song getPreviousSong() {
        if (mQueue.isEmpty()) {
            return null;
        }
        switch (mPlayMode) {
            case LOOP:
                return mQueue.get(getPreviousIndex());
            case RANDOM:
                return mQueue.get(getRandomIndex());
            case REPEAT:
                return mQueue.get(mQueueIndex);
        }
        return null;
    }


    public int getCurrentPosition() {
        if (getNowPlaying() != null) {
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    public int getDuration() {
        if (getNowPlaying() != null) {
            return mMediaPlayer.getDuration();
        }
        return 0;
    }

    public PlayMode getPlayMode() {
        return mPlayMode;
    }

    public void setPlayMode(PlayMode playMode) {
        mPlayMode = playMode;
    }

    private int getNextIndex() {
        mQueueIndex = (mQueueIndex + 1) % mQueue.size();
        return mQueueIndex;
    }
    private int getNextIndexInOrder() {
        if (mQueueIndex + 1 >= mQueue.size()) {
            return -1;
        } else {
            return mQueueIndex+1;
        }
    }
    private int getPreviousIndex() {
        mQueueIndex = (mQueueIndex - 1) % mQueue.size();
        return mQueueIndex;
    }

    private int getRandomIndex() {
        mQueueIndex = new Random().nextInt(mQueue.size()) % mQueue.size();
        return mQueueIndex;
    }

    public void release() {
        mMediaPlayer.release();
        mMediaPlayer = null;
        mContext = null;
    }
}
