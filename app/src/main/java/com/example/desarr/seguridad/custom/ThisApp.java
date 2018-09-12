package com.example.desarr.seguridad.custom;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

public class ThisApp extends Application {

    private static Context context;
    private static Activity activity;

    public void onCreate() {
        super.onCreate();
        ThisApp.context = getApplicationContext();
        ThisApp.activity = (Activity) context;
    }

    public static Context getAppContext() {
        return ThisApp.context;
    }

    public static Activity getAppActivity() {
        return ThisApp.activity;
    }
}
