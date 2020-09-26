package com.hjk.music_3.Service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.hjk.music_3.ui.viewmodel.MusicViewModel;

public class MusicServiceImpl extends AppCompatActivity {
    private static ServiceConnection serviceConnection;
    private static MusicService musicService;

    @Override
    public void onCreate(Bundle save){
        super.onCreate(save);
    }

    public MusicServiceImpl(Context context){
        serviceConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder service) {
                musicService=((MusicService.MusicServiceBinder)service).getService();
            }
            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                serviceConnection=null;
                musicService=null;
            }
        };
        context.bindService(new Intent(context,MusicService.class).setPackage(context.getPackageName()),serviceConnection,Context.BIND_AUTO_CREATE);
    }

    public void setViewModel(MusicViewModel musicViewModel){ musicService.setViewModel(musicViewModel); }
    public void setFragment(Fragment fragment) { musicService.getFragment(fragment);}



    public void initLike(){musicService.initLike();}
    public void init(){ musicService.init();}
    public void init11(){ musicService.init11();}
    public void init12(){ musicService.init12();}
    public void init13(){ musicService.init13();}
    public void init_dream(){ musicService.init_dream();}
    public void init_sleep(){ musicService.init_sleep();}
    public void init_scape(){ musicService.init_scape();}
    public void init3(){musicService.init_3();}
    public void initMusic(){musicService.init_music();}
    //true 설정

    public void setInit(){
        musicService.setInit();
    }
    public void setInit11(){ musicService.setInit11(); }
    public void setInit12(){ musicService.setInit12(); }
    public void setInit13(){ musicService.setInit13(); }
    public void setInit21(){ musicService.setInit21(); }
    public void setInit22(){ musicService.setInit22(); }
    public void setInit23(){ musicService.setInit23(); }
    public void setInit34(){ musicService.setInit34(); }
    public void setInitMusic(){ musicService.setInitMusic(); }


    public boolean getInit(){ return musicService.getInit(); }
    public boolean getInit11(){ return musicService.getInit11(); }
    public boolean getInit12(){ return musicService.getInit12(); }
    public boolean getInit13(){ return musicService.getInit13(); }
    public boolean getInit2_1(){ return musicService.getInit2_1(); }
    public boolean getInit2_2(){ return musicService.getInit2_2(); }
    public boolean getInit2_3(){ return musicService.getInit2_3(); }
    public boolean getInit3(){return musicService.getInit3();}
    public boolean getInitLike(){return musicService.getInitLike();}
    public boolean getInitMusic(){return musicService.getInitMusic();}

    public void play() throws Exception{ musicService.play();}
    public void start(){
        musicService.start();
    }
    public void pause(){
        musicService.pause();
    }
    public int getDuration(){
        return musicService.getDuration();
    }
    public int current_position(){
        return musicService.current_position();
    }
    public void setData(String result){musicService.setData(result);}
    public boolean isPlaying(){return musicService.isPlaying();}
    public void set_seek(int time){
        musicService.set_seek(time);
    }
    public void setInitLike(){musicService.setInitLike();}
    //미디어 플레이어 메서드

    public void next11(){musicService.next11();}
    public void next12(){musicService.next12();}
    public void next13(){musicService.next13();}
    public void nextSleep(){ musicService.nextSleep(); }
    public void nextScape(){ musicService.nextScape(); }
    public void nextDream(){ musicService.nextDream(); }

    public void prev11(){musicService.prev11();}
    public void prev12(){musicService.prev12();}
    public void prev13(){musicService.prev13();}
    public void prevSleep(){ musicService.prevSleep(); }
    public void prevScape(){ musicService.prevScape(); }
    public void prevDream(){ musicService.prevDream(); }public void setHide(Boolean hide){ musicService.setHide(hide); }
    public Boolean getHide(){ return musicService.getHide(); } //시작시 미니플레이어 숨기기

}
