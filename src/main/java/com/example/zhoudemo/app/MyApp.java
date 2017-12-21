package com.example.zhoudemo.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by FLOWER on 2017/11/21.
 */

public class MyApp extends Application{
    private static Context context ;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}
