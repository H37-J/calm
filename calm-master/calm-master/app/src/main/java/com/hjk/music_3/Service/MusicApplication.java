package com.hjk.music_3.Service;

import android.app.Application;

import com.hjk.music_3.breath.BreathePreferences;

public class MusicApplication extends Application {
    private static MusicApplication musicApplication;
    private static MusicServiceImpl musicService;

    @Override
    public void onCreate(){
        super.onCreate();
        BreathePreferences.init(getApplicationContext());
        musicApplication=this;
        musicService=new MusicServiceImpl(getApplicationContext());
    }

    public static MusicApplication getInstance(){
        return musicApplication;
    }
    public static MusicServiceImpl getServiceInterface() { return musicService; }







}
