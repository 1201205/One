package com.hyc.zhihu.utils;

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
}
