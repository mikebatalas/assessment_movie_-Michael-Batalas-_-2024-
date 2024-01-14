package com.nick.movieexplorer.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

public class Utils {
    public static void setFavorite(Context context, String key, boolean value) {
        SharedPreferences setFavoriteShared = PreferenceManager.getDefaultSharedPreferences(context);
        setFavoriteShared.edit().putBoolean(key, value).apply();
    }

    public static Boolean getFavorite(Context context, String key) {
        SharedPreferences getFavoriteShared = PreferenceManager.getDefaultSharedPreferences(context);
        return getFavoriteShared.getBoolean(key, false);
    }

    public static boolean networkChecker(Activity activity) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
