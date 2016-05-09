package com.hyc.zhihu;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.hyc.zhihu.helper.FrescoHelper;

/**
 * Created by ray on 16/4/19.
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FrescoHelper.initialize(this);
    }
}
