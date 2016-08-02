package com.hyc.one.utils;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by ray on 16/5/10.
 */
public class RealmUtil {
    private static Executor mExecutor = Executors.newSingleThreadExecutor();
    //每个线程一个实例
    private static Realm sRealm;

    static {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                sRealm = Realm.getDefaultInstance();
            }
        });
    }

    public static <E extends RealmModel> void save(final E o) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
//                sRealm=Realm.getDefaultInstance();
                sRealm.beginTransaction();
                sRealm.copyToRealm(o);
                sRealm.commitTransaction();
            }
        });
    }

    public static <E extends RealmModel> void saveOrUpdate(final E o) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
//                sRealm=Realm.getDefaultInstance();
                sRealm.beginTransaction();
                sRealm.copyToRealmOrUpdate(o);
                sRealm.commitTransaction();
            }
        });
    }

    public static <E extends RealmModel> List<E> getListByCount(final Class<E> clazz, Date date) {
        Realm temp = Realm.getDefaultInstance();
        temp.beginTransaction();
        List<E> realmResults = temp.where(clazz).greaterThan("date", date).findAllSorted("date", Sort.DESCENDING);
        temp.commitTransaction();
        return realmResults;
    }


    public static <E extends RealmModel> List<E> getListByLower(final Class<E> clazz, String filedName, List<String> ids) {
        Realm temp = Realm.getDefaultInstance();
        temp.beginTransaction();
        RealmQuery<E> query = temp.where(clazz);
        //findAll 未测试性能
        List<E> realmResults = query.findAll().subList(0, 10);
        temp.commitTransaction();
        return realmResults;
    }

    public static <E extends RealmModel> void save(final List<E> o) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                sRealm.beginTransaction();
                sRealm.copyToRealmOrUpdate(o);
                sRealm.commitTransaction();
            }
        });
    }

    public static <E extends RealmModel> RealmResults<E> findByKeyBackGround(final Class<E> clazz, final String fieldName, final String value) {
        FutureTask task = new FutureTask(new Callable() {
            @Override
            public RealmResults<E> call() throws Exception {
                sRealm.beginTransaction();
                RealmResults<E> realmResults = sRealm.where(clazz).equalTo(fieldName, value).findAll();
                sRealm.commitTransaction();
                return realmResults;
            }
        });
        mExecutor.execute(task);
        try {
            return (RealmResults<E>) task.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <E extends RealmModel> RealmResults<E> findByKey(final Class<E> clazz, final String fieldName, final String value) {
        Realm temp = Realm.getDefaultInstance();
        temp.beginTransaction();
        RealmResults<E> realmResults = temp.where(clazz).equalTo(fieldName, value).findAll();
        temp.commitTransaction();
        return realmResults;
    }

    public static <E extends RealmModel> E findByKeyOne(final Class<E> clazz, final String fieldName, final String value) {
        Realm temp = Realm.getDefaultInstance();
        temp.beginTransaction();
        RealmResults<E> realmResults = temp.where(clazz).equalTo(fieldName, value).findAll();
        temp.commitTransaction();
        if (realmResults != null && realmResults.size() > 0) {
            return realmResults.first();
        }
        return null;
    }
}
