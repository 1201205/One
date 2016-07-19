package com.hyc.zhihu.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.hyc.zhihu.event.NetWorkChangeEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/7/19.
 */
public class NetWorkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, intent.getAction(), 1).show();
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//        NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo activeInfo = manager.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            EventBus.getDefault().post(new NetWorkChangeEvent(true));
        } else {
            EventBus.getDefault().post(new NetWorkChangeEvent(false));
        }
//        Toast.makeText(context, "mobile:"+mobileInfo.isConnected()+"\n"+"wifi:"+wifiInfo.isConnected()
//                +"\n"+"active:"+(activeInfo==null?"null":activeInfo.getTypeName()), Toast.LENGTH_LONG).show();
    }  //如果无网络连接activeInfo为null

}