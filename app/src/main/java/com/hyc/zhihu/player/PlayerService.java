package com.hyc.zhihu.player;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.hyc.zhihu.event.PlayEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by ray on 16/5/22.
 */
public class PlayerService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    //接收EventBus post过来的PlayEvent
    @Subscribe
    public void onEvent(PlayEvent playEvent) {
        switch (playEvent.getAction()) {
            case PLAY:
                MyPlayer.getPlayer().setQueue(playEvent.getQueue(), 0);
                break;
            case NEXT:
                MyPlayer.getPlayer().next();
                break;
            case PAUSE:
                MyPlayer.getPlayer().pause();
                break;
            case RESUME:
                MyPlayer.getPlayer().resume();
                break;
            case ADDLIST:
                MyPlayer.getPlayer().setQueue(playEvent.getQueue(), -1);
                break;
            case PLAYITEM:
                MyPlayer.getPlayer().play(playEvent.getSong());
                break;
            case PLAYPATH:
                MyPlayer.getPlayer().play(playEvent.getPath());
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
