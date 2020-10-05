package com.hjk.music_3.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.hjk.music_3.R;
import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.data.local.model.User;
import com.hjk.music_3.ui.activity.login.LoginActivity;
import com.hjk.music_3.ui.viewmodel.BackViewModel;
import com.hjk.music_3.ui.viewmodel.CateViewModel;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;
import com.hjk.music_3.ui.viewmodel.UserViewModel;
import com.hjk.music_3.utils.TimeUtils;
import com.hjk.music_3.utils.ToastUtils;

import java.util.Random;


public class SplashActivity extends AppCompatActivity {

    UserViewModel userViewModel;
    MusicViewModel musicViewModel;
    BackViewModel backViewModel;
    CateViewModel cateViewModel;
    int count;
    int day;
    int save_back;

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

        //미니플레이어 하나로 통일하기

        userViewModel.getUserRoom().observe(this,u-> {
            if (u!=null) {
                System.out.println("로그인시도:" + u.getId());

                userViewModel.getUserList().observe(this, u2 -> {
                    for (int i = 0; i < u2.size(); i++) {
                        if (u2.get(i).getId().equals(u.getId())) {
                            if (Integer.parseInt(u2.get(i).getLast_login()) == Integer.parseInt(TimeUtils.getDayCom()) - 1) {
                                day = Integer.parseInt(u2.get(i).getSave_day()) + 1;
                            } else if (Integer.parseInt(u2.get(i).getLast_login()) == Integer.parseInt(TimeUtils.getDayCom())) {
                                day = Integer.parseInt(u2.get(i).getSave_day());
                            } else {
                                day = 1;
                            }

                            userViewModel.setCurrent_user(u2.get(i));
                            User user = new User();
                            user = u2.get(i);
                            user.setSave_day(Integer.toString(day));

                            user.setSubscription_status(u2.get(i).getSubscription_status());
                            user.setSubscription_start_date(u2.get(i).getSubscription_start_date());
                            user.setSave_music(u2.get(i).getSave_music());

                            user.setLast_login(TimeUtils.getDayCom());
                            userViewModel.user_update(user);

                            save_back = Integer.parseInt(user.getSave_back());

                            UserViewModel.insert_user(user);

                            backViewModel.getBack().observe(this, b -> {
                                backViewModel.set_current_back(b.get(save_back));
                            });
                        }

                    }
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

//알람 기능 보완
// 멤버십
//호흡운동, 프로필 공유기능
