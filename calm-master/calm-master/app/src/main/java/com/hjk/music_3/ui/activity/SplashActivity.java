package com.hjk.music_3.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.hjk.music_3.R;
import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.ui.activity.login.LoginActivity;
import com.hjk.music_3.ui.viewmodel.BackViewModel;
import com.hjk.music_3.ui.viewmodel.CateViewModel;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;
import com.hjk.music_3.ui.viewmodel.UserViewModel;

import java.util.Random;


public class SplashActivity extends AppCompatActivity {

    UserViewModel userViewModel;
    MusicViewModel musicViewModel;
    BackViewModel backViewModel;
    CateViewModel cateViewModel;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Random random=new Random();
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.init();
        cateViewModel=ViewModelProviders.of(this).get(CateViewModel.class);
        cateViewModel.init();
        musicViewModel=ViewModelProviders.of(this).get(MusicViewModel.class);
        musicViewModel.init();

        cateViewModel.getDay().observe(this,c->{
            int num=random.nextInt(c.size());
             cateViewModel.setCurrentDay(num);
        });
        musicViewModel.set_current_music(9999);
        musicViewModel.set_current_music_music(9999);
        musicViewModel.set_current_music_all(9999);

        backViewModel=ViewModelProviders.of(this).get(BackViewModel.class);
        backViewModel.init();

        backViewModel.getBack().observe(this,b->{
            backViewModel.set_current_back(b.get(0));
        });
        //미니플레이어 하나로 통일하기

        userViewModel.getUserRoom().observe(this,u-> {
            if (u!=null) {
                System.out.println("로그인시도:" + u.getId());
                userViewModel.getLogin(u.getId()).observe(this, u2 -> {
                    userViewModel.setCurrent_user(u);

                });
            }
                });

        Handler handler=new Handler();
        handler.postDelayed(new splash(),3000);

    }

    private class splash implements Runnable{
        public void run(){

            if(userViewModel.load_save_login()==0) {
                startActivity(new Intent(getApplication(), LoginActivity.class));

            }else {
                startActivity(new Intent(getApplication(), MusicActivity.class));
            }
            finish();
        }
    }
}
