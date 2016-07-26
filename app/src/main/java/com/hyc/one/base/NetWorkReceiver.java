package com.hyc.one.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.hyc.one.event.NetWorkChangeEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/7/19.
 */
public class NetWorkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = manager.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            EventBus.getDefault().post(new NetWorkChangeEvent(true));
        } else {
            EventBus.getDefault().post(new NetWorkChangeEvent(false));
        }
    }

}