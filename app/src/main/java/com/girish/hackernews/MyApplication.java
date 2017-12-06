package com.girish.hackernews;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.girish.hackernews.database.DBNews;

/**
 * Created by Girish on 05-Dec-17.
 */

public class MyApplication extends Application {

    private static MyApplication myApplication = null;

    public static MyApplication getInstance() {
        return myApplication;
    }

    private static DBNews mDatabase;

    public static Context getAppContext() {
        return myApplication.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        mDatabase = new DBNews(this);
    }

    public synchronized static DBNews getWritableDatabase() {
        if (mDatabase == null)
            mDatabase = new DBNews(getAppContext());
        return mDatabase;
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static void saveToPreferences(Context context, String preferenceName, boolean preferenceValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sharedPreferences.getString(preferenceName, defaultValue);
    }

    public static boolean readFromPreferences(Context context, String preferenceName, boolean defaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sharedPreferences.getBoolean(preferenceName, defaultValue);
    }
}
