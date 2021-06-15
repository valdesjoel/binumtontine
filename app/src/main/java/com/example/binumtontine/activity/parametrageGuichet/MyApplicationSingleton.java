package com.example.binumtontine.activity.parametrageGuichet;

import android.app.Application;
//import android.net.http.RequestQueue;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.toolbox.Volley;

import com.android.volley.RequestQueue;

public class MyApplicationSingleton extends Application {
    public static final String TAG = MyApplicationSingleton.class
            .getSimpleName();

    private RequestQueue mRequestQueue;

    private static MyApplicationSingleton mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized MyApplicationSingleton getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
