package com.hyc.zhihu;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.hyc.zhihu.helper.FrescoHelper;
import com.hyc.zhihu.utils.S;
import com.hyc.zhihu.utils.SPUtil;

import java.io.File;
import java.io.IOException;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;

/**
 * Created by ray on 16/4/19.
 */
public class MainApplication extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        FrescoHelper.initialize(this);
        sContext = this;
        initRealm();
    }

    public static Context getApplication() {
        return sContext;
    }

    private void initRealm() {
        RealmConfiguration configuration = new RealmConfiguration.Builder(getExternalCacheDir())
                .name("test1")
                .migration(new RealmMigration() {
                    @Override
                    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

                    }
                })
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(configuration);

    }

    private void checkFirstInit(File file) {
        if (SPUtil.get(this, S.FIRST_INIT, false)) {
            SPUtil.put(this, S.FIRST_INIT, true);
        }
    }
}
