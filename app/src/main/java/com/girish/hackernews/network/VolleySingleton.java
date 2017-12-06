package com.girish.hackernews.network;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.girish.hackernews.MyApplication;

public class VolleySingleton {

    private static VolleySingleton volleySingleton = null;
    private RequestQueue requestQueue;

    public VolleySingleton() {
        requestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
    }

    public static VolleySingleton getInstance() {
        if (volleySingleton == null)
            volleySingleton = new VolleySingleton();
        return volleySingleton;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
