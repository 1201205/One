package com.hyc.zhihu;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.hyc.zhihu.helper.FrescoHelper;
import com.hyc.zhihu.player.PlayerService;
import com.hyc.zhihu.utils.S;
import com.hyc.zhihu.utils.SPUtil;
import com.squareup.leakcanary.LeakCanary;

import java.io.File;

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
        sContext = this;
        initRealm();
        FrescoHelper.initialize(this);
        startService(new Intent(this, PlayerService.class));
        LeakCanary.install(this);
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
