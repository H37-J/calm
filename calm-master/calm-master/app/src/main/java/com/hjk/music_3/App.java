package com.hjk.music_3;

import android.app.Application;

import com.hjk.music_3.breath.BreathePreferences;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BreathePreferences.init(getApplicationContext());
    }
}
