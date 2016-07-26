package com.hyc.zhihu;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.hyc.zhihu.event.NetWorkChangeEvent;
import com.hyc.zhihu.helper.FrescoHelper;
import com.hyc.zhihu.player.PlayerService;
import com.hyc.zhihu.utils.S;
import com.hyc.zhihu.utils.SPUtil;

import org.greenrobot.eventbus.EventBus;

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
        getNetWorkState();
        //LeakCanary.install(this);
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
        if (SPUtil.get(S.FIRST_INIT, false)) {
            SPUtil.put(S.FIRST_INIT, true);
        }
    }

    public void getNetWorkState() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = manager.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            EventBus.getDefault().post(new NetWorkChangeEvent(true));
        } else {
            EventBus.getDefault().post(new NetWorkChangeEvent(false));
        }
    }
}
