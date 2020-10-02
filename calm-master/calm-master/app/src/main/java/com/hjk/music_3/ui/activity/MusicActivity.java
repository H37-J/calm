package com.hjk.music_3.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.bumptech.glide.Glide;
import com.github.pwittchen.swipe.library.rx2.Swipe;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hjk.music_3.R;
import com.hjk.music_3.Service.MusicApplication;
import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.databinding.ActivityMainBinding;
import com.hjk.music_3.ui.activity.player.ActivityLike;
import com.hjk.music_3.ui.activity.player.PlayerActivity;
import com.hjk.music_3.ui.activity.player.PlayerActivity2;
import com.hjk.music_3.ui.activity.player.PlayerActivityLike;
import com.hjk.music_3.ui.fragment.MindFragment;
import com.hjk.music_3.ui.fragment.MainFragment;
import com.hjk.music_3.ui.fragment.ProfileFragment;
import com.hjk.music_3.ui.fragment.MusicFragment;
import com.hjk.music_3.ui.fragment.SleepFragment;

import com.hjk.music_3.ui.viewmodel.CateViewModel;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;
import com.hjk.music_3.ui.viewmodel.UserViewModel;
import com.hjk.music_3.utils.ActivityUtils;
import com.hjk.music_3.utils.Binding;
import com.hjk.music_3.utils.MethodUtils;
import com.hjk.music_3.utils.OnSwipeTouchListener;

public class MusicActivity extends AppCompatActivity {

    MusicViewModel musicViewModel;
    CateViewModel cateViewModel;
    ActivityMainBinding binding;
    Swipe swipe;
    OnSwipeTouchListener onSwipeTouchListener;
    UserViewModel userViewModel;
    int temp=1;
    Random random=new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        swipe=new Swipe();
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setActivity(this);



