package com.hyc.zhihu.utils;

import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.hyc.zhihu.MainApplication;

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
}
