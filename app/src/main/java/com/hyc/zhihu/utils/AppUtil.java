package com.hyc.zhihu.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.hyc.zhihu.MainApplication;
import com.hyc.zhihu.R;

import java.util.List;

/**
 * Created by ray on 16/5/13.
 */
public class AppUtil {
    public static void showToast(String s){
        Toast.makeText(MainApplication.getApplication(),s,Toast.LENGTH_LONG).show();
    }
    public static void showToast(int id){
        Toast.makeText(MainApplication.getApplication(),id,Toast.LENGTH_LONG).show();
    }
    public static Drawable getDrawable(int id){
        return MainApplication.getApplication().getResources().getDrawable(id);
    }
    public static int dip2px(float dipValue) {
        final float scale = MainApplication.getApplication().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
    public static int getColor(int id){
        return MainApplication.getApplication().getResources().getColor(R.color.google_blue);
    }
    public static boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)
                mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList
                = activityManager.getRunningServices(30);
        if (!(serviceList.size()>0)) {
            return false;
        }
        for (int i=0; i<serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }
}