        userViewModel= ViewModelProviders.of(this).get(UserViewModel.class);
        musicViewModel=ViewModelProviders.of(this).get(MusicViewModel.class);
        cateViewModel=ViewModelProviders.of(this).get(CateViewModel.class);
        cateViewModel.getDay().observe(this,c->{
            int num=random.nextInt(c.size());
            cateViewModel.setCurrentDay(num);
        });
        setupViewFragment();
        setupBottomNavigation();
    }

    @Override
    public void onResume(){
        super.onResume();
//        MethodUtils.UpdateSaveTime(getApplication());
        int time=Integer.parseInt(UserViewModel.getCurrent_user().getValue().getSave_time());
        time= (MusicViewModel.getSave_time()/60)+time;
        UserViewModel.getCurrent_user().getValue().setSave_time(Integer.toString(time));
        UserViewModel.user_update(UserViewModel.getCurrent_user().getValue());

        binding.musicImage.setOnTouchListener(new OnSwipeTouchListener(MusicActivity.this) {
            public void onSwipeTop() {

                if(MusicApplication.getInstance().getServiceInterface().getInit11()||
                        MusicApplication.getInstance().getServiceInterface().getInit12()||
                        MusicApplication.getInstance().getServiceInterface().getInit13()||
                        MusicApplication.getInstance().getServiceInterface().getInit2_1()||
                        MusicApplication.getInstance().getServiceInterface().getInit2_2()||
                        MusicApplication.getInstance().getServiceInterface().getInit2_3()) {
                    Intent_Current_Music11();
                }
                else{
                    if(MusicApplication.getInstance().getServiceInterface().getInit3()||
                            MusicApplication.getInstance().getServiceInterface().getInitMusic()){
                        Intent_Current_Music3();
                    }
                }
            }
            public void onSwipeRight() {
                Toast.makeText(MusicActivity.this, "음악 중지", Toast.LENGTH_SHORT).show();
                binding.mini.setVisibility(View.GONE);
                MusicApplication.getInstance().getServiceInterface().pause();
            }
            public void onSwipeLeft() {
                Toast.makeText(MusicActivity.this, "음악 중지", Toast.LENGTH_SHORT).show();
                binding.mini.setVisibility(View.GONE);
                MusicApplication.getInstance().getServiceInterface().pause();
            }
            public void onSwipeBottom() {
                Toast.makeText(MusicActivity.this, "음악 중지", Toast.LENGTH_SHORT).show();
                binding.mini.setVisibility(View.GONE);
                MusicApplication.getInstance().getServiceInterface().pause();
            }
        });
        binding.sc.setOnTouchListener(new OnSwipeTouchListener(MusicActivity.this) {
            public void onSwipeTop() {

                if(MusicApplication.getInstance().getServiceInterface().getInit11()||
                        MusicApplication.getInstance().getServiceInterface().getInit12()||
                        MusicApplication.getInstance().getServiceInterface().getInit13()||
                        MusicApplication.getInstance().getServiceInterface().getInit2_1()||
                        MusicApplication.getInstance().getServiceInterface().getInit2_2()||
                        MusicApplication.getInstance().getServiceInterface().getInit2_3()) {
                    Intent_Current_Music11();
                }
                else{
                    if(MusicApplication.getInstance().getServiceInterface().getInit3()||
                            MusicApplication.getInstance().getServiceInterface().getInitMusic()){
                        Intent_Current_Music3();
                    }
                }
            }
            public void onSwipeRight() {
                Toast.makeText(MusicActivity.this, "음악 중지", Toast.LENGTH_SHORT).show();
                binding.mini.setVisibility(View.GONE);
                MusicApplication.getInstance().getServiceInterface().pause();
            }
            public void onSwipeLeft() {
                Toast.makeText(MusicActivity.this, "음악 중지", Toast.LENGTH_SHORT).show();
                binding.mini.setVisibility(View.GONE);
                MusicApplication.getInstance().getServiceInterface().pause();
            }
            public void onSwipeBottom() {
                Toast.makeText(MusicActivity.this, "음악 중지", Toast.LENGTH_SHORT).show();
                binding.mini.setVisibility(View.GONE);
                MusicApplication.getInstance().getServiceInterface().pause();
            }
        });


        if(MusicApplication.getInstance().getServiceInterface().getHide()){
//            ConstraintLayout.LayoutParams layoutParam=(ConstraintLayout.LayoutParams)binding.likeLi.getLayoutParams();
//            layoutParam.bottomMargin=120;
//            binding.likeLi.setLayoutParams(layoutParam);
            binding.mini.setVisibility(View.GONE);
        }
        else if(!MusicApplication.getInstance().getServiceInterface().getHide()){
//            ConstraintLayout.LayoutParams layoutParam=(ConstraintLayout.LayoutParams)binding.likeLi.getLayoutParams();
//            layoutParam.bottomMargin=160;
//            binding.likeLi.setLayoutParams(layoutParam);

            binding.mini.setVisibility(View.VISIBLE);
        }

        musicViewModel=ViewModelProviders.of(this).get(MusicViewModel.class);
        musicViewModel.getIsPlaying().observe(this,new Observer<Boolean>(){
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    Glide.with(getApplicationContext()).load(R.drawable.pulse).into(binding.playBtn);

                }else{
                    binding.playBtn.setImageResource(R.drawable.ic_play_arrow_48dp);
                }
            }
        });
        musicViewModel.getProgress().observe(this,new Observer<String>(){
            @Override
            public void onChanged(String string){
                binding.musicTime.setText(string);
            }
        });
        musicViewModel.current_music_all().observe(this,new Observer<Music>(){
            @Override
            public void onChanged(Music music){
                    binding.musicTitle.setText(music.getTitle());
                    Binding.PicassoImage(binding.musicImage, music.getImage());

            }
        });
        setupViewFragment();
        setupBottomNavigation();
        //뮤직 선택후 다시 UI업데이트를 위해
    }

    public void Intent_Like(){
        Intent intent=new Intent(MusicActivity.this, ActivityLike.class);
        startActivity(intent);
    }


    public void Intent_Current_Music11(){
        Intent intent=new Intent(MusicActivity.this, PlayerActivity.class);
        intent.putExtra("bno",musicViewModel.getPos());
        startActivity(intent);
    }




    public void Intent_Current_Music3(){
        Intent intent=new Intent(MusicActivity.this, PlayerActivity2.class);
        intent.putExtra("bno",musicViewModel.getPos());
        startActivity(intent);
    }



    public void start_pause(){
        if(MusicApplication.getInstance().getServiceInterface().isPlaying()){
            MusicApplication.getInstance().getServiceInterface().pause();
        }
        else{
            MusicApplication.getInstance().getServiceInterface().start();
        }
    }

    public void next()   {
        if(MusicApplication.getInstance().getServiceInterface().getInit11())
            MusicApplication.getInstance().getServiceInterface().next11();

        if(MusicApplication.getInstance().getServiceInterface().getInit12())
            MusicApplication.getInstance().getServiceInterface().next12();

        if(MusicApplication.getInstance().getServiceInterface().getInit12())
            MusicApplication.getInstance().getServiceInterface().next12();

        if(MusicApplication.getInstance().getServiceInterface().getInit13())
            MusicApplication.getInstance().getServiceInterface().next13();

        if(MusicApplication.getInstance().getServiceInterface().getInit2_1())
            MusicApplication.getInstance().getServiceInterface().nextSleep();

        if(MusicApplication.getInstance().getServiceInterface().getInit2_2())
            MusicApplication.getInstance().getServiceInterface().nextSleep();

        if(MusicApplication.getInstance().getServiceInterface().getInit2_3())
            MusicApplication.getInstance().getServiceInterface().nextScape();
    }

    public void prev(){
        if(MusicApplication.getInstance().getServiceInterface().getInit11())
            MusicApplication.getInstance().getServiceInterface().prev11();

        if(MusicApplication.getInstance().getServiceInterface().getInit12())
            MusicApplication.getInstance().getServiceInterface().prev12();

        if(MusicApplication.getInstance().getServiceInterface().getInit13())
            MusicApplication.getInstance().getServiceInterface().prev13();

        if(MusicApplication.getInstance().getServiceInterface().getInit2_1())
            MusicApplication.getInstance().getServiceInterface().prevSleep();

        if(MusicApplication.getInstance().getServiceInterface().getInit2_2())
            MusicApplication.getInstance().getServiceInterface().prevDream();

        if(MusicApplication.getInstance().getServiceInterface().getInit2_3())
            MusicApplication.getInstance().getServiceInterface().prevScape();
    }

    private void setupViewFragment(){
        if(temp==1) {
            MainFragment musicFragment = MainFragment.newInstance();
            ActivityUtils.replaceFragmentInActivity(
                    getSupportFragmentManager(), musicFragment, R.id.fragment_container);
        }
        if(temp==2){
            ActivityUtils.replaceFragmentInActivity(
                    getSupportFragmentManager(), SleepFragment.newInstance(),
                    R.id.fragment_container);
        }
        if(temp==3){
            ActivityUtils.replaceFragmentInActivity(
                    getSupportFragmentManager(), MindFragment.newInstance(),
                    R.id.fragment_container);
        }
        if(temp==4){
            ActivityUtils.replaceFragmentInActivity(
                    getSupportFragmentManager(), MusicFragment.newInstance(),
                    R.id.fragment_container);
        }
        if(temp==5){
            ActivityUtils.replaceFragmentInActivity(
                    getSupportFragmentManager(), ProfileFragment.newInstance(),
                    R.id.fragment_container);
        }
    }

    private void setupBottomNavigation(){
        BottomNavigationView bottomNav=findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.action1:
                        ActivityUtils.replaceFragmentInActivity(
                                getSupportFragmentManager(), MainFragment.newInstance(),
                                R.id.fragment_container);
                        temp=1;
                        return true;
                    case R.id.action2:
                        ActivityUtils.replaceFragmentInActivity(
                                getSupportFragmentManager(), SleepFragment.newInstance(),
                                R.id.fragment_container);
                        temp=2;
                        return true;
                    case R.id.action3:
                        ActivityUtils.replaceFragmentInActivity(
                                getSupportFragmentManager(), MindFragment.newInstance(),
                                R.id.fragment_container);
                        temp=3;
                        return true;

                    case R.id.action4:
                        ActivityUtils.replaceFragmentInActivity(
                                getSupportFragmentManager(), MusicFragment.newInstance(),
                                R.id.fragment_container);
                        temp=4;
                        return true;


                    case R.id.action5:

                        ActivityUtils.replaceFragmentInActivity(
                                getSupportFragmentManager(), ProfileFragment.newInstance(),
                                R.id.fragment_container);
                        temp=5;
                        return true;
                }
                return false;
            }
        });
    }
    @Override
    public void onBackPressed() {
    }



}

//2020 08 25
//첫번째 프래그먼트와 3번째 프래그먼트 안에 액티비티 값을 일치시켰다
// java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.String 미해결
//브로드 캐스트 작업,
// 노래재생중에 내 어플에 재생시 다른 어플 음악 중지하기 개발하기
// 스와이프해서 화면 내리기 개발하기, 이어폰 뺄 때 노래정지 개발하기