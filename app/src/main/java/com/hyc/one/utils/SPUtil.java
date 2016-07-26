package com.hyc.one.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.preference.PreferenceManager;

import com.hyc.one.MainApplication;

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

    private static SharedPreferences getPrefs() {
        SharedPreferences result = prefs;
        if (result == null)
            synchronized (SPUtil.class) {
                result = prefs;
                if (result == null) {
                    result = prefs = PreferenceManager
                            .getDefaultSharedPreferences(MainApplication.getApplication());
                }
            }
        return result;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static <T> void put(String key, T value) {
        Editor ed = _put(key, value);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            ed.apply();
        } else {
            ed.commit();
        }
    }

    public static <T> boolean commit(String key, T value) {
        return _put(key, value).commit();
    }

    @SuppressLint("CommitPrefEdits")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static <T> Editor _put(String key, T value) {
        if (key == null) {
            throw new NullPointerException("Null keys are not permitted");
        }
        Editor ed = getPrefs().edit();
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
            @SuppressWarnings({"unchecked", "unused"})
            Editor dummyVariable = ed.putStringSet(key, (Set<String>) value);
        } else {
            throw new IllegalArgumentException("The given value : " + value
                    + " cannot be persisted");
        }
        return ed;
    }

    @SuppressWarnings("unchecked")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static <T> T get(String key, T defaultValue) {
        if (key == null) {
            throw new NullPointerException("Null keys are not permitted");
        }
        if (defaultValue == null) {
            if (!getPrefs().contains(key))
                return null;
            Object value = getPrefs().getAll().get(key);
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
            return (T) getPrefs().getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return (T) (Boolean) getPrefs().getBoolean(key,
                    (Boolean) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return (T) (Integer) getPrefs().getInt(key,
                    (Integer) defaultValue);
        } else if (defaultValue instanceof Long) {
            return (T) (Long) getPrefs().getLong(key, (Long) defaultValue);
        } else if (defaultValue instanceof Float) {
            return (T) (Float) getPrefs()
                    .getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Set) {
            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                throw new IllegalArgumentException(
                        "You can add sets in the preferences only after API "
                                + Build.VERSION_CODES.HONEYCOMB);
            }
            return (T) getPrefs().getStringSet(key,
                    (Set<String>) defaultValue);
        } else {
            throw new IllegalArgumentException(defaultValue
                    + " cannot be persisted in SharedPreferences");
        }
    }

    public static boolean contains(String key) {
        if (key == null) {
            throw new NullPointerException("Null keys are not permitted");
        }
        return getPrefs().contains(key);
    }

    public static Map<String, ?> getAll() {
        return Collections.unmodifiableMap(getPrefs().getAll());
    }

    public static boolean clear() {
        return getPrefs().edit().clear().commit();
    }

    public static boolean remove(String key) {
        if (key == null) {
            throw new NullPointerException("Null keys are not permitted");
        }
        return getPrefs().edit().remove(key).commit();
    }

    public static void registerListener(Context ctx,
                                        OnSharedPreferenceChangeListener lis) {
        if (lis == null) {
            throw new NullPointerException("Null listener");
        }
        getPrefs().registerOnSharedPreferenceChangeListener(lis);
    }

    public static void unregisterListener(
            OnSharedPreferenceChangeListener lis) {
        if (lis == null) {
            throw new NullPointerException("Null listener");
        }
        getPrefs().unregisterOnSharedPreferenceChangeListener(lis);
    }

    public static void callListener(
            OnSharedPreferenceChangeListener lis, String key) {
        if (lis == null) {
            throw new NullPointerException("Null listener");
        }
        if (key == null) {
            throw new NullPointerException("Null keys are not permitted");
        }
        lis.onSharedPreferenceChanged(getPrefs(), key);
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