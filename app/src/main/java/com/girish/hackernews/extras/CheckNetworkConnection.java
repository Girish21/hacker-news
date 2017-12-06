package com.girish.hackernews.extras;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Girish on 07-Dec-17.
 */

public class CheckNetworkConnection {

    public static boolean isInternetAvailable(Context context) {
        NetworkInfo info = null;
        if (context.getSystemService(Context.CONNECTIVITY_SERVICE) != null) {
            info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        }

        return info != null;
    }
}
