package com.hyc.zhihu.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SPUtil {

    private static List<Class<?>> CLASSES = new ArrayList<Class<?>>();
    private static SharedPreferences prefs;
    static {
        CLASSES.add(String.class);
        CLASSES.add(Boolean.class);
        CLASSES.add(Integer.class);
        CLASSES.add(Long.class);
        CLASSES.add(Float.class);
        CLASSES.add(Set.class);
    }

    private SPUtil() {
    }

    private static SharedPreferences getPrefs(Context ctx) {
        SharedPreferences result = prefs;
        if (result == null)
            synchronized (SPUtil.class) {
                result = prefs;
                if (result == null) {
                    result = prefs = PreferenceManager
                            .getDefaultSharedPreferences(ctx);
                }
            }
        return result;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static <T> void put(Context ctx, String key, T value) {
        Editor ed = _put(ctx, key, value);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            ed.apply();
        } else {
            ed.commit();
        }
    }

    public static <T> boolean commit(Context ctx, String key, T value) {
        return _put(ctx, key, value).commit();
    }

    @SuppressLint("CommitPrefEdits")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static <T> Editor _put(Context ctx, String key, T value) {
        if (key == null) {
            throw new NullPointerException("Null keys are not permitted");
        }
        Editor ed = getPrefs(ctx).edit();
        if (value == null) {
            ed.putString(key, null);
        } else if (value instanceof String) {
            ed.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            ed.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            ed.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            ed.putLong(key, (Long) value);
        } else if (value instanceof Float) {
            ed.putFloat(key, (Float) value);
        } else if (value instanceof Set) {
            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                throw new IllegalArgumentException(
                        "You can add sets in the preferences only after API "
                                + Build.VERSION_CODES.HONEYCOMB);
            }
            @SuppressWarnings({ "unchecked", "unused" })
                    Editor dummyVariable = ed.putStringSet(key, (Set<String>) value);
        } else {
            throw new IllegalArgumentException("The given value : " + value
                    + " cannot be persisted");
        }
        return ed;
    }

    @SuppressWarnings("unchecked")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static <T> T get(Context ctx, String key, T defaultValue) {
        if (key == null) {
            throw new NullPointerException("Null keys are not permitted");
        }
        if (defaultValue == null) {
            if (!getPrefs(ctx).contains(key))
                return null;
            Object value = getPrefs(ctx).getAll().get(key);
            if (value == null)
                return null;
            Class<?> valueClass = value.getClass();
            for (Class<?> cls : CLASSES) {
                if (valueClass.isAssignableFrom(cls)) {
                    return (T) valueClass.cast(value);
                }
            }
            throw new IllegalStateException("Unknown class for value :\n\t"
                    + value + "\nstored in preferences");
        } else if (defaultValue instanceof String) {
            return (T) getPrefs(ctx).getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return (T) (Boolean) getPrefs(ctx).getBoolean(key,
                    (Boolean) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return (T) (Integer) getPrefs(ctx).getInt(key,
                    (Integer) defaultValue);
        } else if (defaultValue instanceof Long) {
            return (T) (Long) getPrefs(ctx).getLong(key, (Long) defaultValue);
        } else if (defaultValue instanceof Float) {
            return (T) (Float) getPrefs(ctx)
                    .getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Set) {
            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                throw new IllegalArgumentException(
                        "You can add sets in the preferences only after API "
                                + Build.VERSION_CODES.HONEYCOMB);
            }
            return (T) getPrefs(ctx).getStringSet(key,
                    (Set<String>) defaultValue);
        } else {
            throw new IllegalArgumentException(defaultValue
                    + " cannot be persisted in SharedPreferences");
        }
    }

    public static boolean contains(Context ctx, String key) {
        if (key == null) {
            throw new NullPointerException("Null keys are not permitted");
        }
        return getPrefs(ctx).contains(key);
    }
    public static Map<String, ?> getAll(Context ctx) {
        return Collections.unmodifiableMap(getPrefs(ctx).getAll());
    }

    public static boolean clear(Context ctx) {
        return getPrefs(ctx).edit().clear().commit();
    }

    public static boolean remove(Context ctx, String key) {
        if (key == null) {
            throw new NullPointerException("Null keys are not permitted");
        }
        return getPrefs(ctx).edit().remove(key).commit();
    }

    public static void registerListener(Context ctx,
                                        OnSharedPreferenceChangeListener lis) {
        if (lis == null) {
            throw new NullPointerException("Null listener");
        }
        getPrefs(ctx).registerOnSharedPreferenceChangeListener(lis);
    }

    public static void unregisterListener(Context ctx,
                                          OnSharedPreferenceChangeListener lis) {
        if (lis == null) {
            throw new NullPointerException("Null listener");
        }
        getPrefs(ctx).unregisterOnSharedPreferenceChangeListener(lis);
    }

    public static void callListener(Context ctx,
                                    OnSharedPreferenceChangeListener lis, String key) {
        if (lis == null) {
            throw new NullPointerException("Null listener");
        }
        if (key == null) {
            throw new NullPointerException("Null keys are not permitted");
        }
        lis.onSharedPreferenceChanged(getPrefs(ctx), key);
    }

    @SuppressWarnings("unused")
    private static Set<String> checkSetContainsStrings(Set<?> set) {
        if (!set.isEmpty()) {
            for (Object object : set) {
                if (!(object instanceof String)) {
                    throw new IllegalArgumentException(
                            "The given set does not contain strings only");
                }
            }
        }
        @SuppressWarnings("unchecked")
        Set<String> stringSet = (Set<String>) set;
        return stringSet;
    }
}