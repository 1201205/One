package com.hyc.one.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import com.hyc.one.MainApplication;
import com.hyc.one.R;
import com.hyc.one.ui.OtherDetailActivity;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ray on 16/5/13.
 */
public class AppUtil {
    public static void showToast(String s) {
        Toast.makeText(MainApplication.getApplication(), s, Toast.LENGTH_LONG).show();
    }

    public static void showToast(int id) {
        Toast.makeText(MainApplication.getApplication(), id, Toast.LENGTH_LONG).show();
    }

    /**
     * 用过id获取Drawable
     *
     * @param id
     * @return
     */
    public static Drawable getDrawable(int id) {
        return MainApplication.getApplication().getResources().getDrawable(id);
    }

    /**
     * dp转px
     *
     * @param dipValue
     * @return
     */
    public static int dip2px(float dipValue) {
        final float scale = MainApplication.getApplication().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        final float fontScale = MainApplication.getApplication().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static String getString(int id) {
        return MainApplication.getApplication().getString(id);
    }

    /**
     * 获取颜色
     *
     * @param id
     * @return
     */
    public static int getColor(int id) {
        return MainApplication.getApplication().getResources().getColor(R.color.google_blue);
    }

    public static boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)
                mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList
                = activityManager.getRunningServices(30);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    // 随机生成一个数
    private final static AtomicInteger c = new AtomicInteger(1);

    // 获取一个不重复的数, 从1开始
    public static int getID() {
        return c.incrementAndGet();
    }

    public static void startActivityWithID(String id, Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra(S.ID, id);
        context.startActivity(intent);
    }

    public static void startActivityWithID(String id, Context context, Class clazz, int title) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra(S.ID, id);
        intent.putExtra(S.TITLE, AppUtil.getString(title));
        context.startActivity(intent);
    }

    public static void startActivityWithUrl(String url, Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra(S.URL, url);
        context.startActivity(intent);
    }

    public static void startActivityWithType(int type, Context context, Class clazz, int title) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra(S.TYPE, type);
        intent.putExtra(S.TITLE, AppUtil.getString(title));
        context.startActivity(intent);
    }

    public static void startActivityWithIDAndType(String id, int type, Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra(S.ID, id);
        intent.putExtra(S.TYPE, type);
        context.startActivity(intent);
    }

    /**
     * 他人相关的跳转
     *
     * @param id      id
     * @param type    跳转页面对应的type
     * @param context contex
     * @param clazz   目标Activity
     * @param title   title
     */
    public static void startActivityWithIDAndType(String id, int type, Context context, Class clazz, int title) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra(S.ID, id);
        intent.putExtra(S.TYPE, type);
        intent.putExtra(S.TITLE, AppUtil.getString(title));
        context.startActivity(intent);
    }

    /**
     * @param id      其他人的id
     * @param context context
     * @return 获取跳转到Other页面的点击事件
     */
    public static View.OnClickListener getOtherClickListener(final String id, final Context context) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OtherDetailActivity.jumpTo(context, id);
            }
        };
    }

    /**
     * @param context context
     * @return 是否有网络连接
     */
    public static boolean hasConnect(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = manager.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
